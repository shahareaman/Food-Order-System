package com.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CafeDineInn extends JFrame {

    // Declare components
    private JCheckBox pizzaCheckBox, burgerCheckBox, pastaCheckBox, coldDrinkCheckBox, shakeCheckBox;
    private JSpinner pizzaQuantitySpinner, burgerQuantitySpinner, pastaQuantitySpinner, coldDrinkQuantitySpinner, shakeQuantitySpinner;
    private JRadioButton creditCardRadioButton, upiRadioButton, cashRadioButton;
    private ButtonGroup paymentGroup;
    private JButton submitButton;
    private JTextArea resultTextArea;

    // Prices for the food items
    private static final double PIZZA_PRICE = 99.0;
    private static final double BURGER_PRICE = 59.0;
    private static final double PASTA_PRICE = 89.0;
    private static final double COLD_DRINK_PRICE = 39.0;
    private static final double SHAKE_PRICE = 69.0;

    // Conversion rate (1 USD = 80 INR)
    private static final double EXCHANGE_RATE = 80;

    public CafeDineInn() {
        // Set frame title
        setTitle("Food Order System");

        // Set layout manager
        setLayout(new BorderLayout());

        // Initialize components
        pizzaCheckBox = new JCheckBox("Pizza (₹99)");
        burgerCheckBox = new JCheckBox("Burger (₹59)");
        pastaCheckBox = new JCheckBox("Pasta (₹89)");
        coldDrinkCheckBox = new JCheckBox("Cold Drink (₹39)");
        shakeCheckBox = new JCheckBox("Shake (₹69)");

        // Spinners for selecting quantity (with minimized width)
        pizzaQuantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        burgerQuantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        pastaQuantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        coldDrinkQuantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        shakeQuantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

        // Set the preferred width for spinners to minimize them
        Dimension spinnerSize = new Dimension(50, 20);  // Width of 50 pixels, height of 20 pixels
        pizzaQuantitySpinner.setPreferredSize(spinnerSize);
        burgerQuantitySpinner.setPreferredSize(spinnerSize);
        pastaQuantitySpinner.setPreferredSize(spinnerSize);
        coldDrinkQuantitySpinner.setPreferredSize(spinnerSize);
        shakeQuantitySpinner.setPreferredSize(spinnerSize);

        creditCardRadioButton = new JRadioButton("Credit Card");
        upiRadioButton = new JRadioButton("UPI");
        cashRadioButton = new JRadioButton("Cash");

        // Group the radio buttons so only one can be selected
        paymentGroup = new ButtonGroup();
        paymentGroup.add(creditCardRadioButton);
        paymentGroup.add(upiRadioButton);
        paymentGroup.add(cashRadioButton);

        // Create submit button and result area
        submitButton = new JButton("Submit Order");
        resultTextArea = new JTextArea(15, 30);
        resultTextArea.setEditable(false);
        resultTextArea.setLineWrap(true);
        resultTextArea.setWrapStyleWord(true);

        // Header panel with welcome message, date/time, and bill number
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS)); // Stack the components vertically

        JLabel welcomeLabel = new JLabel("Welcome to CAFE ALS DINEINN", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Date and Bill Number in the next line after welcome message
        JPanel dateAndBillPanel = new JPanel();
        dateAndBillPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Date and Time label
        JLabel dateLabel = new JLabel("Date/Time: " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()), SwingConstants.LEFT);
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        // Bill Number label (aligned to the right side)
        JLabel billNumberLabel = new JLabel("Bill No: " + generateBillNumber(), SwingConstants.RIGHT);
        billNumberLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        // Add date and bill number to the panel
        dateAndBillPanel.add(dateLabel);
        dateAndBillPanel.add(billNumberLabel);

        // Add Welcome and Date & Bill Panel to the headerPanel
        headerPanel.add(welcomeLabel);
        headerPanel.add(dateAndBillPanel);

        // Food items panel
        JPanel foodPanel = new JPanel();
        foodPanel.setLayout(new GridLayout(5, 3, 10, 10)); // Increased spacing between columns
        foodPanel.add(pizzaCheckBox);
        foodPanel.add(pizzaQuantitySpinner);
        foodPanel.add(burgerCheckBox);
        foodPanel.add(burgerQuantitySpinner);
        foodPanel.add(pastaCheckBox);
        foodPanel.add(pastaQuantitySpinner);
        foodPanel.add(coldDrinkCheckBox);
        foodPanel.add(coldDrinkQuantitySpinner);
        foodPanel.add(shakeCheckBox);
        foodPanel.add(shakeQuantitySpinner);

        // Payment method panel
        JPanel paymentPanel = new JPanel();
        paymentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        paymentPanel.add(creditCardRadioButton);
        paymentPanel.add(upiRadioButton);
        paymentPanel.add(cashRadioButton);

        // Arrange the order of components in the frame
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(foodPanel);  // Add food items checkboxes and quantity spinners
        mainPanel.add(paymentPanel);  // Add payment method radio buttons
        mainPanel.add(submitButton);  // Add submit button

        // Add the main panel, header panel, and result text area to the frame
        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(new JScrollPane(resultTextArea), BorderLayout.SOUTH);

        // Add button click listener
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle order submission
                handleOrderSubmission();
            }
        });

        // Set default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the frame size and make it visible
        setSize(600, 600);
        setVisible(true);
    }

    private void handleOrderSubmission() {
        double totalAmount = 0;
        StringBuilder orderDetails = new StringBuilder();
        orderDetails.append("ALS DINEINN\n"); // Updated restaurant name
        orderDetails.append("Bill No: " + generateBillNumber() + "\n");
        orderDetails.append("Date & Time: " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()) + "\n");
        orderDetails.append("---------------------------------------------------------\n");

        // Column Headers with fixed width
        orderDetails.append(String.format("%-5s%-20s%-15s%-10s%-15s\n", "Sr.No.", "Item Name", "Price (₹)", "Quantity", "Total (₹)"));
        orderDetails.append("---------------------------------------------------------\n");

        int srNo = 1;

        // Check for selected food items and calculate total amount
        if (pizzaCheckBox.isSelected()) {
            int pizzaQuantity = (int) pizzaQuantitySpinner.getValue();
            double pizzaTotal = PIZZA_PRICE * pizzaQuantity;
            orderDetails.append(String.format("%-5d%-20s%-15.2f%-10d%-15.2f\n", srNo++, "Pizza", PIZZA_PRICE, pizzaQuantity, pizzaTotal));
            totalAmount += pizzaTotal;
        }
        if (burgerCheckBox.isSelected()) {
            int burgerQuantity = (int) burgerQuantitySpinner.getValue();
            double burgerTotal = BURGER_PRICE * burgerQuantity;
            orderDetails.append(String.format("%-5d%-20s%-15.2f%-10d%-15.2f\n", srNo++, "Burger", BURGER_PRICE, burgerQuantity, burgerTotal));
            totalAmount += burgerTotal;
        }
        if (pastaCheckBox.isSelected()) {
            int pastaQuantity = (int) pastaQuantitySpinner.getValue();
            double pastaTotal = PASTA_PRICE * pastaQuantity;
            orderDetails.append(String.format("%-5d%-20s%-15.2f%-10d%-15.2f\n", srNo++, "Pasta", PASTA_PRICE, pastaQuantity, pastaTotal));
            totalAmount += pastaTotal;
        }
        if (coldDrinkCheckBox.isSelected()) {
            int coldDrinkQuantity = (int) coldDrinkQuantitySpinner.getValue();
            double coldDrinkTotal = COLD_DRINK_PRICE * coldDrinkQuantity;
            orderDetails.append(String.format("%-5d%-20s%-15.2f%-10d%-15.2f\n", srNo++, "Cold Drink", COLD_DRINK_PRICE, coldDrinkQuantity, coldDrinkTotal));
            totalAmount += coldDrinkTotal;
        }
        if (shakeCheckBox.isSelected()) {
            int shakeQuantity = (int) shakeQuantitySpinner.getValue();
            double shakeTotal = SHAKE_PRICE * shakeQuantity;
            orderDetails.append(String.format("%-5d%-20s%-15.2f%-10d%-15.2f\n", srNo++, "Shake", SHAKE_PRICE, shakeQuantity, shakeTotal));
            totalAmount += shakeTotal;
        }

        // Final total amount
        orderDetails.append("---------------------------------------------------------\n");
        orderDetails.append(String.format("%-5s%-20s%-15s%-10s₹%-15.2f\n", "", "Total", "", "", totalAmount));

        // Payment method section
        String paymentMethod = "";
        if (creditCardRadioButton.isSelected()) {
            paymentMethod = "Credit Card";
        } else if (upiRadioButton.isSelected()) {
            paymentMethod = "UPI";
        } else if (cashRadioButton.isSelected()) {
            paymentMethod = "Cash";
        }
        orderDetails.append("Payment Method: " + paymentMethod + "\n");

        // Thank you message
        orderDetails.append("Thank you! Visit Again.\n");

        // Display the order summary in the text area
        resultTextArea.setText(orderDetails.toString());
    }

    private String generateBillNumber() {
        Random rand = new Random();
        return "BILL" + String.format("%04d", rand.nextInt(10000));  // Generates a bill number in the format BILLxxxx
    }

    public static void main(String[] args) {
        new CafeDineInn();
    }
}
