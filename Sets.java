import java.util.*;
public class Sets {
     public static void main(String[] args) {
          Set<String> names = new HashSet<>();
          names.add("Jesse");
          names.add("Diana");
          names.add("Jesse");
          System.out.println("No duplicate name: " + names);
     }
} 