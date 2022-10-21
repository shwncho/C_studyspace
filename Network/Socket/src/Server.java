import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
public class Server {
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket;
        HashMap<String, Account> client=new HashMap<>();
        //Client 5 account info
        client.put("Cho",new Account("Cho",10000));
        client.put("Jeong",new Account("Jeong",1000));
        client.put("Seo",new Account("Seo",5000));
        client.put("Lee",new Account("Lee",50));
        client.put("Nam",new Account("Nam",20));

        try{
            serverSocket = new ServerSocket(6789);
            while (true) {
                Socket socket = serverSocket.accept();
                Thread thread = new ServerThread(socket,client);
                thread.start();

            }

        } catch(IOException e){
            System.out.println(e.getMessage());
        }

    }
}
