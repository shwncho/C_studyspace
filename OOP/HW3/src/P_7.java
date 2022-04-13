import java.util.Scanner;
/**
 * Program to display the triangle by writing lines of asterisks.
 * Author: Cho Suk Hwan
 * E-mail Address: sukhwan5027@naver.com
 * Programming Assignment 3.
 * Last Changed: April 8, 2022
 */
public class P_7 {
    public static void main(String[] args) {
        int num; // user's input (the size of a triangle)
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter an integer from 1 to 50:");

        num=sc.nextInt();
        System.out.println();

        for(int i=0; i<num; i++){
            for(int j=0; j<=i; j++){
                System.out.print("*");
            }
            System.out.println();
        }

        for(int i=num-1; i>0; i--){
            for(int j=0; j<i; j++){
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
