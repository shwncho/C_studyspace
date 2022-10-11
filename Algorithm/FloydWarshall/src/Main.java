import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[][] dis = new int[5][5];

        for(int i=0; i<5; i++){
            Arrays.fill(dis[i],1000000);
        }

        for(int i=0; i<5; i++){
            dis[i][i]=0;
        }

        dis[0][1]=2;
        dis[0][4]=3;
        dis[1][2]=1;
        dis[1][3]=3;
        dis[1][4]=-1;
        dis[2][0]=4;
        dis[2][3]=1;
        dis[3][4]=1;
        dis[4][1]=2;
        dis[4][2]=3;

        for(int k=0; k<5; k++){
            for(int i=0; i<5; i++){
                for(int j=0; j<5; j++){
                    if(dis[i][j]>dis[i][k]+dis[k][j]){
                        dis[i][j]=dis[i][k]+dis[k][j];
                    }
                }
            }
        }

        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                if(dis[i][j]>1000000){
                    System.out.print('&'+" ");
                }
                else System.out.print(dis[i][j]+" ");
            }
            System.out.println();
        }



    }
}
