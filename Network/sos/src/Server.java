import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Server{
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket;
        Map<String,Account> playUser = new HashMap<>();
        Map<String,Account> userDB = new HashMap<>();
        ArrayList<Point> seats=new ArrayList<>();
        AtomicInteger userLifes=new AtomicInteger(0);


        try{
            serverSocket = new ServerSocket(6789);
            Collections.synchronizedMap(playUser);
            Collections.synchronizedMap(userDB);
            Collections.synchronizedList(seats);
            while (true) {
                Socket socket = serverSocket.accept();
                Thread thread = new ServerThread(socket,playUser,seats,userDB,userLifes);
                thread.start();

            }

        } catch(IOException e){
            System.out.println(e.getMessage());
        }

    }

}
