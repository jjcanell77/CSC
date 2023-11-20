import java.util.InputMismatchException;
import java.util.Scanner;

public class GradeStatistics {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        float sum = 0, grade;
        int count = 0;
        float max = Float.MIN_VALUE, min = Float.MAX_VALUE;

        for (int i = 0; i < 10; i++) {
            System.out.println("Enter grade " + (i + 1));
            System.out.println("or a non-numeric value to finish/exit:");
            try {
                grade = scanner.nextFloat();
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
            System.out.println("No grades were entered.");
        }
    }
}
