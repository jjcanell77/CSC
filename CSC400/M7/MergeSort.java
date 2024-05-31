package CSC400.M7;

public class MergeSort {
     public static void merge(int[] array, int[] firstHalf, int[] secondHalf, int lengthF, int lengthS) {
          // indices of first and second subarrays
          int i = 0, j = 0;
          // initial index of merged subarray array
          int k = 0;
          //  merge the temp arrays back into one array
          while (i < lengthF && j < lengthS) {
               if (firstHalf[i] <= secondHalf[j]) {
                    array[k++] = firstHalf[i++];
               }
               else {
                    array[k++] = secondHalf[j++];
               }
           }
          // copy remaining elements of firstHalf if any
          while (i < lengthF) {
               array[k++] = firstHalf[i++];
          }
          // copy remaining elements of secondHalf if any
          while (j < lengthS) {
               array[k++] = secondHalf[j++];
          }
     }
 
    // recurively divded array into halves, sort indivdually, and merge
     public static void mergeSort(int[] array, int arrayLength) {
          //base case
          if (arrayLength >= 2){
               // find the middle
               int middle = arrayLength / 2;
               // Find sizes of two subarrays to be merged
               int lengthF = middle;
               int lengthS =  arrayLength - lengthF;
               // Create temp arrays
               int[] firstHalf = new int[lengthF];
               int[] secondHalf = new int[lengthS];

                // Copy data to temp arrays
               for(int i = 0; i < lengthF; i++){
                    firstHalf[i] = array[i];
               }
               for(int j = lengthF; j < arrayLength; j++){
                    secondHalf[j - lengthF] = array[j];
               }
               // divide first half
               mergeSort(firstHalf, lengthF);
               // divide second half
               mergeSort(secondHalf, lengthS);
               // then merge them
               merge(array, firstHalf, secondHalf, lengthF, lengthS);
          }
     }

     public static void printOut(int[] array){
          for (int i : array) {
               System.out.print(i + " ");
          }
     }
 
     public static void main(String[] args) {
         int[] array = {12, 11, 13, 5, 6, 7};
         System.out.println("Initial Array");
         printOut(array);
 
         mergeSort(array, array.length);
 
         System.out.println("\nMerge Sorted array");
         printOut(array);
     }
}
 