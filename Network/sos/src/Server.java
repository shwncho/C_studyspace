import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Server{
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket;
        //유저명 + 유저의 격자판 위치
        Map<String,Account> grid = new HashMap<>();
        ArrayList<Point> seats=new ArrayList<>();

        try{
            serverSocket = new ServerSocket(6789);
            Collections.synchronizedMap(grid);
            Collections.synchronizedList(seats);
            while (true) {
                Socket socket = serverSocket.accept();
                Thread thread = new ServerThread(socket,grid,seats);
                thread.start();

            }

        } catch(IOException e){
            System.out.println(e.getMessage());
        }

    }
    //격자판 서버 쓰레드에 반영

    //소켓 연결 로직

    //유저로부터 받은 회원정보 서버 쓰레드에 반영


}
