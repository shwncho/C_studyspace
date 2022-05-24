import java.io.*;

public class BinaryInputDemo {
    public static void main(String[] args) {
        String fileName = "numbers.dat";
        try{
            ObjectInputStream inputStream =
                    new ObjectInputStream(new FileInputStream(fileName));
            System.out.println("Reading the nonnegative integers");
            System.out.println("in the file " + fileName);
            int anInteger = inputStream.readInt();
            while(true){
                System.out.println(anInteger);
                anInteger = inputStream.readInt();
            }



        } catch(FileNotFoundException e){
            System.out.println("Problem opening the file " + fileName);
        } catch(EOFException e){
            System.out.println("Problem reading the file " + fileName);
            System.out.println("Reached end of the file.");
        } catch(IOException e){
            System.out.println("Problem reading the file " + fileName);
        }
        System.out.println("End of reading from file.");
    }
}
