import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread{
    Socket socket;
    //name entered by the user
    String clientName;
    //display user menu
    String menu;
    //choice menuNum
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

            //Entered by user
            clientName = inFromUser.readLine();

            //send to server
            writer.println(clientName);
            writer.flush();

            //If user does not exist, Exception is occurred
            String nameCheck = inFromServer.readLine();
            if(nameCheck.equals("4000")){
                System.out.println("code: 4000,\nmessage: Account doesn't exist");
                socket.close();
            }
            else System.out.println("code: 1000,\nmessage: Success Login");

            while(flag){
                //receive menu by server
                menu = inFromServer.readLine();
                //display user menu
                System.out.println(menu);

                //user enter menuNum
                menuNum = inFromUser.readLine();

                //send menuNum to server
                writer.println(menuNum);
                writer.flush();

                switch (menuNum) {

                    //check
                    case "1":
                        System.out.println("code:1000\nmessage:"+inFromServer.readLine());
                        break;

                    //deposit
                    case "2":
                        //entered deposit money by user
                        System.out.println("Enter deposit money");
                        String depositMoney = inFromUser.readLine();

                        //send depositMoney to server
                        writer.println(depositMoney);
                        writer.flush();

                        //check if entered value is numeric
                        String checkNumeric = inFromServer.readLine();
                        if(checkNumeric.equals("4001")){
                            System.out.println("code:4001,\nmessage: you have to enter only number");
                            continue;
                        }


                        System.out.println("code:1000,\nmessage: After deposit, your balance");
                        System.out.println(inFromServer.readLine());
                        break;

                    //withdraw
                    case "3":
                        //entered withdrawMoney by user
                        System.out.println("Enter withdraw money");
                        String withdrawMoney = inFromUser.readLine();

                        //send withdrawMoney to server
                        writer.println(withdrawMoney);
                        writer.flush();

                        //check if entered value is numeric
                        checkNumeric = inFromServer.readLine();
                        if(checkNumeric.equals("4001")){
                            System.out.println("code:4001,\nmessage: you have to enter only number");
                            continue;
                        }

                        //check user's balance
                        String withdrawResult = inFromServer.readLine();
                        if (withdrawResult.equals("4002")) {
                            System.out.println("code:4002,\nmessage: Your account balance is insufficient.");
                            continue;
                        } else {
                            System.out.println("code:1000,\nmessage:After withdraw, your balance");
                            System.out.println(inFromServer.readLine());
                            break;
                        }

                    //transfer
                    case "4":
                        //Entered the transfer target and the transfer money by user
                        System.out.println("Enter the transfer target and the transfer money in order.(between target and money, greater than 1 space)");
                        writer.println(inFromUser.readLine());
                        writer.flush();

                        //check argument number(number have to have 2(target, money))
                        String argumentCheck = inFromServer.readLine();
                        if (argumentCheck.equals("4003")) {
                            System.out.println("code:4003,\nmessage: lack of arguments");
                            continue;
                        }

                        //check user's balance
                        String transferCheck = inFromServer.readLine();
                        if (transferCheck.equals("4002")) {
                            System.out.println("code:4002,\nmessage: Your account balance is insufficient.");
                            continue;
                        } else if (transferCheck.equals("4000")) {
                            System.out.println("code:4000,\nmessage: Account doesn't exist");
                            continue;
                        } else {
                            System.out.println("code:1000,\nmessage: After transfer, your balance");
                            System.out.println(inFromServer.readLine());
                            break;
                        }

                    // exit banking system
                    case "5":
                        flag = false;
                        socket.close();
                        break;

                    //If a value other than the menu number is entered
                    default:
                        if(inFromServer.readLine().equals("4001")){
                            System.out.println("code:4001,\nmessage: you have to enter number(1~5)");
                        }
                }

            }


        } catch (IOException e){
            System.out.println("code:4004\n"+e.getMessage());
        }
    }
}
