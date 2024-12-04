package gui;

import javax.swing.*;
import java.awt.*;

public class RegistrationPanel extends JPanel {

    public RegistrationPanel(BookingSystem parentFrame) {
        setLayout(new GridLayout(7, 2, 10, 10));

        JLabel firstNameLabel = new JLabel("Förnamn:");
        JLabel lastNameLabel = new JLabel("Efternamn:");
        JLabel idLabel = new JLabel("Personnummer:");
        JLabel phonenumberLabel = new JLabel("Telefonnummer:");
        JLabel emailLabel = new JLabel("E-postadress:");
        JLabel passwordLabel = new JLabel("Lösenord:");

        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField idField = new JTextField();
        JTextField phonenumberField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JButton registerButton = new JButton("Registrera");
        JButton backButton = new JButton("Tillbaka");

        registerButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String id = idField.getText();
            String phonenumber = phonenumberField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            // TODO Registreringslogik
            JOptionPane.showMessageDialog(this, "Registrerad:\n" + firstName + " " + lastName);
            parentFrame.showCard("Login");
        });

        backButton.addActionListener(e -> parentFrame.showCard("Login"));

        add(firstNameLabel);
        add(firstNameField);
        add(lastNameLabel);
        add(lastNameField);
        add(idLabel);
        add(idField);
        add(phonenumberLabel);
        add(phonenumberField);
        add(emailLabel);
        add(emailField);
        add(passwordLabel);
        add(passwordField);
        add(registerButton);
        add(backButton);
    }
}
