package CSC400.M5;

public class ReverseString {
    public static String reverse(String str) {
          // base case is when the string is empty
          if (str.isEmpty()) { 
               return str;
          } else {
               return reverse(str.substring(1)) + str.charAt(0);
          }
    }

    public static void main(String[] args) {
        String original = "My Name Is Jesse";
        System.out.println("original string - " + original);
        String reversed = reverse(original);
        System.out.println("reversed string -  " + reversed);
    }
}