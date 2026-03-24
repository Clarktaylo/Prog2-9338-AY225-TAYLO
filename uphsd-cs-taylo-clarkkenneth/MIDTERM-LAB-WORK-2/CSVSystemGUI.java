/*
=====================================================
Student Name    : TAYLO, CLARK KENNETH C.
Course          : BSCSIT 1203 Programming 2
Assignment      : Programming Assignment 2 — Machine Problem Set
School          : University of Perpetual Help System DALTA, Molino Campus
Date            : March 2026
GitHub Repo     : https://github.com/Clarktaylo/Prog2-9338-AY225-TAYLO
=====================================================
*/

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class CSVSystemGUI {

    static java.util.List<String[]> data = new ArrayList<>();
    static String[] headers;

    static JTextArea outputArea = new JTextArea();
    static JComboBox<String> columnBox = new JComboBox<>();

    // ── LOAD FILE ─────────────────────────────────────
    static void loadFile(File file) throws Exception {
        data.clear();
        outputArea.setText("");

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        // Read headers
        headers = br.readLine().split(",");

        // Update dropdown
        columnBox.removeAllItems();
        for (int i = 0; i < headers.length; i++) {
            columnBox.addItem(i + " - " + headers[i]);
        }

        // Display headers
        outputArea.append(String.join(" | ", headers) + "\n");
        outputArea.append("--------------------------------------------------\n");

        // Read and display all rows
        while ((line = br.readLine()) != null) {
            String[] row = line.split(",");
            data.add(row);
            outputArea.append(String.join(" | ", row) + "\n");
        }

        br.close();
    }

    // ── UNIQUE VALUES ─────────────────────────────────
    static void uniqueValues() {
        int col = columnBox.getSelectedIndex();
        Set<String> unique = new LinkedHashSet<>();

        for (String[] row : data) {
            if (col < row.length) {
                unique.add(row[col]);
            }
        }

        outputArea.setText("Unique Values:\n\n");
        unique.forEach(val -> outputArea.append(val + "\n"));
    }

    // ── EXPORT FIRST 50 (WITH FILE CHOOSER) ───────────
    static void export50(JFrame frame) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save CSV File");

        int result = fileChooser.showSaveDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

                // Optional: include headers
                if (headers != null) {
                    bw.write(String.join(",", headers));
                    bw.newLine();
                }

                for (int i = 0; i < Math.min(50, data.size()); i++) {
                    bw.write(String.join(",", data.get(i)));
                    bw.newLine();
                }

                outputArea.setText("File exported successfully:\n" + file.getAbsolutePath());

            } catch (Exception e) {
                outputArea.setText("Error: " + e.getMessage());
            }
        }
    }

    // ── CLEAN DATA ────────────────────────────────────
    static void cleanData() {
        outputArea.setText("Clean Data:\n\n");

        for (String[] row : data) {
            boolean valid = true;

            for (String col : row) {
                if (col.trim().isEmpty()) {
                    valid = false;
                    break;
                }
            }

            if (valid) {
                outputArea.append(String.join(" | ", row) + "\n");
            }
        }
    }

    // ── MAIN GUI ──────────────────────────────────────
    public static void main(String[] args) {

        JFrame frame = new JFrame("CSV System (Enhanced)");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();

        JButton loadBtn = new JButton("Load CSV");
        JButton uniqueBtn = new JButton("Unique Values");
        JButton exportBtn = new JButton("Export 50");
        JButton cleanBtn = new JButton("Clean Data");

        topPanel.add(loadBtn);
        topPanel.add(columnBox);
        topPanel.add(uniqueBtn);
        topPanel.add(exportBtn);
        topPanel.add(cleanBtn);

        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // ── BUTTON ACTIONS ─────────────────────────────

        // LOAD
        loadBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(frame);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    loadFile(fileChooser.getSelectedFile());
                } catch (Exception ex) {
                    outputArea.setText("Error: " + ex.getMessage());
                }
            }
        });

        // UNIQUE
        uniqueBtn.addActionListener(e -> uniqueValues());

        // EXPORT
        exportBtn.addActionListener(e -> export50(frame));

        // CLEAN
        cleanBtn.addActionListener(e -> cleanData());

        frame.setVisible(true);
    }
}