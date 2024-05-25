package CSC400.M5;
import java.util.Arrays;

public class RadixSort {
     public static void radixSort(int[] array) {
          // finds the max number to set the number of digits
          // converts the array to a stream, max finds the max int, and getAsInt gets the max
          int max = Arrays.stream(array).max().getAsInt();

          // will iniate a counting sort for each digit in max
          for (int exp  = 1; max / exp  > 0; exp *= 10) {
               countSort(array, exp );
          }
     }

     private static void countSort(int[] array, int exp ) {
          int n = array.length;
          int i;
          // an empty output array the same size as the orginal array
          int[] output = new int[n];
          // an empty array to store the count of digits (0 to 9)
          int[] count = new int[10];

          // stores the count of occurrence for each digit
          for (int digit : array) {
               int num = (digit / exp) % 10;
               count[num]++;
          }

          // changes the count[i] so that it contains the actual position of the digit in output[]
          for (i = 1; i < 10; i++) {
               count[i] += count[i - 1];
          }

          // adds the ints into the output array
          for (i = n - 1; i >= 0; i--) {
               int num = (array[i] / exp) % 10;
               output[count[num] - 1] = array[i];
               count[num]--;
          }

          //  copies the contents of the output array back into the original array 
          System.arraycopy(output, 0, array, 0, n);
     }

     public static void main(String[] args) {
          int[] array = {783, 99, 472, 182, 264, 543, 356, 295, 692, 491, 94};
          System.out.println("Starting array: " + Arrays.toString(array));
          radixSort(array);
          System.out.println("RadixSorted array: " + Arrays.toString(array));
     }
}