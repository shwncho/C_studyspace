import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread{
    Socket socket;
    String clientName;
    String menu;
    int menuNum;
    boolean flag = true;

    ClientThread(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {
        try{
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            System.out.println("성함을 입력해주세요.");

            //이름 입력받음
            clientName = inFromUser.readLine();

            //이름을 서버로 보냄
            writer.println(clientName);
            writer.flush();

            //없는 고객일 경우 예외 처리

            //존재할경우
            System.out.println("인증되었습니다.");
            while(true){

                menu = inFromServer.readLine();
                System.out.println(menu);

                //유저로부터 메뉴번호 입력받기
                menuNum = Integer.parseInt(inFromUser.readLine());

                //서버로 메뉴 번호 보냄
                writer.println(menuNum);
                writer.flush();

                switch (menuNum){
                    //이름과 잔액 확인
                    case 1:
                        System.out.println(inFromServer.readLine());
                        break;

                    //입금
                    case 2:
                        //유저에게 입금액 입력받기
                        System.out.println("입금액을 입력해주세요.");
                        double depositMoney=Double.parseDouble(inFromUser.readLine());

                        //서버로 입금액 보내기
                        writer.println(depositMoney);
                        writer.flush();

                        //입금이후 잔액
                        System.out.println("입금이후 잔액:");
                        System.out.println(inFromServer.readLine());
                        break;

                    //출금
                    case 3:
                        //유저에게 출금액 입력받기
                        System.out.println("출금액을 입력해주세요.");
                        double withdrawMoney = Double.parseDouble(inFromUser.readLine());

                        //서버로 출금액 보내기
                        writer.println(withdrawMoney);
                        writer.flush();

                        System.out.println("출금이후 잔액:");
                        System.out.println(inFromServer.readLine());
                        break;

                    //이체
                    case 4:
                        //유저로부터 이체 대상과 금액 입력받기
                        System.out.println("이체 대상과 이체 금액을 순서대로 입력해주세요.");
                        writer.println(inFromUser.readLine());
                        writer.flush();

                        System.out.println("이체 후 잔액:");
                        System.out.println(inFromServer.readLine());

                        break;

                    // 거래 종료
                    case 5:
                        flag=false;
                        socket.close();
                        break;

                    default:
                        System.out.println(inFromServer.readLine());
                }
            }


        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
