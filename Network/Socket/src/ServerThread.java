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
    String menu="1.check    2.deposit    3.withdraw    4.transfer    5.exit";
    String menuNum;
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
                Account out = null;
                if(client.containsKey(clientName)){
                    out = client.get(clientName);
                    writer.println("success");
                    writer.flush();
                }
                else{
                    writer.println("Exception: Account not found");
                    writer.flush();
                    socket.close();
                }
                while(flag) {
                    try {
                        writer.println(menu);
                        writer.flush();

                        menuNum = reader.readLine();

                        switch (menuNum) {

                            //이름과 잔액 확인
                            case "1":
                                writer.println(out.check());
                                writer.flush();

                                break;

                            // 입금
                            case "2":
                                String money = reader.readLine();
                                if(!isNumeric(money)){
                                    writer.println("Invalid input. Please try again.");
                                    writer.flush();
                                    continue;
                                }
                                else{
                                    writer.println("success");
                                    writer.flush();
                                }

                                double depositMoney = Double.parseDouble(money);
                                out.deposit(depositMoney);

                                writer.println(out.check());
                                writer.flush();

                                break;

                            // 출금
                            case "3":
                                money = reader.readLine();
                                if(!isNumeric(money)){
                                    writer.println("Invalid input. Please try again.");
                                    writer.flush();
                                    continue;
                                }
                                else{
                                    writer.println("success");
                                    writer.flush();
                                }
                                double withdrawMoney = Double.parseDouble(money);

                                if (withdrawMoney > out.getBalance()) {

                                    //출금액이 계좌 잔고보다 많을경우
                                    writer.println("Your account balance is insufficient.");
                                    writer.flush();
                                    continue;
                                } else {
                                    writer.println("success");
                                    out.withdraw(withdrawMoney);

                                    writer.println(out.check());
                                    writer.flush();
                                }

                                break;

                            // 이체
                            case "4":

                                StringTokenizer st = new StringTokenizer(reader.readLine());
                                if (st.countTokens() == 2) {
                                    writer.println("success");
                                    writer.flush();
                                } else {
                                    writer.println("Exception: lack of arguments");
                                    writer.flush();
                                    continue;

                                }
                                String targetName = st.nextToken();
                                Double transferMoney = Double.parseDouble(st.nextToken());
                                if (client.containsKey(targetName)) {
                                    if (out.getBalance() < transferMoney) {
                                        writer.println("Exception: Your account balance is insufficient.");
                                        writer.flush();
                                        continue;
                                    }
                                    out.transfer(client.get(targetName), transferMoney);

                                    writer.println("success");
                                    writer.flush();

                                } else {
                                    writer.println("Exception: Account not found");
                                    writer.flush();
                                    continue;
                                }

                                writer.println(out.check());
                                writer.flush();
                                break;

                            // 거래 종료
                            case "5":
                                flag = false;
                                socket.close();
                                break;

                            default:
                                writer.println("Invalid input. Please try again.");
                                writer.flush();
                        }
                    } catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }


            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
    }

    private static boolean isNumeric(String s){
        try{
            Double.parseDouble(s);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
