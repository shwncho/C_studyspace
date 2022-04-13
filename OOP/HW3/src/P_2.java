import java.util.Scanner;
/**
 * Program to display other result depending on last character
 * Author: Cho Suk Hwan
 * E-mail Address: sukhwan5027@naver.com
 * Programming Assignment 3.
 * Last Changed: April 8, 2022
 */
public class P_2 {
    public static void main(String[] args) {
        String str; //user's input
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter a one line question or statement.");
        str= sc.nextLine();

        if(str.endsWith("?")){
            int num=0;
            for(int i=0; i<str.length(); i++){
                if(Character.isDigit(str.charAt(i)))    num=Character.getNumericValue(str.charAt(i));
            }
            if(num%2==0) System.out.println("Yes");
            else System.out.println("No");

        }
        else if(str.endsWith("!")) System.out.println("Wow");
        else{
            System.out.println("You always say " + "\""+str+"\"");
        }
    }
}
