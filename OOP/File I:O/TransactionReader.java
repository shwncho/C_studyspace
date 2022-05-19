import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TransactionReader {
    public static void main(String[] args) {
        String fileName = "Transactions.txt";
        try{
            Scanner inputStream = new Scanner(new File(fileName));
            //필요없는 header line을 제거해주기 위해 사용
            String line = inputStream.nextLine();
            double total=0;

            while(inputStream.hasNextLine()){
                line = inputStream.nextLine();
                String[] ary = line.split(",");
                String SKU = ary[0];
                int quantity = Integer.parseInt(ary[1]);
                double price = Double.parseDouble(ary[2]);
                String description = ary[3];
                System.out.printf("Sold %d of %s (SKU: %s) at $%1.2f each.\n", quantity,description,SKU,price);

                total+=quantity*price;
            }
            System.out.printf("Total sales: $%1.2f\n",total);
            inputStream.close();
        } catch(FileNotFoundException e){
            System.out.println("Cannot find file " + fileName);
        } catch(Exception e){
            System.out.println("Problem with input from file " + fileName);
        }
    }
}
