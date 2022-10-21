import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class Client {
    public static void main(String[] args) throws Exception{

        try{
            BufferedReader br = new BufferedReader(new FileReader("serverinfo.dat"));
            StringTokenizer st = new StringTokenizer(br.readLine());
            String host = st.nextToken();
            int port = Integer.parseInt(st.nextToken());

            Socket socket = new Socket(host,port);
            Thread thread = new ClientThread(socket);
            thread.start();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }



    }
}
