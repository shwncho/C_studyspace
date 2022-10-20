import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread{
    Socket socket;
    String clientName;
    String menu;
    String menuNum;
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

            System.out.println("Enter your name: ");

            //이름 입력받음
            clientName = inFromUser.readLine();

            //이름을 서버로 보냄
            writer.println(clientName);
            writer.flush();

            //없는 고객일 경우 예외 처리
            String nameCheck = inFromServer.readLine();
            if(!nameCheck.equals("success")){
                System.out.println("Error message: Account doesn't exist");
                socket.close();
            }
            else System.out.println("Success Login");

            while(true){

                menu = inFromServer.readLine();
                System.out.println(menu);


                menuNum = inFromUser.readLine();

                //서버로 메뉴 번호 보냄
                writer.println(menuNum);
                writer.flush();
                try{
                switch (menuNum) {

                        //이름과 잔액 확인
                        case "1":
                            System.out.println(inFromServer.readLine());
                            break;

                        //입금
                        case "2":
                            //유저에게 입금액 입력받기
                            System.out.println("Enter deposit money");
                            String depositMoney = inFromUser.readLine();

                            //서버로 입금액 보내기
                            writer.println(depositMoney);
                            writer.flush();

                            String checkNumeric = inFromServer.readLine();
                            if(!checkNumeric.equals("success")){
                                System.out.println("Error Message: you have to enter only number");
                                continue;
                            }

                            //입금이후 잔액
                            System.out.println("After deposit, your balance");
                            System.out.println(inFromServer.readLine());
                            break;

                        //출금
                        case "3":
                            //유저에게 출금액 입력받기
                            System.out.println("Enter withdraw money");
                            String withdrawMoney = inFromUser.readLine();

                            //서버로 출금액 보내기
                            writer.println(withdrawMoney);
                            writer.flush();

                            checkNumeric = inFromServer.readLine();
                            if(!checkNumeric.equals("success")){
                                System.out.println("Error Message: you have to enter only number");
                                continue;
                            }


                            String withdrawResult = inFromServer.readLine();
                            if (!withdrawResult.equals("success")) {
                                System.out.println("Error Message: Your account balance is insufficient.");
                                continue;
                            } else {
                                System.out.println("After withdraw, your balance");
                                System.out.println(inFromServer.readLine());
                                break;
                            }


                            //이체
                        case "4":
                            //유저로부터 이체 대상과 금액 입력받기
                            System.out.println("Enter the transfer target and the transfer money in order.(between target and money, greater than 1 space)");
                            writer.println(inFromUser.readLine());
                            writer.flush();

                            String argumentResult = inFromServer.readLine();
                            if (!argumentResult.equals("success")) {
                                System.out.println("Error message: lack of arguments");
                                continue;
                            }

                            String transferResult = inFromServer.readLine();
                            if (transferResult.equals("Exception: Your account balance is insufficient.")) {
                                System.out.println("Error message: Your account balance is insufficient.");
                                continue;
                            } else if (transferResult.equals("Exception: Account not found")) {
                                System.out.println("Error message: Account doesn't exist");
                                continue;
                            } else {
                                System.out.println("After transfer, your balance");
                                System.out.println(inFromServer.readLine());
                                break;
                            }

                            // 거래 종료
                        case "5":
                            flag = false;
                            socket.close();
                            break;

                        default:
                            System.out.println(inFromServer.readLine());
                    }
                }catch(Exception e){
                    System.out.println("Invalid input. Please try again.");
                }
            }


        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
