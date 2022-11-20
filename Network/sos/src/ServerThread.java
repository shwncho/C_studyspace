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
                    st=new StringTokenizer(msg," ");
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
                            msg=st.nextToken();
                            startGame(msg);
                            break;
                        case "4":
                            msg=st.nextToken();
                            move(msg);
                            break;
                        case "5":
                            msg=st.nextToken();
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
        writer.println(account.getNickname() + "이(가) 입장했습니다.");
        writer.flush();
    }

    private void startGame(String msg){
        //게임 시작하는 유저의 닉네임을 넘겨받는다.
        if(playUser.size()<4){
            writer.println("4004");
            writer.flush();
        }
        else{
            Account user = playUser.get(msg);
            user.setSeated(false);
            user.setLife(true);
            userLifes.incrementAndGet();
            user.setPoint(randomUserLocation());

            writer.println(user.getNickname()+" "+user.getPoint());
            //게임시작할 때 3개
            if(seats.size()<4){
                writer.println("seat: "+randomSeatLocation());
                writer.flush();
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
        seats.stream().filter(s-> s.equals(user.getPoint())).forEach(s -> {
            seats.remove(s);
            user.setSeated(true);
        });
    }

    private boolean isSeatNum(){
        return seats.size() != 0;
    }

    private void finishRound(){
        if(account.isLife() && !account.isSeated()){
            account.setLife(false);
            userLifes.decrementAndGet();
            writer.println("Fail user: "+account.getNickname());
            writer.flush();
        }
        //마지막 턴에서 이긴 유저는 클라이언트에서 winner 출력해주기
        if(userLifes.get()==1){
            writer.println("Winner: "+account.getNickname());
            writer.flush();
        }

    }

    private void nextRound(String msg){
        //Life가 있는 유저인지 확인
        if(account.isLife()){
            Account user = playUser.get(msg);
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
        for(Account user : playUser.values()){
            if(user.isHost()){
                prevHostNick=user.getNickname();
                user.setHost(false);
            }
        }

        for(Account user : playUser.values()){
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
        playUser.remove(account.getNickname());
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
