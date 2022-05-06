public class SortDemo {
    public static void main(String[] args) {
        SelectionSort ss = new SelectionSort();
        int[] arr = {7,5,11,2,16,4,18,14,12,30};
        System.out.print("Before Sorting array: ");
        ss.printArray(arr);
        ss.sort(arr);
        System.out.print("After Sorting array: ");
        ss.printArray(arr);
    }
}
