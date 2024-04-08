package CSC372.M8;

public class Student implements Comparable<Student> {
     private String name;
     private String address;
     private double GPA;
 
     public Student(String name, String address, double GPA) {
         this.name = name;
         this.address = address;
         this.GPA = GPA;
     }
 
     public String getName() {
          return name;
     }
     public String getAddress() {
          return address;
     }
     public double getGPA() {
          return GPA;
     }
 
     public void setName(String name) {
          this.name = name;
     } 
     public void setAddress(String address) {
          this.address = address;
     }
     public void setGPA(double GPA) {
          this.GPA = GPA;
     }
 
     @Override
     public int compareTo(Student other) {
          return this.name.compareTo(other.name);
     } 
     @Override
     public String toString() {
          return "Student Name: " + name + "\tAddress: " + address + "\t\tGPA: " + GPA;
     }
 }
 
