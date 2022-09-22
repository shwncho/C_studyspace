import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());

        int[][]mat = new int[n][2];
        int[][]dp = new int[n][n];

        for(int i=0; i<n; i++){
            st=new StringTokenizer(br.readLine());
            mat[i][0]=Integer.parseInt(st.nextToken());
            mat[i][1]=Integer.parseInt(st.nextToken());
        }

        for(int i=1; i<n; i++){
            for(int j=0; j<n-i; j++){
                dp[j][j+i]=Integer.MAX_VALUE;
                for(int k=0; k<i; k++){
                    int value = dp[j][j+k] + dp[j+k+1][j+i]+mat[j][0]*mat[j+k][1]*mat[j+i][1];
                    dp[j][j+i] = Math.min(dp[j][j+i],value);
                }
            }
        }
        System.out.println(dp[0][n-1]);

    }
}