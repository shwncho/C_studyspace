import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerThread extends Thread {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private StringTokenizer st;
    private Account account;
    Map<String,Account> grid;
    ArrayList<Point> seats;


    ServerThread(Socket socket, Map<String,Account> grid, ArrayList<Point> seats){
        this.socket=socket;
        this.grid=grid;
        this.seats=seats;
        try{
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run(){
        //실행 로직 선택숫자
        // 1. 회원가입 2. 로그인
        String num="";
        //클라이언트로부터 넘겨받는 메세지
        String msg="";
        try{
            while(true){
                if((num=reader.readLine())!=null){
                    switch (num){
                        case "1":
                            //회원가입
                            msg=reader.readLine();
                            signUp(msg);
                            break;
                        case "2":
                            //로그인
                            msg=reader.readLine();
                            signIn(msg);
                            break;
                        case "3":
                            //게임시작
                            startGame(msg);
                            break;
                        case "4":
                            move(msg);
                            break;
                        case "5":
                            nextRound(msg);
                            break;
                        case "6":
                            changeHost();
                            break;
                        case "7":
                            exit();
                            break;

                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void signUp(String msg){
        //닉네임 비밀번호를 공백을 기준으로 입력받음
        st=new StringTokenizer(msg," ");
        String nickname = st.nextToken();
        String password = st.nextToken();

        //이미 존재하는 닉네임 일경우
        if(grid.containsKey(nickname)){
            writer.println("Exception: ALREADY EXISTS NICKNAME");
            writer.flush();
        }
        else{
            account=new Account(nickname,password);
            writer.println("Success: SIGNUP");
            writer.flush();
        }
    }

    private void signIn(String msg){
        //닉네임 비밀번호를 공백을 기준으로 입력받음
        st=new StringTokenizer(msg," ");
        String nickname = st.nextToken();
        String password = st.nextToken();

        //존재하지 않는 닉네임 일경우
        if(!grid.containsKey(nickname)){
            writer.println("Exception: ACCOUNT NOT FOUND");
            writer.flush();
        }
        else if(grid.get(nickname).getPassword()!=password){
            writer.println("Exception: PASSWORD NOT EQUALS");
            writer.flush();
        }
        else{
            //가장 먼저 로그인해서 방에 입장한 유저에게 host 부여
            grid.put(nickname,account);
            if(grid.size()==1){
                grid.get(nickname).setHost(true);
                writer.println("Host is: "+nickname);
                writer.flush();
            }
            else if(grid.size()>4){
                writer.println("Exception: PLAYER CAN NOT MORE THAN 4");
                writer.flush();
            }
            else grid.get(nickname).setHost(false);
            //방 입장 메세지
            joinUser();
        }
    }

    private void joinUser(){
        writer.println(account.getNickname() + "이(가) 입장했습니다.");
        writer.flush();
    }

    private void startGame(String msg){
        //게임 시작하는 유저의 닉네임을 넘겨받는다.
        if(grid.size()!=4){
            writer.println("Exception: INITIAL PLAYER IS NOT 4");
            writer.flush();
        }
        else{
            Account user = grid.get(msg);
            user.setSeated(false);
            user.setLife(true);
            user.setPoint(randomUserLocation());

            writer.println(user.getNickname()+" "+user.getPoint());
            writer.println("seat: "+ randomSeatLocation());
            writer.flush();

        }

    }

    private Point randomUserLocation(){
        //격자판 크기 정해지면 제대로 할당
        int x = (int)(Math.random()*10);
        int y = (int)(Math.random()*10);

        return new Point(x,y);
    }

    private Point randomSeatLocation(){
        boolean flag=true;
        Point seat=null;
        //현재 유저 위치의 격자판을 제외하고 의자 위치 생성
        while(true){
            //격자판 크기 정해지면 제대로 할당
            int x = (int)(Math.random()*10);
            int y = (int)(Math.random()*10);

            seat = new Point(x,y);
            for (Account user : grid.values()) {
                if(user.getPoint().equals(seat))    flag=false;
            }
            if(flag)    break;
        }

        seats.add(seat);
        return seat;
    }

    private void move(String msg){
        //msg => 유저 닉네임 + 'W' or 'A' or 'S' or 'D'
        st=new StringTokenizer(msg," ");
        String nickname = st.nextToken();
        String key = st.nextToken();

        Account user = grid.get(nickname);
        Point userPoint = user.getPoint();

        if(key.equals("W")){
            moveW(user,userPoint);
        }
        else if(key.equals("A")){
            moveA(user,userPoint);
        }
        else if(key.equals("S")){
            moveS(user,userPoint);
        }
        else if(key.equals("D")){
            moveD(user,userPoint);
        }
        else{
            writer.println("Exception: INVALID INPUT");
            writer.flush();
        }

        //움직인 유저의 닉네임과 변경된 위치 반환
        writer.println(user.getNickname()+" "+user.getPoint());
        writer.flush();
        //남은 의자 좌석수 확인
        if(!isSeatNum()){
            writer.println("FINISH ROUND");
            writer.flush();
            finishRound();
        }
    }

    private void moveW(Account user, Point userPoint){
        user.setPoint(new Point(userPoint.getX(),userPoint.getY()+1));
        seats.stream().filter(s-> s.equals(user.getPoint())).forEach(s -> {
            seats.remove(s);
            user.setSeated(true);
        });
    }

    private void moveA(Account user, Point userPoint){
        user.setPoint(new Point(userPoint.getX()-1, userPoint.getY()));
        seats.stream().filter(s-> s.equals(user.getPoint())).forEach(s -> {
            seats.remove(s);
            user.setSeated(true);
        });
    }

    private void moveS(Account user, Point userPoint){
        user.setPoint(new Point(userPoint.getX(), userPoint.getY()-1));
        seats.stream().filter(s-> s.equals(user.getPoint())).forEach(s -> {
            seats.remove(s);
            user.setSeated(true);
        });
    }

    private void moveD(Account user, Point userPoint){
        user.setPoint(new Point(userPoint.getX()+1, userPoint.getY()));
        seats.stream().filter(s-> s.equals(user.getPoint())).forEach(s -> {
            seats.remove(s);
            user.setSeated(true);
        });
    }

    private boolean isSeatNum(){
        return seats.size() != 0;
    }

    private void finishRound(){
        if(!account.isSeated()){
            account.setLife(false);
            writer.println("Fail user: "+account.getNickname());
            writer.flush();
        }
        //마지막 턴에서 이긴 유저는 클라이언트에서 winner 출력해주기

    }

    private void nextRound(String msg){
        //Life가 있는 유저인지 확인
        if(account.isLife()){
            Account user = grid.get(msg);
            user.setSeated(false);
            user.setPoint(randomUserLocation());

            writer.println(user.getNickname()+" "+user.getPoint());
            writer.println("seat: "+ randomSeatLocation());
            writer.flush();
        }

    }

    //host가 10초안에 게임 시작 안할 시 변경
    private void changeHost(){
        String prevHostNick="";
        for(Account user : grid.values()){
            if(user.isHost()){
                prevHostNick=user.getNickname();
                user.setHost(false);
            }
        }

        for(Account user : grid.values()){
            if(!user.isHost() && !user.getNickname().equals(prevHostNick)){
                user.setHost(true);
                writer.println("New host: "+user.getNickname());
                writer.flush();
            }
        }

    }

    private void exit(){
        if(account.isHost())    changeHost();
        writer.println("Exit user: "+account.getNickname());
        grid.remove(account.getNickname());
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
