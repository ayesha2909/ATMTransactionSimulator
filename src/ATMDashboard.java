
import javax.swing.*;
import java.awt.*;

public class ATMDashboard extends JFrame {

    private Account account;
    private ATMManager manager;
    private JLabel balanceLabel;
    private JTextArea historyArea;

    private final Color BLUE = new Color(67, 97, 238);
    private final Color NAVY = new Color(24, 39, 75);
    private final Color BG = new Color(245, 247, 251);

    public ATMDashboard(Account account, ATMManager manager) {

        this.account = account;
        this.manager = manager;

        setTitle("ATM Banking Dashboard");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        createHeader();
        createDashboard();

        setVisible(true);
    }

    private void createHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NAVY);
        header.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel("ATM BANKING");
        title.setFont(new Font("SansSerif", Font.BOLD, 21));
        title.setForeground(Color.WHITE);

        JButton logout = new JButton("Logout");
        logout.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        header.add(title, BorderLayout.WEST);
        header.add(logout, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);
    }

    private void createDashboard() {

        JPanel main = new JPanel(new BorderLayout(20, 20));
        main.setBackground(BG);
        main.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setOpaque(false);

        JLabel heading = new JLabel("Dashboard");
        heading.setFont(new Font("SansSerif", Font.BOLD, 28));
        heading.setForeground(NAVY);

        JLabel subtitle = new JLabel(
                "Welcome! Manage your banking transactions.");
        subtitle.setForeground(Color.GRAY);

        top.add(heading);
        top.add(Box.createVerticalStrut(8));
        top.add(subtitle);

        JPanel balanceCard = new JPanel();
        balanceCard.setLayout(new BoxLayout(balanceCard, BoxLayout.Y_AXIS));
        balanceCard.setBackground(BLUE);
        balanceCard.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel balanceTitle = new JLabel("AVAILABLE BALANCE");
        balanceTitle.setForeground(new Color(220, 225, 255));
        balanceTitle.setFont(new Font("SansSerif", Font.BOLD, 13));

        balanceLabel = new JLabel();
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setFont(new Font("SansSerif", Font.BOLD, 36));

        balanceCard.add(balanceTitle);
        balanceCard.add(Box.createVerticalStrut(15));
        balanceCard.add(balanceLabel);

        updateBalance();

        JPanel actions = new JPanel(new GridLayout(1, 2, 20, 0));
        actions.setOpaque(false);

        JButton deposit = createButton("Deposit Money");
        JButton withdraw = createButton("Withdraw Money");

        deposit.addActionListener(e -> depositMoney());
        withdraw.addActionListener(e -> withdrawMoney());

        actions.add(deposit);
        actions.add(withdraw);

        JPanel historyCard = new JPanel(new BorderLayout(10, 10));
        historyCard.setBackground(Color.WHITE);
        historyCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel historyTitle = new JLabel("Transaction History");
        historyTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        historyTitle.setForeground(NAVY);

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        historyArea.setText("No transactions yet.");

        JScrollPane scroll = new JScrollPane(historyArea);

        historyCard.add(historyTitle, BorderLayout.NORTH);
        historyCard.add(scroll, BorderLayout.CENTER);

        updateHistory();

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        balanceCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));
        actions.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));

        center.add(balanceCard);
        center.add(Box.createVerticalStrut(20));
        center.add(actions);
        center.add(Box.createVerticalStrut(20));
        center.add(historyCard);

        main.add(top, BorderLayout.NORTH);
        main.add(center, BorderLayout.CENTER);

        add(main, BorderLayout.CENTER);
    }

    private JButton createButton(String text) {

        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setForeground(BLUE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void depositMoney() {

        String value = JOptionPane.showInputDialog(
                this, "Enter deposit amount:");

        if (value == null)
            return;

        try {
            double amount = Double.parseDouble(value);

            if (amount <= 0 || !Double.isFinite(amount)) {
                throw new NumberFormatException();
            }

            account.deposit(amount);
            manager.saveAccounts();

            updateBalance();
            updateHistory();

            JOptionPane.showMessageDialog(this, "Deposit successful!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a valid amount.");
        }
    }

    private void withdrawMoney() {

        String value = JOptionPane.showInputDialog(
                this, "Enter withdrawal amount:");

        if (value == null)
            return;

        try {
            double amount = Double.parseDouble(value);

            if (!Double.isFinite(amount) || amount <= 0) {
                throw new NumberFormatException();
            }

            if (!account.withdraw(amount)) {
                JOptionPane.showMessageDialog(
                        this, "Insufficient balance.");
                return;
            }

            manager.saveAccounts();

            updateBalance();
            updateHistory();

            JOptionPane.showMessageDialog(this, "Withdrawal successful!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a valid amount.");
        }
    }

    private void updateBalance() {

        balanceLabel.setText(
                String.format("₹ %,.2f", account.getBalance()));
    }

    private void updateHistory() {

        StringBuilder history = new StringBuilder();

        for (String transaction : account.getTransactions()) {
            history.append("• ").append(transaction).append("\n");
        }

        historyArea.setText(
                history.length() == 0
                        ? "No transactions yet."
                        : history.toString());
    }
}
