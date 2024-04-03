package CSC372.M6;
import java.util.Comparator;
import java.util.ArrayList;

public class Sorter {
     public static class SortByName implements Comparator<Student> {
          @Override
          public int compare(Student student1, Student student2) {
               return student1.getName().compareTo(student2.getName());
          }
     }

     public static class SortByRollno implements Comparator<Student> {
          @Override
          public int compare(Student student1, Student student2) {
               return Integer.compare(student1.getRollno(), student2.getRollno());
          }
     }

     public static void selectionSort(ArrayList<Student> students, Comparator<Student> comparator) {
          for (int i = 0; i < students.size() - 1; i++) {
               int minIndex = i;
               for (int j = i + 1; j < students.size(); j++){
                    if (comparator.compare(students.get(j), students.get(minIndex)) < 0){
                         minIndex = j;
                    }
               }
               Student temp = students.get(minIndex);
               students.set(minIndex, students.get(i));
               students.set(i, temp);
          }
     }

     public static void main(String[] args) {
          ArrayList<Student> students = new ArrayList<>();
          students.add(new Student(29, "Jesse", "Chesterfield Lane"));
          students.add(new Student(28, "Diana", "Highland Road Street"));
          students.add(new Student(31, "Gabby", "J Street"));
          students.add(new Student(32, "Anabell", "Deer Run Pass"));
          students.add(new Student(47, "Myrsine", "Five Oaks Circle"));
          students.add(new Student(24, "Blake", "Dusty Saddle Road"));
          students.add(new Student(20, "Alyssa", "Alondra Street"));
          students.add(new Student(8, "Austin", "Winding Creek Road"));
          students.add(new Student(1, "Athena", "Lariat Ridge"));
          students.add(new Student(1, "Titan", "Vip Drive"));

          selectionSort(students, new SortByName());
          System.out.println("Students sorted by name:");
          for (Student student : students) {
               System.out.println(student);
          }

          System.out.println("\n");

          selectionSort(students, new SortByRollno());
          System.out.println("Students sorted by roll number:");
          for (Student student : students) {
               System.out.println(student);
          }
     }
}
