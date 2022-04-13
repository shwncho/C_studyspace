import java.util.Scanner;
/**
 * Program to convert either Celsius to Fahrenheit or Fahrenheit to Celsius
 * Author: Cho Suk Hwan
 * E-mail Address: sukhwan5027@naver.com
 * Programming Assignment 3.
 * Last Changed: April 8, 2022
 */
public class P_3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s; //user's choice celsius or fahrenheit
        double num,Degrees_C,Degrees_F;
        System.out.println("Enter a temperature in degrees (for example 29.6):");
        num=sc.nextDouble();
        sc.nextLine(); //  nextLine() is To remove "\n"

        System.out.println("Enter 'F' (or 'f') for Fahrenheit or 'C' (or 'c') for Celsius:");
        s=sc.nextLine();

        if(s.equalsIgnoreCase("f")){
            Degrees_C=5*(num-32)/9;
            System.out.println(num+" degrees F = "+Degrees_C+" degrees Celsius.");
        }
        else if(s.equalsIgnoreCase("c")){
            Degrees_F=(9*num/5)+32;
            System.out.println(num+" degrees C = "+Degrees_F+" degrees Fahrenheit.");

        }
        else{
            System.out.println("Invalid input value");
        }
    }
}
