package gui;

import javax.swing.*;
import java.awt.*;

public class RegistrationPanel extends JPanel {

    public RegistrationPanel(ViewManager parentFrame) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 550));

        // Laddar bilden och skalar om den
        ImageIcon originalIcon = new ImageIcon("src/resources/background.jpg");
        Image originalImage = originalIcon.getImage(); // Hämtar bilden som en Image
        Image scaledImage = originalImage.getScaledInstance(400, 500, Image.SCALE_SMOOTH); // Skalar om bilden
        ImageIcon scaledIcon = new ImageIcon(scaledImage); // Skapar en ny ImageIcon med den skalade bilden

        // Lägger den skalade bilden som bakgrunden
        JLabel backgroundLabel = new JLabel(scaledIcon);
        backgroundLabel.setLayout(new BorderLayout()); // Gör så att komponenter kan placeras ovanpå
        add(backgroundLabel);


        // Panel för registreringsfält
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
        fieldPanel.setOpaque(false); // Gör panelen genomskinlig
        fieldPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // TODO: förkorta, lista och for loop?
        JLabel firstNameLabel = new JLabel("Förnamn:");
        firstNameLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        JTextField firstNameField = new JTextField();
        firstNameField.setFont(new Font("Times New Roman", Font.PLAIN, 14));

        JLabel lastNameLabel = new JLabel("Efternamn:");
        lastNameLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        JTextField lastNameField = new JTextField();
        lastNameField.setFont(new Font("Times New Roman", Font.PLAIN, 14));

        JLabel idLabel = new JLabel("Personnummer (yymmddxxxx):");
        idLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        JTextField idField = new JTextField();
        idField.setFont(new Font("Times New Roman", Font.PLAIN, 14));

        JLabel phonenumberLabel = new JLabel("Telefonnummer:");
        phonenumberLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        JTextField phonenumberField = new JTextField();
        phonenumberField.setFont(new Font("Times New Roman", Font.PLAIN, 14));

        JLabel emailLabel = new JLabel("E-postadress:");
        emailLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        JTextField emailField = new JTextField();
        emailField.setFont(new Font("Times New Roman", Font.PLAIN, 14));

        JLabel passwordLabel = new JLabel("Lösenord:");
        passwordLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 14));

        // Checkbox för att visa/dölja lösenord
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

        // Lägger till komponenter till fieldPanel
        fieldPanel.add(firstNameLabel);
        fieldPanel.add(firstNameField);
        fieldPanel.add(Box.createVerticalStrut(10));
        fieldPanel.add(lastNameLabel);
        fieldPanel.add(lastNameField);
        fieldPanel.add(Box.createVerticalStrut(10));
        fieldPanel.add(idLabel);
        fieldPanel.add(idField);
        fieldPanel.add(Box.createVerticalStrut(10));
        fieldPanel.add(phonenumberLabel);
        fieldPanel.add(phonenumberField);
        fieldPanel.add(Box.createVerticalStrut(10));
        fieldPanel.add(emailLabel);
        fieldPanel.add(emailField);
        fieldPanel.add(Box.createVerticalStrut(10));
        fieldPanel.add(passwordLabel);
        fieldPanel.add(passwordField);
        fieldPanel.add(Box.createVerticalStrut(10));
        fieldPanel.add(showPasswordCheckBox);

        backgroundLabel.add(fieldPanel, BorderLayout.CENTER);

        // Panel för knappar
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Gör panelen genomskinlig
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton registerButton = new JButton("Registrera dig");
        registerButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        registerButton.setPreferredSize(new Dimension(150, 30));
        registerButton.setBackground(Color.WHITE);
        registerButton.setForeground(Color.BLACK);

        JButton backButton = new JButton("Tillbaka");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);

        // ActionListeners för knappar
        registerButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String id = idField.getText();
            String phonenumber = phonenumberField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            // Kontrollerar att alla fält är ifyllda
            if (firstName.isEmpty() || lastName.isEmpty() || id.isEmpty() ||
                    phonenumber.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Alla fält måste vara ifyllda.", "Felmeddelande", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Kontrollerar att persnr består av siffror och endast 10st
        if (id.length() != 10 || !id.chars().allMatch(Character::isDigit)) {
            JOptionPane.showMessageDialog(this, "Ange personnummer i tio siffror", "Felmeddelande", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Kontrollerar att telnr består av siffror och endast 10st
        if (phonenumber.length() != 10 || !phonenumber.chars().allMatch(Character::isDigit)) {
            JOptionPane.showMessageDialog(this, "Ange ett korrekt telefonnummer", "Felmeddelande", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Kontrollerar om e-postadressen innehåller @
        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Ange en korrekt e-postadress", "Felmeddelande", JOptionPane.ERROR_MESSAGE);
            return;
        }

            // TODO: Lägg till registreringslogik, nedan är om registeringen sköts i någon form av UserDataManager för att spara till fil
//            User newUser = UserFactory.createUser("Customer", id, firstName + " " + lastName, email, password);
//            UserDataManager.getInstance().registerUser(newUser);
//
//            JOptionPane.showMessageDialog(this, "Registrering lyckades!");
//            parentFrame.showCard("Login");
        });

        backButton.addActionListener(e -> parentFrame.showCard("Start"));

        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);

        backgroundLabel.add(buttonPanel, BorderLayout.SOUTH);
    }
}
