import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception{
        try{
            Socket socket = new Socket("127.0.0.1",6789);
            Thread thread = new ClientThread(socket);
            thread.start();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }



    }
}
