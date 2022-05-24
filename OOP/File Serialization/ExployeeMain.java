import java.io.*;
public class ExployeeMain {
    public static void main(String[] args) {
        File file = new File("test_serialization.txt");

        try{
            FileOutputStream foStream = new FileOutputStream(file);
            ObjectOutputStream oStream = new ObjectOutputStream(foStream);
            Employee employee = new Employee();
            employee.setId(1);
            employee.setName("Tom");
            employee.setDepartment("SW@GCU");
            employee.setExpLevel(5.0);
            System.out.println("Object values to be serialized = " +employee.getName() +";"+employee.getId()+";"+employee.getDepartment()+";"+employee.getExpLevel());
            oStream.writeObject(employee);
            ObjectInputStream iStream = new ObjectInputStream(new FileInputStream(file));
            Employee emp = (Employee)iStream.readObject();
            System.out.println("De serialized object values = " +emp.getName()+";"+emp.getId()+";"+emp.getDepartment()+";"+emp.getExpLevel());
            oStream.close();
            iStream.close();

        } catch(FileNotFoundException fn){
            fn.printStackTrace();
        } catch(IOException io){
            io.printStackTrace();
        } catch(ClassNotFoundException cnf){
            cnf.printStackTrace();
        }
    }
}
