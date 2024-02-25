import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BankBalance extends JFrame 
{
     private JPanel panel;
     private JButton depositButton, withdrawButton, exitButton;
     private JTextField amountField;
     private JLabel balanceLabel, actionLabel;
     private double balance = 0;

     public BankBalance() 
     {
          setTitle("Bank Application");
          setSize(300, 200);
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          mainPanel();
          add(panel);
          setVisible(true);
     }

     private void mainPanel() 
     {
          depositButton = new JButton("Deposit");
          depositButton.addActionListener(new ActionListener() 
          {
               public void actionPerformed(ActionEvent e) 
               {
                    deposit();
               }
          });
          withdrawButton = new JButton("Withdraw");
          withdrawButton.addActionListener(new ActionListener()
          {
               public void actionPerformed(ActionEvent e) 
               {
                    withdraw();
               }
          });
          exitButton = new JButton("Exit");
          exitButton.addActionListener(new ActionListener() 
          {
               public void actionPerformed(ActionEvent e) 
               {
                    JOptionPane.showMessageDialog(null, "Current Balance: $" + balance);
                    System.exit(0);
               }
          });
          amountField = new JTextField(10);
          balanceLabel = new JLabel("Balance: $" + balance);
          actionLabel = new JLabel("Enter amount: ");

          panel = new JPanel();
          panel.setLayout(new GridLayout(4, 2));
          panel.add(actionLabel);
          panel.add(amountField);
          panel.add(depositButton);
          panel.add(withdrawButton);
          panel.add(exitButton);
          panel.add(balanceLabel);
     }

     private void deposit() {
          try 
          {
               double amount = Double.parseDouble(amountField.getText());
               balance += amount;
               balanceLabel.setText("Balance: $" + balance);
          }
          catch (NumberFormatException e) 
          {
               JOptionPane.showMessageDialog(null, "Please enter a correct number.");
          }
     }

     private void withdraw() 
     {
          try 
          {
               double amount = Double.parseDouble(amountField.getText());
               if (amount <= balance) 
               {
                    balance -= amount;
                    balanceLabel.setText("Balance: $" + balance);
               } 
               else 
               {
                    JOptionPane.showMessageDialog(null, "Insufficient funds.");
               }
          } 
          catch (NumberFormatException e) 
          {
               JOptionPane.showMessageDialog(null, "Please enter a correct number.");
          }
     }

     public static void main(String[] args) 
     {
        new BankBalance();
     }
}
