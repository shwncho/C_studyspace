import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];
        int tmp=0;

        for(int i=0; i<n; i++){
            arr[i]=Integer.parseInt(br.readLine());
        }

        for(int i=1; i<n; i++){
            for(int j=i; j>0; j--){
                if(arr[j]<arr[j-1]){
                    tmp=arr[j];
                    arr[j]=arr[j-1];
                    arr[j-1]=tmp;
                }
                else    break;

            }
        }

        for (int x : arr) {
            System.out.println(x);
        }

    }
}
