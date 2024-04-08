package CSC372.M8;
import java.util.*;
import java.io.*;

public class Gradebook {
     public static void main(String[] args) {
          Scanner scanner = new Scanner(System.in);
          LinkedList<Student> students = new LinkedList<>();

          while (true) {
               System.out.println("Please enter student's name or 'exit'");
               String name = scanner.nextLine();
               if (name.equalsIgnoreCase("exit")){
                    break;
               } 

               System.out.println("Please enter student's address");
               String address = scanner.nextLine();

               double GPA = 0;
               while (true) {
                    System.out.println("Please enter student's GPA");
                    try {
                         GPA = Double.parseDouble(scanner.nextLine());
                         if (GPA < 0.0 || GPA > 4.0) {
                              throw new IllegalArgumentException("GPA should be between 0.0-4.0.");
                         }
                         break;
                    } catch (IllegalArgumentException e) {
                         System.out.println("Invalid input.");
                    }
               }

               students.add(new Student(name, address, GPA));
          }
          scanner.close();
          sortAndSave(students);
     }

     private static void sortAndSave(LinkedList<Student> students) {
          if (students.isEmpty()) {
               System.out.println("No data entered. No need to save.");
               return; 
          }
          Collections.sort(students);

          try (PrintWriter out = new PrintWriter("students-gradebook.txt")) {
               for (Student s : students) {
                    out.println(s);
               }
          } catch (FileNotFoundException e) {
               System.out.println("An error while saving occurred.");
          }
          System.out.println("Saved in students-gradebook.txt");
     }
}
