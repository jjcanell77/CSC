package CSC400.M8;

public class Person {
     String firstName;
     String lastName;
     int age;
 
     public Person(String firstName, String lastName, int age) {
         this.firstName = firstName;
         this.lastName = lastName;
         this.age = age;
     }
 
     @Override
     public String toString() {
         return "Name: " + firstName + " " + lastName + ", Age: " + age;
     }
}