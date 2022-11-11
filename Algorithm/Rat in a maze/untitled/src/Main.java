import java.util.Scanner;

public class Main {
    static int answer=0;
    static int[][] graph;
    //12시 3시 6시 9시 시계방향으로 x,y값의 변화
    static int[] dx={-1,0,1,0};
    static int[] dy={0,1,0,-1};
    public static void DFS(int x, int y){
        //탈출할 수 있는 경우의 수 누적(answer)
        if(x==4 && y==4)    answer++;
        else{
            for(int i=0; i<4; i++){
                int nextX = x+dx[i];
                int nextY = y+dy[i];
                if(nextX>=1 && nextX<=4 && nextY>=1 && nextY<=4 && graph[nextX][nextY]==1){
                    graph[x][y]=0;
                    DFS(nextX,nextY);
                    graph[x][y]=1;
                }
            }
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        graph=new int[5][5];

        for(int i=1; i<=4; i++) {
            for (int j = 1; j <= 4; j++) {
                graph[i][j] = sc.nextInt();
            }
        }
        graph[1][1]=0;
        DFS(1,1);
        System.out.println(answer);
    }
}