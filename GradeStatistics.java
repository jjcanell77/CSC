import java.util.InputMismatchException;
import java.util.Scanner;

public class GradeStatistics {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        float sum = 0, grade;
        int count = 0;
        float max = 0, min = 100;
        for (int i = 0; i < 10; i++) {
            System.out.println("Enter grade " + (i + 1) + " (or a non-numeric value to finish):");
            try {
                grade = scanner.nextFloat();
                if (grade < 0 || grade > 100) {
                    System.out.println("Grade must be between 0 and 100. Please try again.");
                    i--; 
                    continue;
                }
            } catch (InputMismatchException e) {
                break; 
            }
            sum += grade;
            count++;
            if (grade > max) {
                max = grade;
            }
            if (grade < min) {
                min = grade;
            }
            scanner.nextLine();
        }
        if (count > 0) {
            float average = sum / count;
            System.out.println("Average grade: " + average);
            System.out.println("Maximum grade: " + max);
            System.out.println("Minimum grade: " + min);
        } else {
            System.out.println("No valid grades were entered.");
        }
    }
}
