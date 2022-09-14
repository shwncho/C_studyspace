public class Main {
    static void swap(int[] arr, int i, int j){
        int tmp = arr[i];
        arr[i]=arr[j];
        arr[j]=tmp;
    }

    static void quickSort(int[] arr, int left, int right){
        if (left<right){
            //p는 partition이 된 수
            int p = partition(arr,left,right);
            quickSort(arr,left, p-1);
            quickSort(arr,p+1, right);
        }
    }


    static int partition(int[] arr, int left, int right){
        //pivot을 마지막 원소로 정함
        int pivot = arr[right];
        int i=left-1;
        for(int j=left; j<right; j++){
            if(arr[j]<pivot){
                i++;
                swap(arr,i,j);
            }
        }
        swap(arr,i+1,right);
        return i+1;
    }
    public static void main(String[] args) {
        int[] arr = {5,8,3,4,7,9};
        quickSort(arr,0,arr.length-1);
        for (int i : arr) {
            System.out.print(i+" ");

        }
    }
}
