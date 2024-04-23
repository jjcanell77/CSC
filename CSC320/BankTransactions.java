import java.util.ArrayList;
import java.util.List;

public class BankTransactions {
    private List<String> transactions;

    public BankTransactions() {
        transactions = new ArrayList<>();
    }

    public void add(String transaction) {
        transactions.add(transaction);
    }

    public boolean contains(String transaction) {
        return transactions.contains(transaction);
    }

    public int size() {
        return transactions.size();
    }

    public boolean remove(String transaction) {
        return transactions.remove(transaction);
    }

    public static void main(String[] args) {
        BankTransactions bag = new BankTransactions();

        bag.add("Deposit");
        bag.add("Withdrawal");
        bag.add("Deposit");
        bag.add("Transfer");
        bag.add("Deposit");
        bag.add("Withdrawal");

        System.out.println("Number of transactions: " + bag.size());
        System.out.println("Contains 'Deposit': " + bag.contains("deposit"));

        bag.remove("Transfer");
        System.out.println("Number of transactions minus 'Transfer': " + bag.size());
    }
}
