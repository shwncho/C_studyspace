public class Pet {
    private String name;
    private int age;
    private double weight;

    public Pet(){
        name = "No name yet.";
        age = 0;
        weight = 0;
    }

    public Pet(String initName, int initAge, double initWeight){
        name = initName;
        age = initAge;
        weight = initWeight;
    }

    public static void main(String[] args) {
        Pet p = new Pet();
        Pet q = new Pet("Garfield", 3, 10);
    }
}

