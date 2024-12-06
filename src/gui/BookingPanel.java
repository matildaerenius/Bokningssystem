package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingPanel extends JPanel {
    // TODO: TILLFÄLLIG ish, hela denna klass kan förkortas och göras allmänt snyggare och lägga funktionaliteten i annan klass, behövde bara se hur det ser ut :)
    private LocalDate currentMonth;
    private JTable calendarTable;
    private DefaultTableModel calendarModel;
    private JLabel monthLabel;
    private JPanel timePanel;
    private List<String> availableTimes;
    private Map<LocalDate, List<String>> timeSlots;
    private boolean isAdmin;
    private String currentUserEmail;

    public BookingPanel(boolean isAdmin, String userEmail) {
        this.isAdmin = isAdmin;
        this.currentUserEmail = userEmail;
        currentMonth = LocalDate.now();
        timeSlots = new HashMap<>();
        availableTimes = List.of("09:00", "10:00", "11:00", "14:00", "15:00", "16:00");

        setLayout(new BorderLayout());

        ImageIcon scaledIcon = ImageFactory.createScaledImageIcon("src/resources/background.jpg", 600, 500);
        JLabel backgroundLabel = new JLabel(scaledIcon);
        backgroundLabel.setLayout(new BorderLayout());
        add(backgroundLabel);


        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        setupCalendar(mainPanel);
        setupTimePanel(mainPanel);

        backgroundLabel.add(mainPanel, BorderLayout.CENTER);
    }

    private void setupCalendar(JPanel parentPanel) {
        JPanel calendarPanel = new JPanel(new BorderLayout());
        calendarPanel.setOpaque(false);
        calendarPanel.setPreferredSize(new Dimension(250, 300));

        // Månadsrubriken
        monthLabel = new JLabel("", SwingConstants.CENTER);
        monthLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));

        // Knappar för att navigera mellan månader
        JButton prevButton = new JButton("<");
        JButton nextButton = new JButton(">");

        prevButton.setPreferredSize(new Dimension(40, 25));
        nextButton.setPreferredSize(new Dimension(40, 25));
        Font buttonFont = new Font("Times New Roman", Font.BOLD, 11);
        prevButton.setFocusable(false);
        nextButton.setFocusable(false);
        prevButton.setBackground(Color.WHITE);
        nextButton.setBackground(Color.WHITE);
        prevButton.setFont(buttonFont);
        nextButton.setFont(buttonFont);

        prevButton.addActionListener(e -> navigateMonth(-1));
        nextButton.addActionListener(e -> navigateMonth(1));

        // Panel för kalenderns header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(prevButton, BorderLayout.WEST);
        headerPanel.add(monthLabel, BorderLayout.CENTER);
        headerPanel.add(nextButton, BorderLayout.EAST);

        calendarModel = new DefaultTableModel(null, new String[]{"Mån", "Tis", "Ons", "Tors", "Fre", "Lör", "Sön"});
        calendarTable = new JTable(calendarModel) {
            public boolean isCellEditable(int row, int column) {
                return false; // Gör så att cellerna inte kan redigeras
            }
        };
        calendarTable.setRowHeight(30);
        calendarTable.setCellSelectionEnabled(true);
        calendarTable.getSelectionModel().addListSelectionListener(e -> updateTimePanel());

        JScrollPane calendarScrollPane = new JScrollPane(calendarTable);
        calendarScrollPane.setOpaque(false);
        calendarScrollPane.getViewport().setOpaque(false);
        calendarScrollPane.setPreferredSize(new Dimension(250, 250));
        calendarPanel.add(headerPanel, BorderLayout.NORTH);
        calendarPanel.add(calendarScrollPane, BorderLayout.CENTER);

        parentPanel.add(calendarPanel, BorderLayout.WEST);
        updateCalendar();
    }

    private void setupTimePanel(JPanel parentPanel) {
        timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        timePanel.setOpaque(false);
        timePanel.setBorder(BorderFactory.createTitledBorder("Tillgängliga tider"));

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(timePanel, BorderLayout.CENTER);

        if (isAdmin) {
            JButton addTimeButton = new JButton("Lägg till tid");
            addTimeButton.addActionListener(e -> addTimeSlot());
            wrapper.add(addTimeButton, BorderLayout.SOUTH);
        }

        wrapper.setPreferredSize(new Dimension(250, 300));
        parentPanel.add(wrapper, BorderLayout.CENTER);
    }

    private void navigateMonth(int direction) {
        currentMonth = currentMonth.plusMonths(direction);
        updateCalendar();
    }

    // Uppdaterar kalendern med aktuella data
    private void updateCalendar() {
        monthLabel.setText(currentMonth.getMonth().toString() + " " + currentMonth.getYear());
        calendarModel.setRowCount(0); // Rensar tidigare data

        LocalDate firstDay = currentMonth.withDayOfMonth(1);
        int startDayOfWeek = firstDay.getDayOfWeek().getValue();
        LocalDate currentDay = firstDay.minusDays(startDayOfWeek - 1);

        // Fyller tabellen med dagar
        for (int row = 0; row < 6; row++) {
            Object[] week = new Object[7];
            for (int col = 0; col < 7; col++) {
                if (currentDay.getMonth() == currentMonth.getMonth()) {
                    week[col] = currentDay.getDayOfMonth();
                } else {
                    week[col] = ""; // Lämnar tomma celler för dagar utanför månaden
                }
                currentDay = currentDay.plusDays(1);
            }
            calendarModel.addRow(week);
        }
    }

    // Uppdaterar tidsbokningspanelen baserat på valt datum
    private void updateTimePanel() {
        int selectedRow = calendarTable.getSelectedRow();
        int selectedCol = calendarTable.getSelectedColumn();
        if (selectedRow < 0 || selectedCol < 0) return;

        Object dayObj = calendarTable.getValueAt(selectedRow, selectedCol);
        if (dayObj == null || dayObj.toString().isEmpty()) return;

        LocalDate selectedDate = currentMonth.withDayOfMonth(Integer.parseInt(dayObj.toString()));
        timePanel.removeAll(); // Rensar tidigare tider

        List<String> times = timeSlots.getOrDefault(selectedDate, new ArrayList<>(availableTimes));
        for (String time : times) {
            JButton timeButton = new JButton(time);

            timeButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
            timeButton.setPreferredSize(new Dimension(80, 25));
            timeButton.setBackground(Color.WHITE);
            timeButton.setForeground(Color.BLACK);
            timeButton.setFocusable(false);


            timeButton.setEnabled(!isAdmin || !timeSlots.containsKey(selectedDate) || timeSlots.get(selectedDate).contains(time));
            timeButton.addActionListener(e -> handleBooking(selectedDate, time));
            timePanel.add(timeButton);
        }
        timePanel.revalidate();
        timePanel.repaint();
    }

    private void handleBooking(LocalDate date, String time) {
        int choice = JOptionPane.showConfirmDialog(this,
                "Vill du boka denna tid? " + date + " kl " + time,
                "Bekräfta bokning",
                JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            bookTimeSlot(date, time);
        }
    }

    private void bookTimeSlot(LocalDate date, String time) {
        timeSlots.computeIfAbsent(date, d -> new ArrayList<>()).remove(time);
        JOptionPane.showMessageDialog(this, "Tiden har bokats: " + date + " kl " + time);
        updateTimePanel();
    }

    private void addTimeSlot() {
        int selectedRow = calendarTable.getSelectedRow();
        int selectedCol = calendarTable.getSelectedColumn();
        if (selectedRow < 0 || selectedCol < 0) {
            JOptionPane.showMessageDialog(this, "Välj ett datum i kalendern först!", "Fel", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Object dayObj = calendarTable.getValueAt(selectedRow, selectedCol);
        if (dayObj == null || dayObj.toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Välj ett giltigt datum i kalendern först!", "Fel", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDate selectedDate = currentMonth.withDayOfMonth(Integer.parseInt(dayObj.toString()));

        String time = JOptionPane.showInputDialog(this, "Ange en ny tid (HH:mm):", "Lägg till tid", JOptionPane.PLAIN_MESSAGE);
        if (time != null && !time.isEmpty()) {
            // Kontrollera om tiden redan finns
            List<String> times = timeSlots.computeIfAbsent(selectedDate, d -> new ArrayList<>(availableTimes));
            if (!times.contains(time)) {
                times.add(time); // Lägg till den nya tiden
                times.sort(String::compareTo); // Sortera tiderna
                JOptionPane.showMessageDialog(this, "Ny tid tillagd: " + selectedDate + " kl " + time);
            } else {
                JOptionPane.showMessageDialog(this, "Tiden finns redan: " + selectedDate + " kl " + time, "Fel", JOptionPane.WARNING_MESSAGE);
            }
            updateTimePanel();
        }
    }
}
