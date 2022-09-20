import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int min(int x, int y, int z){
        if(x<=y && x<=z)    return x;
        else if(y<=z && y<=x) return y;
        else    return z;
    }

    static int editDistance(char[] str1, char[] str2, int n, int m){
        if(n==0)    return m;
        if(m==0)    return n;
        if(str1[n-1]==str2[m-1])    return editDistance(str1, str2, n-1, m-1);
        return 1+min(editDistance(str1, str2, n, m-1),
                editDistance(str1, str2, n-1, m),
                editDistance(str1, str2, n-1, m-1));
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char[] str1 = br.readLine().toCharArray();
        char[] str2 = br.readLine().toCharArray();


        System.out.println(editDistance(str1,str2,str1.length,str2.length));
    }
}
