import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] vcolor; // 각 노드별 색깔
    static boolean[][] W; // State Space Tree
    //n x n 배열
    static int n;
    // 사용한 색깔 갯수
    static int m;
    //색칠가능한 경우의 수
    static int cnt=0;
    static boolean promising(int i){
        //State Space Tree 탐색
        int j=1;
        boolean flag=true;
        while(j<i && flag){
            //(i,j)가 연결되어 있으며, 두 노드의 색깔이 같은 경우
            if(W[i][j] && vcolor[i] == vcolor[j]){
                flag=false;
            }
            j++;
        }
        return flag;
    }
    static void DFS(int i){
        if(promising(i)){
            if(i==n){
                cnt++;
            }
            else{
                for(int color=1; color<=m; color++){
                    vcolor[i+1]=color;
                    DFS(i+1);
                }
            }

        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // n x n 그래프
        n = Integer.parseInt(br.readLine());

        vcolor=new int[n+1];
        W=new boolean[n+1][n+1];
        for(int i=1; i<=n; i++){
            st=new StringTokenizer(br.readLine());
            for(int j=1; j<=n; j++){
                if(Integer.parseInt(st.nextToken())==1){
                    W[i][j]=true;
                    W[j][i]=true;
                }
            }
        }
        m=3;
        DFS(1);
        System.out.println(cnt);



    }
}
