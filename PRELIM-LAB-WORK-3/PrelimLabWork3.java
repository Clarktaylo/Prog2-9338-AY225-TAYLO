import java.util.Scanner;
import javax.swing.*;

public class PrelimLabWork3 {

    public static void main(String[] args) {

        // Scanner for user input
        Scanner sc = new Scanner(System.in);

        // ===== INPUTS =====
        System.out.print("Enter number of attendances (0 to 4): ");
        int attendance = sc.nextInt();

        System.out.print("Enter Lab Work 1 grade: ");
        double lab1 = sc.nextDouble();

        System.out.print("Enter Lab Work 2 grade: ");
        double lab2 = sc.nextDouble();

        System.out.print("Enter Lab Work 3 grade: ");
        double lab3 = sc.nextDouble();

        System.out.print("Enter Prelim Exam TOTAL score: ");
        double examTotal = sc.nextDouble();

        // ===== COMPUTATION =====
        double attendancePart = (attendance / 4.0) * 40;
        double labAverage = (lab1 + lab2 + lab3) / 3;
        double labPart = (labAverage / 100.0) * 60;
        double classStandingRaw = attendancePart + labPart;
        double classStandingFinal = classStandingRaw * 0.70;
        double requiredExamScore = ((75 - classStandingFinal) / 30) * examTotal;

        // ===== CONDITIONAL STATEMENT =====
        String remarks;
        if (requiredExamScore <= examTotal && requiredExamScore >= 0) {
            remarks = "Achievable to pass if you hit the required Prelim Exam Score or more!";
        } else {
            remarks = "Not achievable even with a perfect exam score.";
        }

        // ===== FORMATTED OUTPUT TEXT =====
        String resultText =
                "-------------------------------------------\n" +
                "            PRELIM RESULTS SUMMARY          \n" +
                "-------------------------------------------\n" +
                "Attendance: " + Math.round(attendancePart) + "%\n" +
                "Lab Work: " + Math.round(labPart) + "%\n" +
                "Class Standing: " + Math.round(classStandingFinal) + "%\n\n" +
                "Required Prelim Exam Score to PASS: " +
                String.format("%.2f / %.2f", requiredExamScore, examTotal) + "\n\n" +
                "REMARKS: " + remarks + "\n" +
                "-------------------------------------------";

        // ===== DISPLAY RESULTS IN WINDOW FRAME =====
        JTextArea textArea = new JTextArea(resultText);
        textArea.setEditable(false);
        textArea.setFont(new java.awt.Font("Consolas", java.awt.Font.PLAIN, 14));
        textArea.setMargin(new java.awt.Insets(10, 10, 10, 10));

        JFrame frame = new JFrame("Prelim Result Viewer");
        frame.add(new JScrollPane(textArea));
        frame.setSize(480, 350);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        sc.close();
    }
}
