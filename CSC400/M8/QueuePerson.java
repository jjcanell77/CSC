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
        if (!people.isEmpty()) {
            people.remove(0);
        } 
    }

    // gets the list of people in the queue
    public List<Person> getPeople() {
        return people;
    }

    // calls quick sort the queue by last name in descending order.
    public void sortByLastName() {
        // creates a comparator that compares the objects based on lastName reversed so in descending order.
        Comparator<Person> lastName = Comparator.comparing((Person p) -> p.lastName).reversed();
        int startingIndex = 0;
        int endingIndex = people.size() - 1;
        quickSort(lastName, startingIndex, endingIndex);
    }

    // calls quick sort the queue by age in descending order.
    public void sortByAge() {
        // creates a comparator that compares the objects based on lastName reversed so in descending order.
        Comparator<Person> age = Comparator.comparingInt((Person p) -> p.age);
        int startingIndex = 0;
        int endingIndex = people.size() - 1;
        quickSort(age, startingIndex, endingIndex);
    }

    // quick sort that accounts for the comparator
    private void quickSort(Comparator<Person> comparator, int low, int high) {
        // comparator is the array to be sorted
        // low is the starting index
        // high being the ending index
        if (low < high) {
            int partitioningIndex = partition(comparator, low, high);
            quickSort(comparator, low, partitioningIndex - 1);
            quickSort(comparator, partitioningIndex + 1, high);
        }
    }

    // Partition method for quick sort.
    private int partition(Comparator<Person> comparator,int low, int high) {
        // the last person becomes pivot
        Person pivot = people.get(high);
        // index of smaller element
        int i = low - 1;
        for (int j = low; j < high; j++) {
            // if current person is smaller than the last person
            if (comparator.compare(pivot, people.get(j)) > 0) {
                i++;
                // handles the swap with current person j with smaller i
                swap(i, j);
            }
        }
        // handles the swap last person and second to last person
        swap(i + 1, high);
        return i + 1;
    }

    // swaps people in the list.
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
            String firstName = "empty";
            firstName = scanner.nextLine();

            System.out.println("Enter last name of the person:");
            String lastName = "empty";
            lastName = scanner.nextLine();

            System.out.println("Enter age of the person:");
            int age = handleAge(scanner);

            queue.enqueue(new Person(firstName, lastName, age));

            System.out.println("Do you want to add another person? (y/press enter):");
            choice = scanner.nextLine();
        } while (choice.equalsIgnoreCase("y"));
 
        // displays the queue before sorted.
        System.out.println("\nQueue before sorted:");
        printQueue(queue);
 
        queue.sortByLastName();
        System.out.println("\nQueue sorted by last name (descending):");
        printQueue(queue);
 
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

    // handles incorrect ages ibeing inputed
    private static int handleAge(Scanner scanner){
        int age = 0;
        while (true) {
            try {
                age = Integer.parseInt(scanner.nextLine());
                if (age <= 0) {
                    System.out.println("Please enter a positive integer.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid age. Please enter an integer that is greater than 0.");
            }
        }
        return age;
    }
}
