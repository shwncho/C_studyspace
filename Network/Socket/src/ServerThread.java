import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.StringTokenizer;

public class ServerThread extends Thread{
    Socket socket;
    HashMap<String, Account> client;
    String clientName;
    String menu="1. 계좌 확인    2.입금    3.출금    4.이체    5.거래 종료";
    int menuNum;
    boolean flag = true;

    PrintWriter writer;

    ServerThread(Socket socket, HashMap<String, Account> client){
        this.socket=socket;
        this.client=client;
        try{
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {

            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                //이름 읽기
                clientName = reader.readLine();

                Account out = client.get(clientName);

                while(flag) {

                    writer.println(menu);
                    writer.flush();

                    menuNum = Integer.parseInt(reader.readLine());

                    switch (menuNum) {

                        //이름과 잔액 확인
                        case 1:
                            writer.println(out.check());
                            writer.flush();

                            break;

                        // 입금
                        case 2:

                            double depositMoney = Double.parseDouble(reader.readLine());
                            out.deposit(depositMoney);

                            writer.println(out.check());
                            writer.flush();

                            break;

                        // 출금
                        case 3:

                            double withdrawMoney = Double.parseDouble(reader.readLine());

                            if (withdrawMoney > out.getBalance()) {

                                //출금액이 계좌 잔고보다 많을경우
                                writer.println("계좌 잔고가 부족합니다.");
                                writer.flush();
                            }
                            else {
                                out.withdraw(withdrawMoney);

                                writer.println(out.check());
                                writer.flush();
                            }

                            break;

                        // 이체
                        case 4:

                            StringTokenizer st = new StringTokenizer(reader.readLine());
                            out.transfer(client.get(st.nextToken()), Double.parseDouble(st.nextToken()));

                            writer.println(out.check());
                            writer.flush();

                            break;

                        // 거래 종료
                        case 5:
                            flag=false;
                            socket.close();
                            break;

                        default:
                            writer.println("올바르지 않은 입력입니다. 다시 시도해주세요.");
                            writer.flush();
                    }
                }


            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
    }
}
