import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class WeeklyTempature {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> daysOfTheWeek = new ArrayList<>();
        ArrayList<Double> temperatures = new ArrayList<>();

        daysOfTheWeek.add("Monday");
        daysOfTheWeek.add("Tuesdayy");
        daysOfTheWeek.add("Wednesday");
        daysOfTheWeek.add("Thursday");
        daysOfTheWeek.add("Friday");
        daysOfTheWeek.add("Saturday");
        daysOfTheWeek.add("sunday");

        double minTemp = -55.0;
        double maxTemp = 155.0;

        System.out.print("Please enter the temaptures for the following days below:");

        for (String day : daysOfTheWeek) {
            double temp;
            while (true) {
                try{
                    System.out.print(day + ": ");
                    temp = scanner.nextDouble();
                    if (temp >= minTemp && temp <= maxTemp) {
                        break;
                    } 
                    else {
                        System.out.println("Temperatures should be somewhere between " + minTemp + "°F and " + maxTemp + "°F.");
                    }
                }
                catch (InputMismatchException e) {
                    System.out.println("Please enter a valid number.");
                    scanner.next();
                }
            }
            temperatures.add(temp);
        }
        while (true) {
            System.out.print("Enter one of the days of the week to get temperature for that day or 'week' for a weekly summary: ");
            String input = scanner.next();
            if (input.equalsIgnoreCase("week")) {
                double total = 0;
                System.out.println("Weekly Temperature Summary:");
                for (int i = 0; i < daysOfTheWeek.size(); i++) {
                    System.out.println(daysOfTheWeek.get(i) + ": " + temperatures.get(i) + "°F");
                    total += temperatures.get(i);
                }
                double average = total / daysOfTheWeek.size();
                System.out.printf("Weekly Average is %.2f°F\n", average);
                break;
            } 
            else {
                int dayIndex = daysOfTheWeek.indexOf(input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase());
                if (dayIndex != -1) {
                    System.out.println(input + ": " + temperatures.get(dayIndex) + "°F");
                    break;
                } 
                else {
                    System.out.println("Please try again.");
                }
            }
        }
        scanner.close();
    }
}
