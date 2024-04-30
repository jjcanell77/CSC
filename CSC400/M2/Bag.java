package CSC400.M2;
import java.util.*;
/* 
     Represents a collection, or multiset, that allows duplicate 
     elements and does not enforce any particular order. The Bag 
     class uses a generic type T to allow storage of any type of items.
*/
public class Bag<T> {
     private List<T> contents;

     /* 
          Constructs an empty Bag that is an ArrayList. 
     */
     public Bag() {
          this.contents = new ArrayList<>();
     }

     /* 
          Adds the specific item to the bag if its not null.
     */
     public void add(T item) {
          if (item == null){
               throw new IllegalArgumentException("Cannot add null to a Bag.");
          } 
          contents.add(item);
     }

     /*
          Removes one occurrence of the specific item from the bag, if present.
     */
     public boolean remove(T item) {
          return contents.remove(item);
     }

     /*
          Checks whether the specific item is in the bag.
     */
     public boolean contains(T item) {
          return contents.contains(item);
     }

     /*
          Counts the total amount of items in the bag. 
     */
     public int size() {
          return contents.size();
     }

     /*
          Counts the amount of times a particualar item is in the bag. 
     */
     public int count(T item) {
          int count = 0;
          if(contains(item)){
               for(T element : contents){
                    count = element == item ? count + 1 : count;
               }
          }
          return count;
     }

     /*
          Adds all items to other bag therfore merging them into one.  
     */
     public void merge(Bag<T> otherBag) {
          for (T item : otherBag.contents) {
               this.contents.add(item);
          }
     }

     /*
          Returns a new bag that does not have duplicates
     */
     public Bag<T> distinct() {
          Bag<T> checkeditems = new Bag<>();
          for (T element : contents) {
               if (!checkeditems.contains(element)) {
                    checkeditems.add(element);
               }
          }
          return checkeditems;
     }

     /*
          Returns a string representation of the bag's contents.
     */
    @Override
     public String toString() {
          return contents.toString();
     }

     /*
          Prints out contents and tests size for bag
     */
     private static void displayDetails(Bag<String> bag) {
          System.out.println("Contents: \t" + bag);
          System.out.println("Size: \t\t" + bag.size() + "\n");
     }
      

     public static void main(String[] args) {
          //   Fixed-arrays of fruits
          String[] fruits = {"apple", "banana", "apple", "strawberry"};
          String[] moreFruits = {"orange", "apple", "grape"};
          
          //   First bag
          Bag<String> originalBag = new Bag<>();
          for (String fruit : fruits) {
               originalBag.add(fruit);
          }
          System.out.println("Original Bag:");
          displayDetails(originalBag);

          //   Second bag
          Bag<String> anotherBag = new Bag<>();
          for (String fruit : moreFruits) {
               anotherBag.add(fruit);
          }
          System.out.println("Another Bag:");
          displayDetails(anotherBag);

          //   Merged bags into current
          originalBag.merge(anotherBag);
          System.out.println("Merged Bag:");
          displayDetails(originalBag);

          // Removes duplicates from original
          Bag<String> distinctBag = originalBag.distinct();
          System.out.println("Distinct Bag:");
          displayDetails(distinctBag);
     }
}
