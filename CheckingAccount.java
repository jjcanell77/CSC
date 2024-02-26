public class CheckingAccount extends BankAccount {
     private double interestRate;
 
     public CheckingAccount() 
     {
          super();
          this.interestRate = 0.4;
     }
 
     public double getInterestRate() 
     {
          return interestRate;
     }

     public void setInterestRate(double interestRate) 
     {
          this.interestRate = interestRate;
     }
 
     @Override
     public void withdrawal(double amount)
     {
          if (getBalance() - amount < 0) 
          {
               processWithdrawal(amount);
          } 
          else 
          {
               super.withdrawal(amount);
          }
     }

     private void processWithdrawal(double amount)
     {
          double overdraftFee = 30.0;
          System.out.println("Warning: Overdraft fee charged.");
          super.withdrawal(amount + overdraftFee);
     }
 
     public void displayAccount() 
     {
         accountSummary();
         System.out.println("Interest Rate: " + interestRate);
     }
 }
 