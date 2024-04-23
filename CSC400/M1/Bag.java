package CSC400.M1;

import java.util.HashMap;
import java.util.Map;

public class Bag<T> {
    private Map<T, Integer> contents;

    public Bag() {
        this.contents = new HashMap<>();
    }

    /**
     * Adds an item to the bag.
     * @param item The item to be added.
     */
    public void add(T item) {
        contents.put(item, contents.getOrDefault(item, 0) + 1);
    }

    /**
     * Removes one occurrence of the specified item from the bag, if it exists.
     * @param item The item to remove.
     */
    public void remove(T item) {
        if (contents.containsKey(item)) {
            int count = contents.get(item);
            if (count > 1)
                contents.put(item, count - 1);
            else
                contents.remove(item);
        }
    }

    /**
     * Checks if the bag contains the specified item.
     * @param item The item to check.
     * @return true if the item exists in the bag, otherwise false.
     */
    public boolean contains(T item) {
        return contents.containsKey(item);
    }

    /**
     * Returns the count of the specified item in the bag.
     * @param item The item whose count is to be returned.
     * @return The count of the item.
     */
    public int count(T item) {
        return contents.getOrDefault(item, 0);
    }

    /**
     * Returns a string representation of the bag contents.
     * @return A string representing the items and their counts in the bag.
     */
    @Override
    public String toString() {
        return contents.toString();
    }
}
