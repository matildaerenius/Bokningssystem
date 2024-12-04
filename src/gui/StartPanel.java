package gui;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {

    public StartPanel(BookingSystem parentFrame) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 500));

        // Laddar bilden och skalar om den
        ImageIcon originalIcon = new ImageIcon("src/resources/background.jpg");
        Image originalImage = originalIcon.getImage(); // Hämtar bilden som en Image
        Image scaledImage = originalImage.getScaledInstance(400, 500, Image.SCALE_SMOOTH); // Skalar om bilden
        ImageIcon scaledIcon = new ImageIcon(scaledImage); // Skapar en ny ImageIcon med den skalade bilden

        // Lägger den skalade bilden som bakgrunden
        JLabel backgroundLabel = new JLabel(scaledIcon);
        backgroundLabel.setLayout(new BorderLayout()); // Gör så att komponenter kan placeras ovanpå
        add(backgroundLabel);

        // Texten högst upp
        JLabel welcomeLabel = new JLabel("Logga in", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 26));
        welcomeLabel.setForeground(Color.BLACK);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Padding runt texten

        // Panel för inloggningsknappar
        JPanel loginButtonPanel = new JPanel();
        loginButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Gör så att knapparna ligger bredvid varandra
        loginButtonPanel.setOpaque(false); // Gör panelen genomskinlig

        // Knappar för inloggning
        JButton userLoginButton = new JButton("Som kund");
        userLoginButton.addActionListener(e -> parentFrame.showCard("UserLogin"));

        JButton businessLoginButton = new JButton("Som företag");
        businessLoginButton.addActionListener(e -> parentFrame.showCard("BusinessLogin"));


        // Stylar knapparna
        JButton[] loginButtons = {userLoginButton, businessLoginButton};
        for (JButton button : loginButtons) {
            button.setFont(new Font("Times New Roman", Font.BOLD, 16));
            button.setPreferredSize(new Dimension(200, 40));
            button.setFocusable(false);
            button.setBackground(Color.WHITE); // TILLFÄLLIG FÄRG
            button.setForeground(Color.BLACK);
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Lägger till kant
        }

        loginButtonPanel.add(userLoginButton);
        loginButtonPanel.add(businessLoginButton);


        // "Registrera dig"-länken
        JLabel registerLabel = new JLabel("<html><u>Registrera dig</u></html>", SwingConstants.CENTER);
        registerLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        registerLabel.setForeground(Color.BLUE);
        registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Gör muspekaren till en hand vid hovring över
        registerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 60, 0)); // Padding ovanför länken

        // MouseListnener för "länken"
        registerLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                parentFrame.showCard("Register");
            }
        });

        // Lägger till texten och komponenterna till bakgrundslabel
        backgroundLabel.add(welcomeLabel, BorderLayout.NORTH);
        backgroundLabel.add(loginButtonPanel, BorderLayout.CENTER);
        backgroundLabel.add(registerLabel, BorderLayout.SOUTH);
    }
}
