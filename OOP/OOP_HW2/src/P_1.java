import java.util.Scanner;

/**
 * Program to replace a word
 * Author: ChoSukHwan
 * E-mail Address: sukhwan5027@naver.com
 * Programming Assignment2's Problem 1
 * Last Changed: March 25, 2022.
 */
public class P_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter a line of text.");
        String str = sc.nextLine(); // user's input
        System.out.println("I have rephrased that line to read:");
        System.out.println(str.replaceFirst("hate","love"));
    }
}
