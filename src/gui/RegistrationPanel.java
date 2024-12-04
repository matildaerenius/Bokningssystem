package gui;

import javax.swing.*;
import java.awt.*;

public class RegistrationPanel extends JPanel {

    //TODO egen enum klass för dessa
    //Index i String[] labelNames[] för att göra det tydligare vad som är på vilken plats
    private static final int FIRSTNAME = 0;
    private static final int LASTNAME = 1;
    private static final int PERSON_NR = 2;
    private static final int TELEFON_NR = 3;
    private static final int EPOST = 4;

    public RegistrationPanel(ViewManager parentFrame) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 550));

        //Skapar en skalad bild
        ImageIcon scaledIcon = ImageFactory.createScaledImageIcon("src/resources/background.jpg", 400, 500);

        // Lägger den skalade bilden som bakgrunden
        JLabel backgroundLabel = new JLabel(scaledIcon);
        backgroundLabel.setLayout(new BorderLayout()); // Gör så att komponenter kan placeras ovanpå
        add(backgroundLabel);


        // Panel för registreringsfält
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
        fieldPanel.setOpaque(false); // Gör panelen genomskinlig
        fieldPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //Array med namn på knapparna
        String[] labelNames = new String[]{
                "Förnamn:",
                "Efternamn:",
                "Personnummer (yymmddxxxx):",
                "Telefonnummer:",
                "Epostadress:"
        };

        JLabel[] labels = new JLabel[labelNames.length]; //Arrays för att spara labels och fields
        JTextField[] fields = new JTextField[labelNames.length];

        //loopar igenom labelNames
        for (int i = 0; i < labelNames.length; i++) {
            labels[i] = new JLabel(labelNames[i]); //label med texten som är i labelNames på det indexet
            labels[i].setFont(new Font("Times New Roman", Font.BOLD, 16));
            fieldPanel.add(labels[i]); //lägger till i fieldPanel
            fields[i] = new JTextField();
            fields[i].setFont(new Font("Times New Roman", Font.PLAIN, 14));
            fieldPanel.add(fields[i]); //lägger till i fieldPanel
            fieldPanel.add(Box.createVerticalStrut(10)); //Box för mellanrum
        }

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
            String firstName = fields[FIRSTNAME].getText();
            String lastName = fields[LASTNAME].getText();
            String id = fields[PERSON_NR].getText();
            String phonenumber = fields[TELEFON_NR].getText();
            String email = fields[EPOST].getText();
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
