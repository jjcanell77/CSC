package CSC400.M1;
import java.util.*;
/* 
     Represents a collection, or multiset, that allows duplicate 
     elements and does not enforce any particular order. The Bag 
     class uses a generic type T to allow storage of any type of items.
*/
public class Bag<T> {
     private List<T> contents;

     /* 
          Constructs an empty Bag. 
     */
     public Bag() {
          this.contents = new ArrayList<>();
     }

     /* 
          Adds the specified item to the bag.
     */
     public void add(T item) {
          if (item == null){
               throw new IllegalArgumentException("Cannot add null to a Bag.");
          } 
          contents.add(item);
     }

     /*
          Removes one occurrence of the specified item from the bag, if present.
     */
     public boolean remove(T item) {
          return contents.remove(item);
     }

     /*
          Checks whether the specified item is in the bag.
     */
     public boolean contains(T item) {
          return contents.contains(item);
     }

     /*
          Counts the amount of times item is in the bag. 
     */
     public int count(T item) {
          int count = 0;
          if(contents.contains(item)){
               for(T element : contents){
                    count = element == item ? count + 1 : count;
               }
          }
          return count;
     }

     /*
          Returns a string representation of the bag's contents.
     */
    @Override
     public String toString() {
          return contents.toString();
     }

     /*
          Provides the tests for Contains and Counts by accouting for duplicates
     */
     private static void displayFruitDetails(Bag<String> bag, String[] fruits) {
          List<String> checkedFruits = new ArrayList<>();
          for (String fruit : fruits) {
              if (!checkedFruits.contains(fruit)) {
                  System.out.println("Contains " + fruit + ": \t" + bag.contains(fruit));
                  System.out.println("Count of " + fruit + ": \t" + bag.count(fruit));
                  checkedFruits.add(fruit);
              }
          }
     }
      

     public static void main(String[] args) {
          Bag<String> myBag = new Bag<>();
          String[] fruits = {"apple", "banana", "apple", "strawberry"};
          for (String string : fruits) {
               myBag.add(string);
          }

          System.out.println("Bag contains:\t \t" + myBag);
          displayFruitDetails(myBag, fruits);

          myBag.remove("apple");
          System.out.println("\nBag afterwards:\t \t" + myBag);
          displayFruitDetails(myBag, fruits);
     }
}
