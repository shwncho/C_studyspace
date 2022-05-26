import java.util.ArrayList;

public class ExampleCode {
    public static void main(String[] args) {
        ArrayList<Integer> array1 = new ArrayList<>();
        System.out.println(array1);

        array1.add(10);
        array1.add(20);
        array1.add(30);
        array1.add(40);
        System.out.println();
        System.out.println("List added");
        System.out.println(array1);

        array1.add(2,25);
        System.out.println();
        System.out.println("List added using value with index");
        System.out.println(array1);

        array1.remove(1);
        System.out.println();
        System.out.println("List deleted");
        System.out.println(array1);

        System.out.println();
        System.out.println("Get the value in index=2");
        System.out.println(array1.get(2));

        System.out.println();
        System.out.println("Get the size a ArrayList");
        System.out.println(array1.size());

        array1.set(1,100);
        System.out.println();
        System.out.println("List using set");
        System.out.println(array1);

    }
}
