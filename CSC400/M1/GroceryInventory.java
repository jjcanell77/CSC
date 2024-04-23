package CSC400.M1;
import java.util.*;


public class GroceryInventory {
     
     public static class Bag<T> {
          private Map<T, Integer> contents;

          public Bag() {
               this.contents = new HashMap<>();
          }

          public void add(T item) {
               contents.put(item, contents.getOrDefault(item, 0) + 1);
          }

          public void remove(T item) {
               if (contents.containsKey(item)) {
                    int count = contents.get(item);
                    if (count > 1){
                         contents.put(item, count - 1);
                    }
                    else{
                         contents.remove(item);
                    }
               }
          }

          public boolean contains(T item) {
               return contents.containsKey(item);
          }

          public int count(T item) {
               return contents.getOrDefault(item, 0);
          }

          @Override
          public String toString() {
               return contents.toString();
          }
     }

     private static void displayFruitDetails(Bag<String> bag, String[] fruits) {
          Set<String> checkedFruits = new HashSet<>();
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

          System.out.println("Bag contains:\t" + myBag);
          displayFruitDetails(myBag, fruits);

          myBag.remove("apple");
          System.out.println("\nBag afterwards:\t" + myBag);
          displayFruitDetails(myBag, fruits);
     }
}
