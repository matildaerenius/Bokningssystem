package gui;

import javax.swing.*;
import java.awt.*;

public class ViewManager extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ViewManager::new);
    }

    public ViewManager() {
        setTitle("Bokningssystem");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setResizable(false);
        setFocusable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Lägger till de olika windowsen
        mainPanel.add(new StartPanel(this), "Start");
        mainPanel.add(new LoginPanel(this), "Login");
        mainPanel.add(new RegistrationPanel(this), "Register");

        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void showCard(String cardName) {
        cardLayout.show(mainPanel, cardName);
    }
}


