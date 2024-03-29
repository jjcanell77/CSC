package CSC372.M5;
import java.util.Scanner;

public class Recursion {
 
     public static void main(String[] args) {
          Scanner scanner = new Scanner(System.in);
          int[] numbers = new int[5];
  
          System.out.println("please enter five numbers.");
          for (int i = 0; i < 5; i++) {
              numbers[i] = scanner.nextInt();
          }
  
          int sum = calculateTotalSum(numbers, 0);
          System.out.println("Total sum is " + sum);
     }
  
     public static int calculateTotalSum(int[] numbers, int index) {
          if (index == numbers.length) return 0;
          else return numbers[index] + calculateTotalSum(numbers, index + 1);
     }
}