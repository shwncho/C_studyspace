public class Main {
    static int a,b,r1,s1,t1;
    public static void solution(int a, int b){
        r1=a; int r2=b;
        s1=1; int s2=0;
        t1=0; int t2=1;

        while(r2>0){
            int q=r1/r2;
            int r=r1-q*r2;

            r1=r2;
            r2=r;

            int s =s1-q*s2;
            s1=s2;
            s2=s;

            int t =t1-q*t2;
            t1=t2;
            t2=t;
        }
    }
    public static void main(String[] args) {
        a=17;
        b=17;
        solution(a,b);
        System.out.println(s1+" "+t1+" "+r1);
    }
}
