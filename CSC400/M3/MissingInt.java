package CSC400.M3;

public class MissingInt {
     // overall complexity of 0(n)
     public static  int findMissingInt(int[] array) {
          // length of array and is O(1) constant
          int length = array.length;
          // sum of numbers from 1 to n+1 and is O(1) constant
          int totalSum = (length + 1) * (length + 2) / 2;  
          // variable to store the sum of the array elements and is O(1) constant
          int arraySum = 0;
          // repeats O(n) times
          for(int i = 0; i < length ; i++){
               // add the values of the ints to the main count  and is O(1) constant
               arraySum += array[i];
          }
          // the difference should be the missing int
          return totalSum - arraySum;
      }
      public static void main(String[] args) {
          int[] array = {3, 6, 5, 1, 4}; // an array of 5 ints out of order
          System.out.println("missing number is: " + findMissingInt(array));
      }
}