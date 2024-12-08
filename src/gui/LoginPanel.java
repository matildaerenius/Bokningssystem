package gui;

import data.UserDataManager;
import models.User;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    public LoginPanel(ViewManager parentFrame, UserDataManager userDataManager) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 500));

        //Skapar en skalad bild
        ImageIcon scaledIcon = ImageFactory.createScaledImageIcon("src/resources/background.jpg", 400, 500);

        // Lägger den skalade bilden som bakgrunden
        JLabel backgroundLabel = new JLabel(scaledIcon);
        backgroundLabel.setLayout(new BorderLayout()); // Gör så att komponenter kan placeras ovanpå
        add(backgroundLabel);

        // Texten högst upp
        JLabel headerText = new JLabel("Logga in", SwingConstants.CENTER);
        headerText.setFont(new Font("Times New Roman", Font.BOLD, 30));
        headerText.setForeground(Color.BLACK);
        headerText.setBorder(BorderFactory.createEmptyBorder(40, 0, 10, 0)); // Padding runt texten

        // Panel för inloggningsfälten
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
        fieldPanel.setOpaque(false); // Gör panelen genomskinlig
        fieldPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel idLabel = new JLabel("Personnummer / Org.nummer:");
        idLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        JTextField idField = new JTextField();
        idField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        idField.setMaximumSize(new Dimension(550, 25));

        JLabel passwordLabel = new JLabel("Lösenord:");
        passwordLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        passwordField.setMaximumSize(new Dimension(550, 25));

        // Checkbox för att visa/dölja lösenordet
        JCheckBox showPasswordCheckBox = new JCheckBox("Visa lösenord");
        showPasswordCheckBox.setOpaque(false); // Gör checkboxen genomskinlig
        showPasswordCheckBox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        showPasswordCheckBox.addActionListener(e -> {
            if (showPasswordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0); // Visar texten i lösenordsfältet
            } else {
                passwordField.setEchoChar('*'); // Döljer texten med stjärnor
            }
        });

        fieldPanel.add(idLabel);
        fieldPanel.add(idField);
        fieldPanel.add(Box.createVerticalStrut(15));
        fieldPanel.add(passwordLabel);
        fieldPanel.add(passwordField);
        fieldPanel.add(Box.createVerticalStrut(10));
        fieldPanel.add(showPasswordCheckBox);
        fieldPanel.add(Box.createVerticalStrut(20));

        backgroundLabel.add(fieldPanel, BorderLayout.CENTER);
        backgroundLabel.add(headerText, BorderLayout.NORTH);

        // Panel för knappar
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Gör panelen genomskinlig
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton loginButton = new JButton("Logga in");
        loginButton.setFocusable(false);
        loginButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        loginButton.setPreferredSize(new Dimension(150, 30));
        loginButton.setBackground(Color.WHITE);
        loginButton.setForeground(Color.BLACK);

        JButton backButton = new JButton("Tillbaka");
        backButton.setFocusable(false);
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);

        // Action listener för loginButton
        loginButton.addActionListener(e -> {
            String id = idField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            // Kontrollera att fälten inte är tomma
            if (id.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Alla fält måste vara ifyllda.", "Felmeddelande", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Läser från fil
            User user = UserDataManager.getInstance().authenticateUser(id, password);
            if (user != null) {
                JOptionPane.showMessageDialog(this, "Inloggad som: " + user.getName());
                parentFrame.showBookingPanel(user);
            } else {
                JOptionPane.showMessageDialog(this, "Felaktiga inloggningsuppgifter.", "Felmeddelande", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action listener för backButton
        backButton.addActionListener(e -> parentFrame.showCard("Start"));

        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);

        backgroundLabel.add(buttonPanel, BorderLayout.SOUTH);
    }
}
