
import java.io.*;
import java.util.HashMap;

public class ATMManager {

    private HashMap<String, Account> accounts = new HashMap<>();
    private final String FILE = "accounts.dat";

    public ATMManager() {
        loadAccounts();

        if (accounts.isEmpty()) {
            Account demo = new Account("1234567890", "1234", 10000);
            accounts.put(demo.getAccountNumber(), demo);
            saveAccounts();
        }
    }

    public Account login(String accountNumber, String pin) {
        Account account = accounts.get(accountNumber);

        if (account != null && account.validatePin(pin)) {
            return account;
        }

        return null;
    }

    public void saveAccounts() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE))) {
            out.writeObject(accounts);
        } catch (IOException e) {
            System.out.println("Unable to save account data.");
        }
    }

    @SuppressWarnings("unchecked")
    private void loadAccounts() {
        File file = new File(FILE);

        if (!file.exists())
            return;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE))) {
            accounts = (HashMap<String, Account>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            accounts.clear();
        }
    }
}
