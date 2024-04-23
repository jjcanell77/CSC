import java.util.Scanner;

public class TaxCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your weekly income: $");
        double income = scanner.nextDouble();
        double tax = calculateTax(income);
        System.out.println("Weekly tax withholding for an income of $" + income + " is $" + tax);
        scanner.close();
    }

    public static double calculateTax(double income) {
        double taxRate;

        if (income < 500) {
            taxRate = 0.10;
        } else if (income < 1500) {
            taxRate = 0.15;
        } else if (income < 2500) {
            taxRate = 0.20;
        } else {
            taxRate = 0.30;
        }

        return income * taxRate;
    }
}
