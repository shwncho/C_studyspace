import java.io.*;

public class CopyLines {
    public static void main(String[] args) throws IOException {
        BufferedReader inputStream = null;
        PrintWriter outputStream = null;

        try{
            inputStream = new BufferedReader(new FileReader("out.txt"));
            outputStream = new PrintWriter(new FileWriter("dest.txt"));

            String l;
            while((l= inputStream.readLine())!=null){
                outputStream.println(l);
            }
        } finally{
            //할당된 애가 아직 있으면 close
            if(inputStream!=null){
                inputStream.close();
            }
            if(outputStream!=null){
                outputStream.close();
            }
        }

    }
}
