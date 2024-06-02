import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CustomLinkedList {
    private Node head;
    private Node tail;

    // inserts a new node with the given data
    public void insert(int data) {
        Node newNode = new Node(data, null);
        //checks is head is empty
        if (head == null) {
            // if so then the new newNode becomes our head and tail
            head = newNode;
            tail = newNode;
        } else {
            // if not then updates the tail
            tail.nextNode = newNode;
            tail = newNode;
        }
    }
    
    //deletes the first occurrence of a node with the given data.
    public void delete(int data) {
        //checks is head is empty
        if (head != null) {
            // if the head is the num to be deleted
            if (head.data == data) {
                // the head is updated to the next node
                head = head.nextNode;
                // in the case were now it is empty
                if (head == null) {
                    // the would be empty
                    tail = null;
                }
                // no reason to continue
                return;
            }
            Node current = head;
            //finds the node before first occurance of num or end of linked list
            while (current.nextNode != tail && current.nextNode.data != data) {
                current = current.nextNode;
            }
            // if not the end of the list
            if (current.nextNode != null) {
                current.nextNode = current.nextNode.nextNode;
                if (current.nextNode == null) {
                    tail = current;
                }
            }
        }
    }

    // returns an iterator for traversing the linked list.
    public Iterator<Integer> iterator() {
        return new LinkedListIterator();
    }

    // defines the Node structure
    private class Node {
        private int data;
        private Node nextNode;

        public Node(int data, Node nextNode) {
            this.data = data;
            this.nextNode = nextNode;
        }
    }

    // implements the iterator
    private class LinkedListIterator implements Iterator<Integer> {
        private Node current = head;

        // returns true if there is a next element, false otherwise.
        @Override
        public boolean hasNext() {
            return current != null;
        }

        // returns the next element and moves the iterator to the next position.
        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int data = current.data;
            current = current.nextNode;
            return data;
        }
    }

    // method to load data from a text file into the linked list
    public void loadDataFromFile(String filename) {
        try(Scanner scanner = new Scanner(new File(filename))){
            // while files has a next line
            while(scanner.hasNextLine()){
                // reads each line from the specified file an removes everything minus the numbers
                String[] line = scanner.nextLine().replaceAll("[^\\d]", "").split("");
                try{
                    for(int i = 0; i < line.length; i++){
                        insert(Integer.parseInt(line[i]));
                    }
                }
                // else prints out any errors 
                catch(Exception e){
                    System.out.println(e.getMessage() + "\n");
                }
            }
          } 
        // if file cante be found error
        catch(FileNotFoundException e){
            System.out.println(String.format("Error: %s File not found", filename));
        }
    }

    public static void main(String[] args) {
        CustomLinkedList linkedList = new CustomLinkedList();
        String filename = "CSC400\\M6\\listOfInts.txt";

        // loads data from a text file into our linkedList
        linkedList.loadDataFromFile(filename);

        Iterator<Integer> iterator1 = linkedList.iterator();
        System.out.print("List From File:\t\t");
        while (iterator1.hasNext()) {
            System.out.print(iterator1.next() + " ");
        }

        // deletes 2
        linkedList.delete(2);
        // inserts 10
        linkedList.insert(10);

        // iterates and prints our nums in Linked list
        Iterator<Integer> iterator2 = linkedList.iterator();
        System.out.print("\nList After Changes:\t");
        while (iterator2.hasNext()) {
            System.out.print(iterator2.next() + " ");
        }
    }
}
