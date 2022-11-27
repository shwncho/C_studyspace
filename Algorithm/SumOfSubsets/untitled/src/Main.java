import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static int[] w,x;
    //목표 합
    static int W=35;
    //부분집합의 합과 일치하는 답의 개수
    static int cnt=0;
    static boolean promising(int i, int weight, int total){
        return weight + total >= W && (weight == W || weight + w[i + 1] <= W);
    }
    static void sumOfSubSet(int i, int weight, int total){
        if(promising(i,weight,total)){
            if(W==weight){
                cnt++;
            }
            else{
                x[i+1]=w[i+1];
                sumOfSubSet(i+1,weight+w[i+1],total-w[i+1]);
                x[i+1]=0;
                sumOfSubSet(i+1,weight,total-w[i+1]);
            }
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        w=new int[7];
        x=new int[7];
        int total=0;
        for(int i=0; i<7; i++){
            w[i]=sc.nextInt();
            total+=w[i];
        }
        Arrays.sort(w);
        sumOfSubSet(-1,0,total);
        System.out.println(cnt);

    }

}
