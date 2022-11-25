import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] vcolor;
    static boolean[][] W;
    static int n;
    static int cnt=0;
    static boolean promising(int x){
        int j=1;

        while(j<x){
            if(W[x][j] && vcolor[j-1] == vcolor[j])   return false;
            j++;
        }
        return true;
    }
    static void DFS(int x){
        if(x==n)    cnt++;
        else{
            for(int color=1; color<=2; color++){
                vcolor[x]=color;
                if(promising(x))    DFS(x+1);
            }
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // 노드 n개 m개의 간선을 입력받는다고 가정 -> n x m 그래프
        st=new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        vcolor=new int[n+1];
        W=new boolean[n+1][n+1];
        cnt=0;
        for(int j=0; j<m; j++){
            st=new StringTokenizer(br.readLine());
            int a =Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            W[a][b]=true;
            W[b][a]=true;
        }
        DFS(1);
        System.out.println(cnt);



    }
}
