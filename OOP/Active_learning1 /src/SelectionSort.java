public class SelectionSort {

    public void sort(int[] arr){
        int len = arr.length;
        for(int i=0; i<len-1; i++){
            int minIdx=i;
            for(int j=i+1; j<len; j++){
                if(arr[j]<arr[minIdx]){
                    minIdx=j;
                }
            }
            swap(arr,i,minIdx);
        }
    }


    public void swap(int[] arr, int i, int j){
        int tmp = arr[i];
        arr[i]=arr[j];
        arr[j]=tmp;
    }

    public void printArray(int[] arr){
        int len = arr.length;
        for(int i=0; i<len; i++){
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }

}
