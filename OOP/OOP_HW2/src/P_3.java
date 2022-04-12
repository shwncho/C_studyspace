import java.util.Scanner;
/**
 * Program to replace the items in italics with user's input
 * Author: ChoSukHwan
 * E-mail Address: sukhwan5027@naver.com
 * Programming Assignment2's Problem 3
 * Last Changed: March 25, 2022.
 */
public class P_3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String name,color,animal,food;

        System.out.print("Enter a favorite color:");
        color=sc.nextLine();
        System.out.print("Enter a favorite food:");
        food=sc.nextLine();
        System.out.print("Enter a favorite animal:");
        animal=sc.nextLine();
        System.out.print("Enter the first name of a friend or relative:");
        name=sc.nextLine();

        System.out.println("\nI had a dream that "+name+" ate a "+color+" "+animal);
        System.out.println("and said it tasted like "+food+"!");
    }
}
