import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerThread extends Thread {
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;
	private StringTokenizer st;
	private Account account;
	Map<String, Account> playUser;
	Map<String, Account> userDB;
	ArrayList<Point> seats;
	static ArrayList<String> killed = new ArrayList<>();
	AtomicInteger userLifes;
	private final int MOVE_SIZE = 10;

	static int round;


	ServerThread(Socket socket, Map<String, Account> playUser, ArrayList<Point> seats, Map<String, Account> userDB,
				 AtomicInteger userLifes) {
		this.socket = socket;
		this.playUser = playUser;
		this.userDB = userDB;
		this.seats = seats;
		this.userLifes = userLifes;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void run() {
		// 실행 로직 선택숫자
		String num = "";
		// 클라이언트로부터 넘겨받는 메세지
		String msg = "";
		try {
			while (true) {
				if ((msg = reader.readLine()) != null) {
					st = new StringTokenizer(msg, "#");
					num = st.nextToken();
					System.out.println(msg);
					switch (num) {
						case "1":
							// 회원가입
							msg = st.nextToken();
							signUp(msg);
							break;
						case "2":
							// 로그인
							msg = st.nextToken();
							signIn(msg);
							break;
						case "3":
							// 게임시작
							msg = st.nextToken();
							startGame(msg);
							break;
						case "4":
							if (st.hasMoreTokens()) {
								msg = st.nextToken() + " word";
								move(msg);
							}
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
						case "8":
							msg = "3005#" + st.nextToken();
							broadCast(msg);
							break;
						case "9":
							msg = st.nextToken();
							broadCast(msg);
							break;

					}
				}
			}
		} catch (IOException e) {
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


	private void signUp(String msg) {
		// 닉네임 비밀번호를 공백을 기준으로 입력받음
		st = new StringTokenizer(msg, " ");
		String nickname = st.nextToken();
		String password = st.nextToken();

		// 이미 존재하는 닉네임 일경우
		if (userDB.containsKey(nickname)) {
			writer.println("4000#error");
			writer.flush();
		} else {
			this.account = new Account(nickname, password);
			userDB.put(nickname, account);
			writer.println("3000#SUCCESS");
			writer.flush();
		}
	}

	private void signIn(String msg) {
		// 닉네임 비밀번호를 공백을 기준으로 입력받음
		st = new StringTokenizer(msg, " ");
		String nickname = st.nextToken();
		String password = st.nextToken();

		// 존재하지 않는 닉네임 일경우
		if (!userDB.containsKey(nickname)) {
			writer.println("4001#error");
			writer.flush();
		} else if (!userDB.get(nickname).getPassword().equals(password)) {
			writer.println("4002#error");
			writer.flush();
		} else {
			//가장 먼저 로그인해서 방에 입장한 유저에게 host 부여
			account = userDB.get(nickname);
			playUser.put(nickname, account);

			account.setWriter(writer);
			if (playUser.size() == 1) {
				playUser.get(nickname).setHost(true);
				writer.println("3002#Host is: " + nickname);// ȣ��Ʈ ����
				writer.flush();
			} else if (playUser.size() > 4) {
				writer.println("4003#error");
				writer.flush();
			} else
				playUser.get(nickname).setHost(false);
			broadCast("3001#" + nickname + "이(가) 입장했습니다." + "현재인원: (" + playUser.size() + "/4)");
		}
	}

	private void startGame(String id) {


		String prevHostNick = "";
		for (Account user : playUser.values()) {
			if (user.isHost()) {
				prevHostNick = user.getNickname();
			}
		}
		if(!prevHostNick.equals(id)) {
			broadCast("3011#"+id);
			return;
		}
		seats.clear();
		killed.clear();
		round = 1;
		if (playUser.size() < 1) {
			writer.println("4004#error");
			writer.flush();
		} else {
			broadCast("3003#After 3 seconds, Game Start");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			broadCast("3010#Game Start");

			for (Account user : playUser.values()) {
				user.setSeated(false);
				user.setLife(true);
				userLifes.incrementAndGet();
				user.setPoint(randomUserLocation());

				broadCast("3004#" + user.getNickname() + " " + user.getPoint());
			}

			// 게임 시작 유저 4명 고정이므로 3개 의자 랜덤 위치 생성
			String p1 = randomSeatLocation().toString();
			String p2 = randomSeatLocation().toString();
			String p3 = randomSeatLocation().toString();

			broadCast("Chair location is " + p1);
			broadCast("Chair location is " + p2);
			broadCast("Chair location is " + p3);

			broadCast("3005#" + p1 + "#" + "3");
			broadCast("3005#" + p2 + "#" + "2");
			broadCast("3005#" + p3 + "#" + "1");

		}

	}

	private Point randomUserLocation() {
		// 랜덤 유저 위치 할당
		int x = (int) (Math.random() * 50 + 1) * MOVE_SIZE;
		int y = (int) (Math.random() * 50 + 1) * MOVE_SIZE;

		return new Point(x, y);
	}

	private Point randomSeatLocation() {
		boolean flag = true;
		Point seat = null;
		// 현재 유저 위치의 격자판을 제외하 의자 위치 생성
		while (true) {
			// 랜덤 의자 위치 할당
			int x = (int) (Math.random() * 50 + 1) * MOVE_SIZE;
			int y = (int) (Math.random() * 50 + 1) * MOVE_SIZE;

			seat = new Point(x, y);
			for (Account user : playUser.values()) {
				if (user.getPoint().equals(seat))
					flag = false;
			}
			if (flag)
				break;
		}

		seats.add(seat);
		return seat;
	}

	private void move(String msg) {

		// msg => 유저 닉네임 + 'W' or 'A' or 'S' or 'D'
		st = new StringTokenizer(msg, " ");
		String nickname = st.nextToken();
		String key = st.nextToken();

		Account user = playUser.get(nickname);
		Point userPoint = user.getPoint();

		for (String a : killed) {
			broadCast("3009#" + a);
		}
		if (killed.contains(nickname)) {
		} else if (key.equalsIgnoreCase("W")) {
			moveW(user, userPoint);
		} else if (key.equalsIgnoreCase("A")) {
			moveA(user, userPoint);
		} else if (key.equalsIgnoreCase("S")) {
			moveS(user, userPoint);
		} else if (key.equalsIgnoreCase("D")) {
			moveD(user, userPoint);
		} else {
			writer.println("4005#error");
			writer.flush();
		}

		userPoint = user.getPoint();

		//움직인 유저의 닉네임과 변경된 위치 반환
		broadCast("3006#" + nickname + " " + userPoint.toString());

		//남은 의자 좌석수 확인
		if (!isSeatNum()) {
			broadCast("3010#----Finish Round----");

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			finishRound();

		}

	}

	private void moveW(Account user, Point userPoint) {
		user.setPoint(new Point(userPoint.getX(), userPoint.getY() - 10));
		checkSeat(user);
	}

	private void moveA(Account user, Point userPoint) {
		user.setPoint(new Point(userPoint.getX() - 10, userPoint.getY()));
		checkSeat(user);
	}

	private void moveS(Account user, Point userPoint) {
		user.setPoint(new Point(userPoint.getX(), userPoint.getY() + 10));
		checkSeat(user);
	}

	private void moveD(Account user, Point userPoint) {
		user.setPoint(new Point(userPoint.getX() + 10, userPoint.getY()));
		checkSeat(user);
	}

	// 유저가 이동한 위치가 의자인지 확인
	private void checkSeat(Account user) {

		int cnt = 0;
		for (Iterator<Point> itr = seats.iterator(); itr.hasNext();) {

			Point tmp = itr.next();
			System.out.println(tmp);
			if (tmp.equals(user.getPoint())) {
				System.out.println("user finds seat " + user.getPoint());
				user.setSeated(true);
				itr.remove();
				seats.remove(tmp);
				cnt++;

			}

		}
	}

	private boolean isSeatNum() {
		return seats.size() != 0;
	}

	private void finishRound() {
		for (Account user : playUser.values()) {
			if (user.isLife() && !user.isSeated()) {
				user.setLife(false);
				userLifes.decrementAndGet();
				broadCast("Fail user: " + user.getNickname());
				killed.add(user.getNickname());
				seats.clear();
			}
		}

		if (userLifes.get() == 1 || round == 3) {
			broadCast("3010#Winner: " + account.getNickname());
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			broadCast("3008#GameEnd");
		} else {
			nextRound();
		}

	}

	private void nextRound() {
		broadCast("3010#NextRound");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		broadCast("3010#----Next Round----");

		for (Account user : playUser.values()) {
			// Life가 있는 유저인지 확인
			if (account.isLife()) {
				user.setSeated(false);
				user.setPoint(randomUserLocation());

				broadCast("3004#" + user.getNickname() + " " + user.getPoint());
			}
		}
		round++;
		System.out.println(round);
		if (round == 2) {
			broadCast("3005#" + randomSeatLocation().toString() + "#" + "3");
			broadCast("3005#" + randomSeatLocation().toString() + "#" + "2");

		} else if (round == 3) {
			broadCast("3005#" + randomSeatLocation().toString() + "#" + "3");
		} else {
			finishRound();
		}

	}

	// host가 10초안에 게임 시작 안할 시 변경
	private void changeHost() {
		String prevHostNick = "";
		for (Account user : playUser.values()) {
			if (user.isHost()) {
				prevHostNick = user.getNickname();
				user.setHost(false);
			}
		}

		for (Account user : playUser.values()) {
			if (!user.isHost() && !user.getNickname().equals(prevHostNick)) {
				user.setHost(true);
				broadCast("New host: " + user.getNickname());
			}
		}

	}

	private void exit() {
		if (account.isHost())
			changeHost();
		broadCast("Exit user: " + account.getNickname());
		broadCast("현재 인원(" + playUser.size() + "/4)");
		playUser.remove(account.getNickname());
		try {
			socket.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void broadCast(String msg) {
		playUser.values().forEach(user -> {
			PrintWriter writer = user.getWriter();
			writer.println(msg);
			writer.flush();
		});
	}

}
