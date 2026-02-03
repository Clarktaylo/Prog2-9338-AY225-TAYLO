import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class PrelimLabWork1 {

    public static void main(String[] args) {

        // ===== JFrame =====
        JFrame frame = new JFrame("Attendance Tracker");
        frame.setSize(520, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // ===== MAIN PANEL =====
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ===== FORM PANEL =====
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        JLabel nameLabel = new JLabel("Full Name (LAST NAME, FIRST NAME):");
        JLabel courseLabel = new JLabel("Course / Year (COURSE - YEAR):");
        JLabel timeLabel = new JLabel("Time In:");
        JLabel signatureLabel = new JLabel("E-Signature:");

        JTextField nameField = new JTextField();
        JTextField courseField = new JTextField();
        JTextField timeField = new JTextField();
        JTextField signatureField = new JTextField();

        timeField.setEditable(false);
        signatureField.setEditable(false);

        JButton timeInButton = new JButton("ENTER / TIME IN");

        // ===== REAL-TIME CAPSLOCK =====
        KeyAdapter capsAdapter = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                JTextField field = (JTextField) e.getSource();
                field.setText(field.getText().toUpperCase());
            }
        };

        nameField.addKeyListener(capsAdapter);
        courseField.addKeyListener(capsAdapter);

        formPanel.add(nameLabel);
        formPanel.add(nameField);

        formPanel.add(courseLabel);
        formPanel.add(courseField);

        formPanel.add(timeLabel);
        formPanel.add(timeField);

        formPanel.add(signatureLabel);
        formPanel.add(signatureField);

        formPanel.add(new JLabel());
        formPanel.add(timeInButton);

        // ===== STATUS AREA =====
        JTextArea summaryArea = new JTextArea();
        summaryArea.setEditable(false);
        summaryArea.setLineWrap(true);
        summaryArea.setWrapStyleWord(true);
        summaryArea.setFont(new Font("Arial", Font.PLAIN, 12));
        summaryArea.setBorder(BorderFactory.createTitledBorder("Status"));

        // ===== BUTTON ACTION =====
        timeInButton.addActionListener(e -> {

            String name = nameField.getText().trim();
            String course = courseField.getText().trim();

            // ----- NAME VALIDATION -----
            if (!name.matches("[A-Z ,]+")) {
                JOptionPane.showMessageDialog(frame,
                        "Error! Follow correct name format:\n" +
                        "TAYLO, CLARK KENNETH",
                        "Invalid Name",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (name.chars().filter(ch -> ch == ',').count() != 1) {
                JOptionPane.showMessageDialog(frame,
                        "Error! Follow correct name format:\n" +
                        "TAYLO, CLARK KENNETH",
                        "Invalid Name",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] parts = name.split(",");
            String lastName = parts[0].trim();
            String firstPart = parts[1].trim();

            if (lastName.length() < 2 || !lastName.matches("[A-Z ]+")) {
                JOptionPane.showMessageDialog(frame,
                        "Error! Follow correct name format:\n" +
                        "TAYLO, CLARK KENNETH",
                        "Invalid Name",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] firstWords = firstPart.split("\\s+");
            for (String word : firstWords) {
                if (word.length() < 2) {
                    JOptionPane.showMessageDialog(frame,
                            "Error! Follow correct name format:\n" +
                            "TAYLO, CLARK KENNETH",
                            "Invalid Name",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // ----- COURSE / YEAR VALIDATION -----
            String coursePattern =
                    "^[A-Z]{4,6} - (1ST|2ND|3RD|4TH|5TH) YEAR$";

            if (!course.matches(coursePattern)) {
                JOptionPane.showMessageDialog(frame,
                        "Error! Course/Year must follow format:\n" +
                                "BSIT - 3RD YEAR",
                        "Invalid Course / Year",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // ----- FRIENDLY TIME FORMAT -----
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("MMMM d, yyyy | h:mm a");

            String timeIn =
                    LocalDateTime.now().format(formatter).toUpperCase();

            String eSignature = UUID.randomUUID().toString();

            timeField.setText(timeIn);
            signatureField.setText(eSignature);

            // ----- SUCCESS MESSAGE WITH CHECK ICON -----
            summaryArea.setText(
                    "SUCCESSFULLY TIMED IN!\n\n" +
                    "NAME: " + name + "\n" +
                    "COURSE / YEAR: " + course + "\n" +
                    "TIME IN: " + timeIn + "\n" +
                    "E-SIGNATURE: " + eSignature
            );

            nameField.setText("");
            courseField.setText("");
        });

        // ===== ADD TO FRAME =====
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(summaryArea, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}