import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
class Point{
    public double x;
    public double y;
    public int idx;
    Point(double x, double y,int idx){
        this.x=x;
        this.y=y;
        this.idx=idx;
    }
}
class Edge implements Comparable<Edge>{
    public Point v1;
    public Point v2;
    public double cost;
    Edge(Point v1, Point v2){
        this.v1=v1;
        this.v2=v2;
        this.cost=Math.sqrt(Math.pow((v1.x-v2.x),2)+Math.pow((v1.y-v2.y),2));
    }

    @Override
    public int compareTo(Edge o){
        return Double.compare(this.cost,o.cost);
    }
}
public class Main {
    static int[] unf;
    static int Find(int v){
        if(v==unf[v])   return v;
        else return unf[v]=Find(unf[v]);
    }
    static void Union(int a, int  b){
        int fa = Find(a);
        int fb = Find(b);
        if(fa!=fb)  unf[fa]=fb;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());

        ArrayList<Point>pList = new ArrayList<>();
        ArrayList<Edge> eList=new ArrayList<>();

        unf=new int[n+1];

        for(int i=1; i<=n; i++) unf[i]=i;
        for(int i=0; i<n; i++){
            st=new StringTokenizer(br.readLine());
            double a = Double.parseDouble(st.nextToken());
            double b = Double.parseDouble(st.nextToken());
            pList.add(new Point(a,b,i+1));
        }

        for(int i=0; i<n; i++){
            for(int j=i+1; j<n; j++){
                eList.add(new Edge(pList.get(i),pList.get(j)));
            }
        }

        double answer=0.0;
        Collections.sort(eList);



        for(Edge o : eList){
            int fv1 = Find(o.v1.idx);
            int fv2 = Find(o.v2.idx);
            if(fv1!=fv2){
                answer+=o.cost;
                Union(o.v1.idx,o.v2.idx);
            }
        }

        System.out.printf("%.2f", answer);
    }
}
