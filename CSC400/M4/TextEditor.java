package CSC400.M4;
import java.util.Stack;

public class TextEditor {
    private Stack<String> actions = new Stack<>(); 
     // current text in the editor
    private StringBuilder text = new StringBuilder();

    public void write(String string) {
        // pushes the string to the top of the stack
        actions.push(string); 
         // adds the new text to the editor
        text.append(string);
    }

    public void undo() {
        // checks if there is anything to undo
        if (!actions.isEmpty()) { 
            // removes the last action from the end of the stack
            String lastAction = actions.pop(); 
            // removes the last action's text from the editor
            text.delete(text.length() - lastAction.length(), text.length()); 
        }
    }

    public String getText() {
        return text.toString(); // Returns the current text in the editor
    }

    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        editor.write("My");
        editor.write(" ");
        editor.write("Name");
        editor.write(" ");
        editor.write("is");
        editor.write(" ");
        editor.write("Jesse");

        System.out.println("Text before undo: " + editor.getText()); 
        //removes "Jesse"
        editor.undo();
        System.out.println("Text after undo: " + editor.getText()); 
        //removes " "
        editor.undo();
        //removes "is"
        editor.undo();
        System.out.println("Text after another undo: " + editor.getText()); 
    }
}