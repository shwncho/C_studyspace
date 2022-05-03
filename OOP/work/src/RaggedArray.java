public class Main {
    public static void average(int[][] data){
        double sum = 0.0;
        int cnt=0;
        for(int row=0; row<data[row].length; row++){

            if(data[row].length>2){
                sum+=data[row][2];
                cnt++;
            }
        }
        System.out.println(sum/cnt);


    }
    public static void main(String[] args) {

        int[][] arr={{3,5,7,9},{4,2},{5,7,8,6},{6}};

        average(arr);



    }
}
