import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int[][] dp;
    public static int lcs(char[] str1, char[] str2, int n, int m){
        if(n==0 || m==0)    return 0;
        else if(str1[n-1]==str2[m-1])   return 1+lcs(str1,str2,n-1,m-1);
        else    return Math.max(lcs(str1,str2,n-1,m),lcs(str1,str2,n,m-1));
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char[] str1 = br.readLine().toCharArray();
        char[] str2 = br.readLine().toCharArray();

        int n=str1.length;
        int m=str2.length;

        dp = new int[n+1][m+1];

        System.out.println(lcs(str1,str2,n,m));





    }
}
