package CSC400.M8;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class QueuePerson {
    private ArrayList<Person> people;

    public QueuePerson() {
        this.people = new ArrayList<>();
    }

    // adds a person to the queue.
    public void enqueue(Person person) {
        people.add(person);
    }

    // removes a person to the queue.
    public void dequeue() {
        people.remove(0);
    }

    // gets the list of people in the queue
    public List<Person> getPeople() {
        return people;
    }

    // Quick sort the queue by last name in descending order.
    public void sortByLastName() {
        quickSort(Comparator.comparing((Person p) -> p.lastName).reversed(), 0, people.size() - 1);
    }

    //Quick sort the queue by age in descending order.
    public void sortByAge() {
        quickSort(Comparator.comparingInt((Person p) -> p.age).reversed(), 0, people.size() - 1);
    }

    // Generalized quick sort method that takes a comparator.
    private void quickSort(Comparator<Person> people, int low, int high) {
        if (low < high) {
            int pi = partition(people, low, high);
            quickSort(people, low, pi - 1);
            quickSort(people, pi + 1, high);
        }
    }

    // Partition method for quick sort.
    private int partition(Comparator<Person> comparator,int low, int high) {
        Person pivot = people.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare(people.get(j), pivot) > 0) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }

    private void swap(int i, int j) {
        Person temp = people.get(i);
        people.set(i, people.get(j));
        people.set(j, temp);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        QueuePerson queue = new QueuePerson();
        System.out.println("Please Enter atleast one person's first name, last name and age.");
        String choice;

        // prompts the user to add as many peopleas they want to the queue.
         do {
            System.out.println("Enter first name of the person:");
            String firstName = scanner.nextLine();
            System.out.println("Enter last name of the person:");
            String lastName = scanner.nextLine();
            System.out.println("Enter age of the person:");
            int age = Integer.parseInt(scanner.nextLine());
            queue.enqueue(new Person(firstName, lastName, age));

            System.out.println("Do you want to add another person? (yes/no):");
            choice = scanner.nextLine();
        } while (choice.equalsIgnoreCase("yes"));
 
        // displays the queue before sorted.
        System.out.println("\nQueue before sorted:");
        printQueue(queue);
 
        // Sort by last name in descending order and display the queue.
        queue.sortByLastName();
        System.out.println("\nQueue sorted by last name (descending):");
        printQueue(queue);
 
         // Sort by age in descending order and display the queue.
        queue.sortByAge();
        System.out.println("\nQueue sorted by age (descending):");
        printQueue(queue);
 
        scanner.close();
    }
 
    // prints all of the people in the queue.
    private static void printQueue(QueuePerson queue) {
        for (Person person : queue.getPeople()){
            System.out.println(person);
        }
    }
}
