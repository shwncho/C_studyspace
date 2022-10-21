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

                //check if client is
                clientName = reader.readLine();
                Account out = null;
                if(client.containsKey(clientName)){
                    out = client.get(clientName);
                    writer.println("1000");
                    writer.flush();
                }
                else{
                    System.out.println("Exception: Account not found");
                    writer.println("4000");
                    writer.flush();
                    socket.close();
                }
                while(flag) {
                    try {
                        //send menu to client
                        writer.println(menu);
                        writer.flush();

                        //receive menuNum by client
                        menuNum = reader.readLine();

                        switch (menuNum) {

                            //check name and balance
                            case "1":
                                writer.println(out.check());
                                writer.flush();

                                break;

                            // deposit
                            case "2":
                                //receive depositMoney by client
                                String money = reader.readLine();
                                if(!isNumeric(money)){
                                    System.out.println("Exception: Invalid input.(not numeric)");
                                    writer.println("4001");
                                    writer.flush();
                                    continue;
                                }
                                else{
                                    writer.println("1000");
                                    writer.flush();
                                }

                                double depositMoney = Double.parseDouble(money);
                                out.deposit(depositMoney);

                                writer.println(out.check());
                                writer.flush();

                                break;

                            //withdraw
                            case "3":
                                //receive withdrawMoney by client
                                money = reader.readLine();
                                if(!isNumeric(money)){
                                    System.out.println("Exception: Invalid input.(not numeric)");
                                    writer.println("4001");
                                    writer.flush();
                                    continue;
                                }
                                else{
                                    writer.println("1000");
                                    writer.flush();
                                }
                                double withdrawMoney = Double.parseDouble(money);

                                //If withdrawMoney is more than client's balance, Exception is occurred
                                if (withdrawMoney > out.getBalance()) {
                                    System.out.println("Exception: Account balance is insufficient.");
                                    writer.println("4002");
                                    writer.flush();
                                    continue;
                                } else {
                                    writer.println("1000");
                                    out.withdraw(withdrawMoney);

                                    writer.println(out.check());
                                    writer.flush();
                                }

                                break;

                            // transfer
                            case "4":

                                //check argument number(number have to have 2(target, money))
                                StringTokenizer st = new StringTokenizer(reader.readLine());
                                if (st.countTokens() == 2) {
                                    writer.println("1000");
                                    writer.flush();
                                } else {
                                    System.out.println("Exception: lack of arguments");
                                    writer.println("4003");
                                    writer.flush();
                                    continue;

                                }
                                String targetName = st.nextToken();
                                Double transferMoney = Double.parseDouble(st.nextToken());
                                //check if transfer target is
                                if (client.containsKey(targetName)) {
                                    //If transferMoney is more than client's balance, Exception is occurred
                                    if (out.getBalance() < transferMoney) {
                                        System.out.println("Exception: Account balance is insufficient.");
                                        writer.println("4002");
                                        writer.flush();
                                        continue;
                                    }
                                    out.transfer(client.get(targetName), transferMoney);

                                    writer.println("1000");
                                    writer.flush();

                                } else {
                                    System.out.println("Exception: Account not found");
                                    writer.println("4000");
                                    writer.flush();
                                    continue;
                                }

                                writer.println(out.check());
                                writer.flush();
                                break;

                            // exit banking system
                            case "5":
                                flag = false;
                                socket.close();
                                break;

                            //If a value other than the menu number is entered
                            default:
                                System.out.println("Invalid input. (no operation)");
                                writer.println("4001");
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

    //method, check if value is numeric
    private static boolean isNumeric(String s){
        try{
            Double.parseDouble(s);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
