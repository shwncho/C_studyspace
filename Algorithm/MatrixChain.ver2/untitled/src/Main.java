import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int solution(int[] p, int n){
        int[][] m =new int[n][n];

        for(int i=0; i<n; i++){
            m[i][i]=0;
        }
        for(int L=2; L<n; L++){
            for(int i=1; i<n-L+1; i++){
                int j =i+L-1;
                m[i][j]=Integer.MAX_VALUE;
                for(int k=i; k<j; k++){
                    int q =m[i][k]+m[k+1][j]+p[i-1]*p[k]*p[j];
                    m[i][j]=Math.min(m[i][j],q);
                }
            }
        }
        //위 식에서 i=1부터, j는 최대 n-1까지로 설정해서 풀었으므로 m[1][n-1]에 최소값 누적
        return m[1][n-1];

    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int n =Integer.parseInt(br.readLine());
        int[] arr = new int[n+1];
        for(int i=0; i<n; i++){
            st=new StringTokenizer(br.readLine());
            arr[i]=Integer.parseInt(st.nextToken());
            arr[i+1]=Integer.parseInt(st.nextToken());
        }
        System.out.println(solution(arr, arr.length));

    }
}
