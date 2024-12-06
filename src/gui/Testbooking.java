package gui;

import javax.swing.*;

public class Testbooking {
    // TODO: TILLFÄLLIG, enbart för test av BookingPanel, ta bort sen :)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test BookingPanel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            BookingPanel bookingPanel = new BookingPanel(true, "test@example.com");

            frame.add(bookingPanel);
            frame.setSize(600, 500);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
