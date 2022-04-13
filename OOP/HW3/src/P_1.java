import java.util.Scanner;

/**
 * Program to display the string that would be lexicographically second.
 * Author: Cho Suk Hwan
 * E-mail Address: sukhwan5027@naver.com
 * Programming Assignment 3.
 * Last Changed: April 8, 2022
 */
public class P_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s1,s2,s3,tmp,middleWord; // tmp is temporary value

        System.out.println("Please enter three words separated by spaces");
        s1=sc.next();
        s2=sc.next();
        s3=sc.next();

        if(s1.compareTo(s2)>0)  tmp=s1;
        else    tmp=s2;

        if(tmp.compareTo(s3)>0) middleWord=s3;
        else    middleWord=tmp;

        System.out.println("The middle word is "+middleWord);
    }
}
