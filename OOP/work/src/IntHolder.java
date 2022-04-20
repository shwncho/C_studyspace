public class IntHolder{
    public int value = 0;

    static void swap(IntHolder a, IntHolder b){
        int tmp = a.value;
        a.value = b.value;
        b.value = tmp;
    }

    public static void main(String[] args) {
        IntHolder n1 = new IntHolder();
        IntHolder n2 = new IntHolder();

        n1.value=10;
        n2.value=20;

        swap(n1,n2);

        System.out.println("n1 value= " + n1.value);
        System.out.println("n2 value= " + n2.value);
    }
}
