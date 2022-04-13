import java.util.Scanner;
/**
 * Program to display the total number of grades and the number of grades in each letter-grade category
 * Author: Cho Suk Hwan
 * E-mail Address: sukhwan5027@naver.com
 * Programming Assignment 3.
 * Last Changed: April 8, 2022
 */
public class P_6 {
    public static void main(String[] args) {
        int cntA=0,cntB=0,cntC=0,cntD=0,cntF=0; // cntA is the number of 'A', cntB is the number of 'B', ~~~
        int num; // user's input grade
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter numerical grades");
        System.out.println("in the range of 0 to 100 -");
        System.out.println("    integers, only, please!");
        System.out.println();
        System.out.println("Enter -1 to terminate data entry.");

        System.out.println("\n\n");

        while(true){
            System.out.println("Please enter a score from 0 to 100 or -1 to quit:");
            num=sc.nextInt();
            if(num==-1) break;

            if(90<=num) cntA++;
            else if(80<=num)  cntB++;
            else if(70<=num)    cntC++;
            else if(60<=num)    cntD++;
            else{
                cntF++;
            }
        }
        System.out.println();
        System.out.println("Total number of grades = "+(cntA+cntB+cntC+cntD+cntF));
        System.out.println("Number of A's = "+cntA);
        System.out.println("Number of A's = "+cntB);
        System.out.println("Number of A's = "+cntC);
        System.out.println("Number of A's = "+cntD);
        System.out.println("Number of A's = "+cntF);


    }
}
