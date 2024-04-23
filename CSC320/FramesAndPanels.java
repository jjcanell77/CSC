import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

public class FramesAndPanels {
    public static void main(String[] args) {
        JFrame frame = new JFrame("A GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        frame.add(panel);

        JButton button = new JButton("I can be Clicked");
        panel.add(button);
        frame.setVisible(true);
    }
}
