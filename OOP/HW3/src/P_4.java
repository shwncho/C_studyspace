import java.util.Scanner;
/**
 * Program to test whether user's input date is a valid date.
 * Author: Cho Suk Hwan
 * E-mail Address: sukhwan5027@naver.com
 * Programming Assignment 3.
 * Last Changed: April 8, 2022
 */
public class P_4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str;
        int month,day,year;
        String[] afterStr; // afterStr is to split user's input date to String[] by "/"

        System.out.println("Please enter a date to be checked");
        str=sc.nextLine();

        afterStr=str.split("/");
        month=Integer.parseInt(afterStr[0]);
        day=Integer.parseInt(afterStr[1]);
        year=Integer.parseInt(afterStr[2]);

        System.out.println("date is "+afterStr[0]+":"+afterStr[1]+":"+afterStr[2]);
        System.out.println("Your date was "+afterStr[0]+"/"+afterStr[1]+"/"+afterStr[2]);

        if(1<=month && month<=12){
            if((month==4 || month==6 || month==9 || month==11) && 1<=day && day<=30) System.out.println("It is a valid date");
            else if(month==2){
                if(1<=day && day<=28) System.out.println("It is a valid date");
                else {
                    if (((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) && 1 <= day && day <= 29)
                        System.out.println("It is a valid date");
                    else{
                        System.out.println("It is not a valid date");
                        System.out.println("The reason it is invalid: The day value is greater than 28 in February in a non leap year.");
                    }
                }
            }
            else if(1<=day && day<=31)  System.out.println("It is a valid date");
        }
        else{
            System.out.println("It is not a valid date");
            System.out.println("The reason it is invalid: The month value is not from 1 to 12.");
        }


    }
}
