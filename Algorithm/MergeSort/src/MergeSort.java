public class MergeSort {
    static int[] arr={8,2,4,9,3,6};

    public static void mergeSort(int[] a, int left, int right){
        if(left<right){
            int mid = (left+right)/2;
            mergeSort(a, left, mid);
            mergeSort(a, mid+1, right);
            merge(a, left, mid, right);

        }
    }

    public static void merge(int[] a, int left, int mid, int right){
        int i=left;
        int j=mid+1;
        int t=0;
        int[] tmp = new int[a.length];

        // mid와 right 인덱스를 최대값으로 설정해놓고, 서브 리스트의 값들을 비교해서 tmp배열에 정렬해서 넣기
        while(i<=mid && j<=right){
            if(a[i]<=a[j]){
                tmp[t]=a[i];
                t++;
                i++;
            }
            else{
                tmp[t]=a[j];
                t++;
                j++;
            }
        }
        // 위 과정이 끝난 뒤에도 left 서브 리스트에 남아있는 요소들이 있다면 추가
        while(i<=mid){
            tmp[t]=a[i];
            t++;
            i++;
        }
        // 위 과정이 끝난 뒤에도 right 서브 리스트에 남아있는 요소들이 있다면 추가
        while(j<=right){
            tmp[t]=a[j];
            t++;
            j++;
        }
        i=left;
        t=0;

        //정렬된 tmp배열의 값을 원래 배열에 덮어쓰기
        while(i<=right){
            a[i]=tmp[t];
            t++;
            i++;
        }
    }

    public static void main(String[] args) {

        mergeSort(arr,0,arr.length-1);
        for (int x : arr) {
            System.out.print(x+" ");
        }
    }
}
