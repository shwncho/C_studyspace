import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        //입력받을 숫자의 개수
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];
        int tmp=0;

        //배열에 수 입력받기
        for(int i=0; i<n; i++){
            arr[i]=Integer.parseInt(br.readLine());
        }

        for(int i=1; i<n; i++){
            for(int j=i; j>0; j--){
                //j-1번째 요소의 값보다 j번째 요소의 값이 작다면 switch
                if(arr[j]<arr[j-1]){
                    tmp=arr[j];
                    arr[j]=arr[j-1];
                    arr[j-1]=tmp;
                }
                //j번째 보다 앞의 요소들의 값은 정렬되어 있으므로 정렬할게 없다면 종료
                else    break;

            }
        }

        for (int x : arr) {
            System.out.println(x);
        }

    }
}
