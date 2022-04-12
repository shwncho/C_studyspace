import java.util.Scanner;
/**
 * Program to convert a 4-bit binary number into decimal
 * Author: ChoSukHwan
 * E-mail Address: sukhwan5027@naver.com
 * Programming Assignment2's Problem 5
 * Last Changed: March 25, 2022.
 */
public class P_5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int b0,b1,b2,b3,result;
        String str; //user's input(4-bit binary number)

        System.out.print("Enter a 4-bit binary number:");
        str=sc.next();

        b0=8*Integer.parseInt(str.substring(0,1));
        b1=4*Integer.parseInt(str.substring(1,2));
        b2=2*Integer.parseInt(str.substring(2,3));
        b3=Integer.parseInt(str.substring(3,4));

        result=b0+b1+b2+b3;
        System.out.println("From 4-bit binary number to Decimal:"+result);


    }
}
