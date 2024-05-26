package CSC400.M2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* 
     Represents a collection, or multiset, that allows duplicate 
     elements and does not enforce any particular order. The Bag 
     class uses a generic type T to allow storage of any type of items.
*/
public class Bag<T> {
     private List<T> contents;
     private Map<T, Integer> contentsFrequency;

     /* 
          Constructs an empty Bag that is an ArrayList. 
     */
     public Bag() {
          contents = new ArrayList<>();
          contentsFrequency = new HashMap<>();
     }
     /* 
          Constructs a Bag from a collection instead of empty 
     */
     public Bag(Collection<T> collection) {
        contents = new ArrayList<>(collection);
     }

     /* 
          Adds the specific item to the bag if its not null.
     */
     public void add(T item) {
          if (item == null){
               throw new IllegalArgumentException("Cannot add null to a Bag.");
          } 
          contents.add(item);
          contentsFrequency.put(item, contentsFrequency.getOrDefault(item, 0) + 1);
     }

     /*
          Removes one occurrence of the specific item from the bag, if present.
     */
     public boolean remove(T item) {
          if(contents.contains(item)){
               contentsFrequency.put(item, contentsFrequency.get(item) - 1);
               if(contentsFrequency.get(item) == 0) {
                    contentsFrequency.remove(item);
               }
          }
          return contents.remove(item);
     }

     /*
          Checks whether the specific item is in the bag.
     */
     public boolean contains(T item) {
          return contentsFrequency.containsKey(item);
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
          return contentsFrequency.getOrDefault(item, 0);
     }

     /*
          Adds all items to other bag therfore merging them into one.  
     */
     public void merge(Bag<T> otherBag) {
          for (T item : otherBag.contents) {
               this.add(item);
          }
     }

     /*
          Returns a new bag that does not have duplicates by intializng a new bag with the keys from contentsFrequency
     */
     public Bag<T> distinct() {
          Bag<T> uniqueBag = new Bag<>(contentsFrequency.keySet());
          return uniqueBag;
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
          // Fixed-arrays of fruits
          String[] fruits = {"apple", "banana", "apple", "strawberry"};
          String[] moreFruits = {"orange", "apple", "grape"};
          
          // First bag
          Bag<String> originalBag = new Bag<>();
          for (String fruit : fruits) {
               originalBag.add(fruit);
          }
          System.out.println("Original Bag:");
          displayDetails(originalBag);

          // Second bag
          Bag<String> anotherBag = new Bag<>();
          for (String fruit : moreFruits) {
               anotherBag.add(fruit);
          }
          System.out.println("Another Bag:");
          displayDetails(anotherBag);

          // Merged bags into current
          originalBag.merge(anotherBag);
          System.out.println("Merged Bag:");
          displayDetails(originalBag);

          // Removes duplicates from original
          Bag<String> distinctBag = originalBag.distinct();
          System.out.println("Distinct Bag:");
          displayDetails(distinctBag);
     }
}