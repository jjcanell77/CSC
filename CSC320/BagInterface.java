public interface BagInterface<T> {
     public int getCurrentSize();
     public boolean isEmpty();
     public boolean add(T newEntry);
     public T remove();
     public boolean remove (T newEntry);
     public void clear();
     public int getFrequency(T anEntry);
     public boolean contains(T anEntry);
     public T[] toArray();
}
