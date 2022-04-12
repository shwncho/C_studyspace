import java.util.Scanner;
/**
 * Program to determine the change to be dispensed from a vending machine.
 * Author: ChoSukHwan
 * E-mail Address: sukhwan5027@naver.com
 * Programming Assignment2's Problem 4
 * Last Changed: March 25, 2022.
 */
public class P_4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int price,quarters,dimes,nickel,change;
        System.out.println("Enter price of item");
        System.out.print("(from 25 cents to a dollar, in 5-cent increments:");
        price=sc.nextInt(); //Item's price

        System.out.println("You bought an item for "+price+" cents and gave me a dollar,");
        System.out.println("so your change is");
        change=100-price;

        /*
        quarters is 25 cent
        dimes is 10 cent
        nickel is 5 cent
         */

        quarters=change/25;
        change=change%25;

        dimes=change/10;
        change=change%10;

        nickel=change/5;
        change=change%5;

        //Validation
        if(change!=0){
            System.out.println("Wrong change");
        }
        System.out.println(quarters+" quarters,");
        System.out.println(dimes+" dimes, and");
        System.out.println(nickel+" nickel.");





    }
}
