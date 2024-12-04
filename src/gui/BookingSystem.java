package gui;

import javax.swing.*;
import java.awt.*;

public class BookingSystem extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BookingSystem::new);
    }

    public BookingSystem() {
        setTitle("Bokningssystem");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setResizable(false);
        setFocusable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Lägger till olika fönster
        mainPanel.add(new StartPanel(this), "Login");
        mainPanel.add(new CustomerLoginPanel(this), "CustomerLogin");
        mainPanel.add(new CompanyLoginPanel(this), "CompanyLogin");
        mainPanel.add(new RegisterPanel(this), "Register");

        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void showCard(String cardName) {
        cardLayout.show(mainPanel, cardName);
    }
}


