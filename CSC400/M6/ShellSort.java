public class ShellSort {
    public void Sort(int[] array){
        // start with a big gap, then reduce the gap
        for (int gap = array.length / 2; gap > 0; gap /= 2) {
            // perform a gapped insertion sort for this gap size.
            for (int i = gap; i < array.length; i += 1) {
                // save array[i] in temp and make a hole at position i
                int temp = array[i];
                int j;
                // shift earlier gap-sorted elements up until the correct location for array[i] is found
                for (j = i; j >= gap && array[j - gap] > temp; j -= gap) {
                    array[j] = array[j - gap];
                }
                // puts temp (the original array[i]) in its correct location
                array[j] = temp;
            }
        }
    }
    
    static void printArray(int array[]){
        for (int i = 0; i < array.length; ++i){
            System.out.print(array[i] + " ");
        }
    }

    public static void main(String[] args){
        // an array of 5 ints out of order
        int[] myArray = {87, 45, 2, 15, 12};
        ShellSort mySort = new ShellSort();
        // sorts array using shell sort
        mySort.Sort(myArray);
  
        System.out.println("Sorted array: ");
        // prints out each int in the array
        printArray(myArray);
    }
}