public class BankAccount {
     private String fName;
     private String lName;
     private int accountID;
     private double balance;
 
     public BankAccount() 
     {
         this.balance = 0.0;
     }
 
     public String getFName() 
     {
         return fName;
     }
     public void setFName(String fName) 
     {
         this.fName = fName;
     }
     public String getLName() 
     {
         return lName;
     }
     public void setLName(String lName) 
     {
         this.lName = lName;
     }
     public int getAccountID() 
     {
         return accountID;
     }
     public void setAccountID(int accountID) 
     {
         this.accountID = accountID;
     }
     public double getBalance() 
     {
         return balance;
     }
 
     public void deposit(double amount) 
     {
         balance += amount;
     }
 
     public void withdrawal(double amount) 
     {
         balance -= amount;
     }
 
     public void accountSummary() 
     {
         System.out.println("Account Holder: " + fName + " " + lName);
         System.out.println("Account ID: " + accountID);
         System.out.println("Balance: $" + balance);
     }

     public static void main(String[] args) 
     {
          BankAccount account1 = new BankAccount();
          account1.setFName("Jesse");
          account1.setLName("Canell");
          account1.setAccountID(77477);
          account1.deposit(1000);
          account1.withdrawal(1100);
          account1.accountSummary();

          System.out.println();

          CheckingAccount account2 = new CheckingAccount();
          account2.setFName("Diana");
          account2.setLName("Ayala");
          account2.setAccountID(1001);
          account2.deposit(2000);
          account2.withdrawal(2100);
          account2.displayAccount();
      }
 }
 