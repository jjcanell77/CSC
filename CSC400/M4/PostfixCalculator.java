package CSC400.M4;
import java.util.Stack;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class PostfixCalculator {
     private final static String Add = "+";
     private final static String Subtract = "-";
     private final static String Multiply = "*";
     private final static String Divide = "/";
     private final static String Modulus = "%";
     private Stack<Integer> stack;

     public int evaluatePostfix(String postfixExpression){
          stack = new Stack<>();
          // uses regex to split string into an array of chars sepereated by white space
          String[] elements = postfixExpression.split("\\s+");
          String invalidPostfix = "Error: Invalid postfix expression";

          for(String element : elements){
               // if an operand then..
               if(element.matches("-?\\d+")){
                    // push it on the stack
                    stack.push(Integer.parseInt(element));
               } 
               // if an opperator is then..
               else if(element.matches("[+\\-*/%]")){
                    // makes sure that stack has atleast two elements for the opperation
                    if(stack.size() >= 2){
                         // pops the top two elements
                         int operandB = stack.pop();
                         int operandA = stack.pop();

                         // uses a switch statement to check which opperator perform the operation then then pushes result
                         int result = performOperation(element, operandA, operandB, invalidPostfix);
                         stack.push(result);
                    }
                    // if not then throw error
                    else{
                         throw new IllegalArgumentException(invalidPostfix);
                    }
               } 
               // if neither then throws error
               else{
                    throw new IllegalArgumentException("Error: Invalid element " + element);
               }
          }
          
          // checks that there is only one result
          if(stack.size() != 1){
               throw new IllegalArgumentException(invalidPostfix);
          }
          // returns the result on the top of the stack
          return stack.pop();
     }

     // added this due for less complexity
     private int performOperation(String operator, int operandA, int operandB, String Error) {
          switch (operator) {
               case Add:
                    return operandA + operandB;
               case Subtract:
                    return operandA - operandB;
               case Multiply:
                    return operandA * operandB;
               case Divide:
                    //checks if divided by zero
                    if (operandB == 0){ 
                         throw new ArithmeticException(Error + " zero division");
                    }
                    return operandA / operandB;
               case Modulus:
                    //checks if divided by zero
                    if (operandB == 0){ 
                         throw new ArithmeticException(Error + " zero division");
                    }
                    return operandA % operandB;
               default:
                    return 0;
          }
     }
  
     public void evaluateFromFile(String filename){
          try(Scanner scanner = new Scanner(new File(filename))){
               // while files has a next line
               while(scanner.hasNextLine()){
                     // reads each line from the specified file
                    String line = scanner.nextLine();
                    try{
                         // prints out expression
                         System.out.println(String.format("The Postfix Expression: %s", line));
                         // evaluates expression
                         int result = evaluatePostfix(line); 
                         // prints out the result
                         System.out.println(String.format("The result: %s \n", result));
                    }
                    // else prints out any errors 
                    catch(Exception e){
                         System.out.println(e.getMessage() + "\n");
                    }
               }
          } 
          // if file cante be found error
          catch(FileNotFoundException e){
               System.out.println(String.format("Error: %s File not found", filename));
          }
     }

     public static void main(String[] args){
          PostfixCalculator calculator = new PostfixCalculator();
          // all expressions are on the file
          String filename = "CSC400\\M4\\listOfExpressions.txt";
          /*
               Copy of file of contents:
               4 2 * 3 +
               5 3 + 7 *
               4 2 * +
               7 4 -3 * 1 5 + / *
           */
          calculator.evaluateFromFile(filename);
     }
}
