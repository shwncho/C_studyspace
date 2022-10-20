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
        client.put("조석환",new Account("조석환",10000));
        client.put("정조은",new Account("정조은",1000));
        client.put("서은수",new Account("서은수",5000));
        client.put("이서현",new Account("이서현",50));
        client.put("남선우",new Account("남선우",20));

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
