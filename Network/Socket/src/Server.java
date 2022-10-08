import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket;
        HashMap<String, Account> client=new HashMap<>();
        client.put("조석환",new Account("조석환",1000));
        client.put("아무개",new Account("아무개",2000));

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
