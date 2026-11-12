
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField accountField;
    private JPasswordField pinField;
    private ATMManager manager;

    public LoginFrame() {

        manager = new ATMManager();

        setTitle("ATM Transaction Simulator");
        setSize(900, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2));

        // Left panel
        JPanel left = new JPanel();
        left.setBackground(new Color(24, 39, 75));
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBorder(BorderFactory.createEmptyBorder(80, 45, 50, 35));

        JLabel brand = new JLabel("ATM BANKING");
        brand.setFont(new Font("SansSerif", Font.BOLD, 27));
        brand.setForeground(Color.WHITE);

        JLabel title = new JLabel("<html>Banking<br>Made Simple.</html>");
        title.setFont(new Font("SansSerif", Font.BOLD, 36));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
                "<html>Secure. Simple. Convenient.<br>Manage your money with ease.</html>");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 15));
        subtitle.setForeground(new Color(195, 207, 230));

        left.add(brand);
        left.add(Box.createVerticalStrut(75));
        left.add(title);
        left.add(Box.createVerticalStrut(20));
        left.add(subtitle);

        // Right panel
        JPanel right = new JPanel();
        right.setBackground(Color.WHITE);
        right.setLayout(new GridBagLayout());

        JPanel form = new JPanel();
        form.setPreferredSize(new Dimension(310, 350));
        form.setBackground(Color.WHITE);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel("Welcome Back");
        heading.setFont(new Font("SansSerif", Font.BOLD, 27));
        heading.setForeground(new Color(24, 39, 75));

        JLabel description = new JLabel("Login to your account");
        description.setForeground(Color.GRAY);

        JLabel accountLabel = new JLabel("Account Number");
        accountLabel.setFont(new Font("SansSerif", Font.BOLD, 13));

        accountField = new JTextField();
        accountField.setMaximumSize(new Dimension(310, 42));
        accountField.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JLabel pinLabel = new JLabel("PIN");
        pinLabel.setFont(new Font("SansSerif", Font.BOLD, 13));

        pinField = new JPasswordField();
        pinField.setMaximumSize(new Dimension(310, 42));
        pinField.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JButton loginButton = new JButton("Login to Account");
        loginButton.setMaximumSize(new Dimension(310, 45));
        loginButton.setBackground(new Color(67, 97, 238));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel demo = new JLabel(
                "<html><br>Demo Account: 1234567890<br>Demo PIN: 1234</html>");
        demo.setForeground(Color.GRAY);
        demo.setFont(new Font("SansSerif", Font.PLAIN, 12));

        form.add(heading);
        form.add(Box.createVerticalStrut(8));
        form.add(description);
        form.add(Box.createVerticalStrut(35));
        form.add(accountLabel);
        form.add(Box.createVerticalStrut(8));
        form.add(accountField);
        form.add(Box.createVerticalStrut(20));
        form.add(pinLabel);
        form.add(Box.createVerticalStrut(8));
        form.add(pinField);
        form.add(Box.createVerticalStrut(25));
        form.add(loginButton);
        form.add(demo);

        right.add(form);

        add(left);
        add(right);

        loginButton.addActionListener(e -> login());
        pinField.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {

        String accountNumber = accountField.getText().trim();
        String pin = new String(pinField.getPassword());

        Account account = manager.login(accountNumber, pin);

        if (account != null) {
            new ATMDashboard(account, manager);
            dispose();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid account number or PIN.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
