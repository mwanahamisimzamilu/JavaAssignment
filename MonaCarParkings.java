/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monacarparkings;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MonaCarParkings extends JFrame {

    private JLabel title, carPlateLabel, arrivalTimeLabel, departureTimeLabel, classLabel, chargeLabel, snackLabel, drinkLabel, washLabel;
    private JTextField carPlateField, arrivalTimeField, departureTimeField;
    private JComboBox<String> classComboBox;
    private JCheckBox snackCheckBox, drinkCheckBox, washCheckBox;
    private JButton calculateButton, exitButton;
    private JTextArea resultArea;

    public MonaCarParkings() {
        // Set up the frame
        setTitle("Mona Car Parking");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        // Create the title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        title = new JLabel("Mona Car Parking");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(title);
        add(titlePanel, BorderLayout.NORTH);

        // Create the input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 2));

        carPlateLabel = new JLabel("Car Plate Number:");
        carPlateField = new JTextField();
        inputPanel.add(carPlateLabel);
        inputPanel.add(carPlateField);

        arrivalTimeLabel = new JLabel("Arrival Time (HH:MM):");
        arrivalTimeField = new JTextField();
        inputPanel.add(arrivalTimeLabel);
        inputPanel.add(arrivalTimeField);

        departureTimeLabel = new JLabel("Departure Time (HH:MM):");
        departureTimeField = new JTextField();
        inputPanel.add(departureTimeLabel);
        inputPanel.add(departureTimeField);

        classLabel = new JLabel("Class:");
        String[] classes = {"Executive", "V.I.P", "Regular"};
        classComboBox = new JComboBox<>(classes);
        inputPanel.add(classLabel);
        inputPanel.add(classComboBox);

        snackLabel = new JLabel("Snack:");
        snackCheckBox = new JCheckBox();
        inputPanel.add(snackLabel);
        inputPanel.add(snackCheckBox);

        drinkLabel = new JLabel("Drink:");
        drinkCheckBox = new JCheckBox();
        inputPanel.add(drinkLabel);
        inputPanel.add(drinkCheckBox);

        washLabel = new JLabel("Car Wash:");
        washCheckBox = new JCheckBox();
        inputPanel.add(washLabel);
        inputPanel.add(washCheckBox);

        add(inputPanel, BorderLayout.CENTER);

        // Create the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        calculateButton = new JButton("Calculate Charge");
        calculateButton.addActionListener(new CalculateButtonListener());
        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ExitButtonListener());
        buttonPanel.add(calculateButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Create the result area
        resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.EAST);

        // Center the frame
        setLocationRelativeTo(null);
    }

    private class CalculateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get the input values
            String carPlateNumber = carPlateField.getText();
            String arrivalTime = arrivalTimeField.getText();
            String departureTime = departureTimeField.getText();
            String className = (String) classComboBox.getSelectedItem();
            boolean snack = snackCheckBox.isSelected();
            boolean drink = drinkCheckBox.isSelected();
            boolean wash = washCheckBox.isSelected();

            // Calculate the charge
            int baseCharge = 0;
            switch (className) {
                case "Executive":
                    baseCharge = 10000;
                    break;
                case "V.I.P":
                    baseCharge = 5000;
                    break;
                case "Regular":
                    baseCharge = 2000;
                    break;
            }

            // Calculate the additional time charge
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            Date arrivalDate = null;
            Date departureDate = null;
            try {
                arrivalDate = format.parse(arrivalTime);
                departureDate = format.parse(departureTime);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid time format");
                return;
            }
            long timeDiff = departureDate.getTime() - arrivalDate.getTime();
            int minutes = (int) (timeDiff / (1000 * 60));
            int additionalCharge = 0;
            if (minutes < 15) {
                additionalCharge = (int) (baseCharge * 0.25);
            } else if (minutes < 30) {
                additionalCharge = (int) (baseCharge * 0.5);
            } else if (minutes < 45) {
                additionalCharge = (int) (baseCharge * 0.75);
            }

            // Calculate the total charge
            int totalCharge = baseCharge + additionalCharge;

            // Add the snack and drink charges
            if (snack) {
                totalCharge += 500;
            }
            if (drink) {
                totalCharge += 500;
            }

            // Add the car wash charge
            if (wash) {
                switch (className) {
                    case "Executive":
                        break;
                    case "V.I.P":
                        totalCharge += 2500;
                        break;
                    case "Regular":
                        totalCharge += 5000;
                        break;
                }
            }

            // Display the result
            resultArea.setText("Car Plate Number: " + carPlateNumber + "\n");
            resultArea.append("Arrival Time: " + arrivalTime + "\n");
            resultArea.append("Departure Time: " + departureTime + "\n");
            resultArea.append("Class: " + className + "\n");
            resultArea.append("Base Charge: " + baseCharge + "\n");
            resultArea.append("Additional Time Charge: " + additionalCharge + "\n");
            resultArea.append("Total Charge: " + totalCharge + "\n");
        }
    }

    private class ExitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MonaCarParkings frame = new MonaCarParkings();
                frame.setVisible(true);
            }
        });
    }
}
