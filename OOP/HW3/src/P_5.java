import java.util.Scanner;
/**
 * Program to display the largest integer, the smallest integer, and the average of all the integers.
 * Author: Cho Suk Hwan
 * E-mail Address: sukhwan5027@naver.com
 * Programming Assignment 3.
 * Last Changed: April 8, 2022
 */
public class P_5 {
    public static void main(String[] args) {
        int smallest = Integer.MAX_VALUE;
        int largest = Integer.MIN_VALUE;
        int num=0; // user's input value
        int totalSum=0;
        int cnt=0;  // how many value user enters
        double avg=0.0; // average
        Scanner sc = new Scanner(System.in);

        System.out.println("This program allows you to enter a list of positive integers");
        System.out.println("(terminated by a -1) then displays the largest value,");
        System.out.println("smallest value, and average of the list of numbers,");
        System.out.println("not including the final (negative) value that ends the list.");
        System.out.println();

        while(true){
            System.out.println("Please enter a positive integer, or -1 to quit");
            num=sc.nextInt();
            if(num==-1) break;
            totalSum+=num;
            cnt++;
            if(num<smallest)    smallest=num;
            if(num>largest)     largest=num;
        }
        avg=totalSum/(double)cnt;
        System.out.println("For the "+cnt+" numbers you entered");
        System.out.println("the largest value = "+largest);
        System.out.println("the smallest value = "+smallest);
        System.out.println("and the average is = "+ avg);



    }
}
