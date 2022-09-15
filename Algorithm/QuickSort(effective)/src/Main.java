import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int k;
    static void swap(int[] arr, int i, int j){
        int tmp = arr[i];
        arr[i]=arr[j];
        arr[j]=tmp;
    }

    static void quickSort(int[] arr, int left, int right){
        if(left>=right) return;

        // 역정렬인 경우 O(n^2)이 나올 수 있으므로 pivot을 mid값으로 설정
        int pivot = arr[(left+right)/2];
        //임시 인덱스로 변수i,j 할당
        int i = left;
        int j = right;
        while(i<=j){
            //pivot을 기준으로 왼쪽은 작은값이, 오른쪽은 큰 값이 올바르게 있는지 확인.
            //올바르게 있으면 증가or감소
            while(arr[i]<pivot) i++;
            while(arr[j]>pivot) j--;
            //pivot의 왼쪽에 pivot보다 큰 값이, pivot의 오른쪽에 pivot보다 작은 값이 있으면 i,j인덱스는 멈춘후 swap
            if(i<=j){
                swap(arr,i,j);
                //다음 단계를 진행하기위해 인덱스 증가or감소
                i++;
                j--;
            }
        }
        //mid인덱스 기준 왼쪽 배열과, 오른쪽 배열 재귀방식으로 quickSort진행
        quickSort(arr,left,j);
        quickSort(arr,i,right);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        int[] arr = new int[n];

        st=new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++){
            arr[i]=Integer.parseInt(st.nextToken());
        }

        quickSort(arr,0,n-1);
        System.out.println(arr[k-1]);


    }
}
