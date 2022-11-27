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
    Map<String,Account> playUser;
    Map<String, Account> userDB;
    ArrayList<Point> seats;
    AtomicInteger userLifes;
    private final int MOVE_SIZE=10;


    ServerThread(Socket socket, Map<String,Account> playUser, ArrayList<Point> seats,Map<String,Account> userDB,AtomicInteger userLifes){
        this.socket=socket;
        this.playUser=playUser;
        this.userDB=userDB;
        this.seats=seats;
        this.userLifes=userLifes;
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
        String num="";
        //클라이언트로부터 넘겨받는 메세지
        String msg="";
        try{
            while(true){
                if((msg=reader.readLine())!=null){
                    st=new StringTokenizer(msg,"#");
                    num=st.nextToken();
                    switch (num){
                        case "1":
                            //회원가입
                            msg=st.nextToken();
                            signUp(msg);
                            break;
                        case "2":
                            //로그인
                            msg=st.nextToken();
                            signIn(msg);
                            break;
                        case "3":
                            //게임시작
                            startGame();
                            break;
                        case "4":
                            msg=st.nextToken();
                            move(msg);
                            break;
                        case "5":
                            nextRound();
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
        if(userDB.containsKey(nickname)){
            writer.println("4000");
            writer.flush();
        }
        else{
            this.account=new Account(nickname,password);
            userDB.put(nickname,account);
            writer.println("SUCCESS");
            writer.flush();
        }
    }

    private void signIn(String msg){
        //닉네임 비밀번호를 공백을 기준으로 입력받음
        st=new StringTokenizer(msg," ");
        String nickname = st.nextToken();
        String password = st.nextToken();

        //존재하지 않는 닉네임 일경우
        if(!userDB.containsKey(nickname)){
            writer.println("4001");
            writer.flush();
        }
        else if(!userDB.get(nickname).getPassword().equals(password)){
            writer.println("4002");
            writer.flush();
        }
        else{
            //가장 먼저 로그인해서 방에 입장한 유저에게 host 부여
            playUser.put(nickname,account);
            account.setWriter(writer);
            if(playUser.size()==1){
                playUser.get(nickname).setHost(true);
                writer.println("Host is: "+nickname);
                writer.flush();
            }
            else if(playUser.size()>4){
                writer.println("4003");
                writer.flush();
            }
            else playUser.get(nickname).setHost(false);
            //방 입장 메세지
            joinUser();
        }
    }

    private void joinUser(){
        broadCast(account.getNickname() + "이(가) 입장했습니다.");
        broadCast("현재 인원: ("+playUser.size()+"/4");
    }

    private void startGame(){
        if(playUser.size()<4){
            writer.println("4004");
            writer.flush();
        }
        else{
            broadCast("After 3 seconds, Game Start");
            try{
                Thread.sleep(3000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            broadCast("--------------Game Start--------------");

            for(Account user : playUser.values()){
                user.setSeated(false);
                user.setLife(true);
                userLifes.incrementAndGet();
                user.setPoint(randomUserLocation());

                broadCast(user.getNickname()+" "+user.getPoint());
            }

            //게임시작할 때 3개
            for(int i=0; i<3; i++){
                broadCast("seat: "+randomSeatLocation());
            }

        }

    }

    private Point randomUserLocation(){
        //랜덤 유저 위치 할당
        int x = (int)(Math.random()*38 + 1)*MOVE_SIZE;
        int y = (int)(Math.random()*38 + 1)*MOVE_SIZE;

        return new Point(x,y);
    }

    private Point randomSeatLocation(){
        boolean flag=true;
        Point seat=null;
        //현재 유저 위치의 격자판을 제외하고 의자 위치 생성
        while(true){
            //랜덤 의자 위치 할당
            int x = (int)(Math.random()*38 +1)*MOVE_SIZE;
            int y = (int)(Math.random()*38 +1)*MOVE_SIZE;

            seat = new Point(x,y);
            for (Account user : playUser.values()) {
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

        Account user = playUser.get(nickname);
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
            writer.println("4005");
            writer.flush();
        }

        //움직인 유저의 닉네임과 변경된 위치 반환
        broadCast(user.getNickname()+" "+user.getPoint());
        //남은 의자 좌석수 확인
        if(!isSeatNum()){
            broadCast("--------------Finish Round--------------");
            finishRound();
            try{
                Thread.sleep(3000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private void moveW(Account user, Point userPoint){
        user.setPoint(new Point(userPoint.getX(),userPoint.getY()-1));
        checkSeat(user);
    }

    private void moveA(Account user, Point userPoint){
        user.setPoint(new Point(userPoint.getX()-1, userPoint.getY()));
        checkSeat(user);
    }

    private void moveS(Account user, Point userPoint){
        user.setPoint(new Point(userPoint.getX(), userPoint.getY()+1));
        checkSeat(user);
    }

    private void moveD(Account user, Point userPoint){
        user.setPoint(new Point(userPoint.getX()+1, userPoint.getY()));
        checkSeat(user);
    }

    //유저가 이동한 위치가 의자인지 확인
    private void checkSeat(Account user){
        for(Point p : seats){
            if(p.equals(user.getPoint())){
                seats.remove(p);
                user.setSeated(true);
            }
        }
    }

    private boolean isSeatNum(){
        return seats.size() != 0;
    }

    private void finishRound(){
        for(Account user : playUser.values()){
            if(user.isLife() && !user.isSeated()){
                user.setLife(false);
                userLifes.decrementAndGet();
                broadCast("Fail user: "+user.getNickname());
            }
        }

        //마지막 턴에서 이긴 유저는 클라이언트에서 winner 출력해주기
        if(userLifes.get()==1){
            broadCast("Winner: "+account.getNickname());
        }


    }

    private void nextRound(){
        broadCast("After 3 seconds, Next Round");
        try{
            Thread.sleep(3000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        broadCast("--------------Next Round--------------");

        for(Account user : playUser.values()){
            //Life가 있는 유저인지 확인
            if(account.isLife()){
                user.setSeated(false);
                user.setPoint(randomUserLocation());

                broadCast(user.getNickname()+" "+user.getPoint());
            }
        }

        for(int i=0; i<userLifes.get()-1; i++){
            broadCast("seat: "+ randomSeatLocation());
        }


    }

    //host가 10초안에 게임 시작 안할 시 변경
    private void changeHost(){
        String prevHostNick="";
        for(Account user : playUser.values()){
            if(user.isHost()){
                prevHostNick=user.getNickname();
                user.setHost(false);
            }
        }

        for(Account user : playUser.values()){
            if(!user.isHost() && !user.getNickname().equals(prevHostNick)){
                user.setHost(true);
                broadCast("New host: "+user.getNickname());
            }
        }

    }

    private void exit(){
        if(account.isHost())    changeHost();
        broadCast("Exit user: "+account.getNickname());
        broadCast("현재 인원("+playUser.size()+"/4)");
        playUser.remove(account.getNickname());
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void broadCast(String msg){
        playUser.values().forEach(user -> {
            PrintWriter writer = user.getWriter();
            writer.println(msg);
            writer.flush();
        });
    }

}
