import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int min(int x, int y, int z){
        if(x<=y && x<=z)    return x;
        else if(y<=x && y<=z)   return y;
        else    return z;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char[] str1 = br.readLine().toCharArray();
        char[] str2 = br.readLine().toCharArray();

        int[][] dp = new int[str1.length+1][str2.length+1];
        for(int i=0; i<=str1.length; i++){
            for(int j=0; j<=str2.length; j++){

                if(i==0)    dp[i][j]=j;
                else if(j==0)   dp[i][j]=i;
                else if(str1[i-1]==str2[j-1])    dp[i][j]=dp[i-1][j-1];
                else{
                    dp[i][j]=1+min(dp[i][j-1],dp[i-1][j],dp[i-1][j-1]);
                }
            }
        }

        System.out.println(dp[str1.length][str2.length]);

    }
}
