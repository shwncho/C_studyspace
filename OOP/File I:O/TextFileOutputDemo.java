import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TextFileOutputDemo {
    public static void main(String[] args) {
        String fileName = "out.txt";
        PrintWriter outputStream = null;
        try{
            outputStream = new PrintWriter(fileName);
        }
        catch (FileNotFoundException e){
            System.out.println("Error opening the file " + fileName);
            System.exit(0);

        }
        System.out.println("Enter three lines of text: ");
        Scanner sc =new Scanner(System.in);
        for(int cnt=1; cnt<=3; cnt++){
            String line = sc.nextLine();
            outputStream.println(cnt+" "+line);
        }
        outputStream.close();
        System.out.println("Those lines were written to " + fileName);
    }
}
