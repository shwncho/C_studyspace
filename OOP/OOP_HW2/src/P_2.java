import java.util.Scanner;
/**
 * Program to move the first word to the end of the line
 * Author: ChoSukHwan
 * E-mail Address: sukhwan5027@naver.com
 * Programming Assignment2's Problem 2
 * Last Changed: March 25, 2022.
 */
public class P_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str; // user's input
        String firstWord; // original sentence's firstword
        String firstLetter; // new sentence's firstletter

        System.out.println("Enter a line of text. No punctuation please.");
        str=sc.nextLine();
        System.out.println("I have rephrased that line to read:");

        //the first word moved to the end of the line.
        firstWord=str.split(" ")[0];
        str=str.replace(firstWord,"");
        str=str.trim();
        str=str+" "+firstWord;

        //the new first word with a capital letter
        firstLetter=str.substring(0,1).toUpperCase();
        str=firstLetter+str.substring(1);

        System.out.println(str);








    }
}
