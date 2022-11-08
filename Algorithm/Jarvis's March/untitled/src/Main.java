import java.util.ArrayList;

class Point{
    public int x;
    public int y;
    Point(int x, int y){
        this.x = x;
        this.y = y;
    }
}
public class Main {
    static int leftIndex(Point[] p){
        int min =0;
        for(int i=1; i<p.length; i++){
            if(p[i].x < p[min].x)   min=i;
            else if(p[i].x==p[min].x)
                if(p[i].y>p[min].y) min=i;
        }
        return min;
    }

    static int orientation(Point p, Point q, Point r){
        int val = (q.y-p.y)*(r.x-q.x)-(q.x-p.x)*(r.y-q.y);

        if(val==0)  return 0;
        else if(val>0)  return 1;
        else return 2;
    }

    static void convexHull(Point[] points, int n){
        if(n<3) return;

        int l =leftIndex(points);

        ArrayList<Integer> hull=new ArrayList<>();

        int p = l;
        int q =0;
        while(true){
            hull.add(p);
            q=(p+1)%n;
            for(int i=0; i<n; i++){
                if(orientation(points[p],points[i],points[q])==2)   q=i;
            }
            p=q;

            if(p==l) break;
        }

        for(int each : hull){
            System.out.println(points[each].x+" "+points[each].y);
        }
    }
    public static void main(String[] args) {
        Point[] points={
                new Point(0,3), new Point(1,1), new Point(2,2),
                new Point(4,4), new Point(0,0), new Point(1,2),
                new Point(3,1), new Point(3,3)};

        convexHull(points, points.length);
    }
}
