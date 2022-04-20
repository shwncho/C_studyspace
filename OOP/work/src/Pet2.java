public class Pet2 {
    private String name;
    private int age;
    private double weight;

    public Pet2(String initialName, int initialAge, double initialWeight){
        set(initialName, initialAge, initialWeight);
    }
    public Pet2(String initialName){
        set(initialName, 0,0);
    }
    public Pet2(int initialAge){
        set("No name yet", initialAge, 0);
    }
    public Pet2(double initialWeight){
        set("No name yet",0,initialWeight);
    }
    public Pet2(){
        set("No name yet", 0, 0);
    }

    public void setName(String name) {
        set(name,age,weight);
    }

    public void setAge(int age) {
        set(name,age,weight);
    }

    public void setWeight(double weight) {
        set(name,age,weight);
    }
    public void setPet(String newName, int newAge, double newWeight){
        set(newName, newAge, newWeight);
    }

    private void set(String newName, int newAge, double newWeight){
        name=newName;
        if((newAge<0) || (newWeight<0)){
            System.out.println("Error Negative age or weight");
            System.exit(0);
        }
        else{
            age=newAge;
            weight=newWeight;
        }
    }
}
