package gui;

import javax.swing.*;
import java.awt.*;

public class CompanyLoginPanel extends JPanel {

    public CompanyLoginPanel(BookingSystem parentFrame) {
        setLayout(new GridLayout(3, 2, 10, 10));

        JLabel idLabel = new JLabel("Org.nummer:");
        JLabel passwordLabel = new JLabel("Lösenord:");
        JTextField idField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Logga in");
        JButton backButton = new JButton("Tillbaka");

        loginButton.addActionListener(e -> {
            String id = idField.getText();
            String password = new String(passwordField.getPassword());

            JOptionPane.showMessageDialog(this, "Inloggad som företag: " + id);
            parentFrame.showCard("Login");
        });

        backButton.addActionListener(e -> parentFrame.showCard("Login"));

        add(idLabel);
        add(idField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(backButton);
    }
}
