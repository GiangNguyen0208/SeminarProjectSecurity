package view;

import controller.BasicController;
import java.awt.*;
import javax.swing.*;

public class BasicCipherPanel extends JPanel {

    private JComboBox<String> algorithmOption;
    private JTextArea inputText;
    private JTextArea outputText;
    private JTabbedPane inputTabbedPane;
    private JTextField filePathField;
    private JButton browseButton;
    private JButton encryptBtn;
    private JButton decryptBtn;

    private BasicController controller;

    public BasicCipherPanel() {
        controller = new BasicController();
        setLayout(new BorderLayout(10, 10));
        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        // Options
        algorithmOption = new JComboBox<>(new String[]{"Caesar", "Substitution"});

        // Input/Output
        inputText = new JTextArea();
        outputText = new JTextArea();
        outputText.setEditable(false);
        inputTabbedPane = new JTabbedPane();

        // File components
        filePathField = new JTextField();
        filePathField.setEditable(false);
        browseButton = new JButton("Chọn File");

        // Buttons
        encryptBtn = new JButton("Mã hóa");
        encryptBtn.addActionListener(algorithmOption);
        decryptBtn = new JButton("Giải mã");
    }

    private void setupLayout() {
        // Label Panel
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));

        JLabel titleLabel = new JLabel("MÃ HÓA CƠ BẢN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 102, 153)); // Màu xanh dương đậm

        JLabel descLabel = new JLabel("Mã hóa và giải mã sử dụng các thuật toán");
        descLabel.setFont(new Font("Arial", Font.ITALIC, 12));

        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 5, 5));
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel);
        titlePanel.add(descLabel);

        labelPanel.add(titlePanel);

        // Options Panel
        JPanel optionsPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        optionsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Tùy chọn"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        optionsPanel.add(new JLabel("Thuật toán: "));
        optionsPanel.add(algorithmOption);

        // Text Input Panel
        JPanel textInputPanel = new JPanel(new BorderLayout());
        textInputPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        textInputPanel.add(new JScrollPane(inputText), BorderLayout.CENTER);

        JPanel fileSelectionPanel = new JPanel(new BorderLayout(5, 0));
        fileSelectionPanel.add(new JLabel("File Path: "), BorderLayout.WEST);
        fileSelectionPanel.add(filePathField, BorderLayout.CENTER);
        fileSelectionPanel.add(browseButton, BorderLayout.EAST);

        JTextArea filePreview = new JTextArea();
        filePreview.setEditable(false);
        filePreview.setRows(10);

        // Setup Input Tabs
        inputTabbedPane.addTab("Văn bản", textInputPanel);

        // Output Panel
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Kết quả"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        outputPanel.add(new JScrollPane(outputText), BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        buttonPanel.add(encryptBtn);
        buttonPanel.add(decryptBtn);

        // Main Layout
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.add(inputTabbedPane);
        centerPanel.add(outputPanel);

        // Add to main panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(labelPanel, BorderLayout.NORTH);
        topPanel.add(optionsPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add padding to main panel
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void setupListeners() {
        encryptBtn.addActionListener(e -> {
            String text = inputText.getText();
            String algorithm = (String) algorithmOption.getSelectedItem();
            String result = "";

            if ("Caesar".equals(algorithm)) {
                result = controller.encryptCaesar(text);
            } else if ("Substitution".equals(algorithm)) {
                result = controller.encryptSubstitution(text);
            }

            outputText.setText(result);
        });

        decryptBtn.addActionListener(e -> {
            String text = outputText.getText();
            String algorithm = (String) algorithmOption.getSelectedItem();
            String result = "";

            if ("Caesar".equals(algorithm)) {
                result = controller.decryptCaesar(text);
            } else if ("Substitution".equals(algorithm)) {
                result = controller.decryptSubstitution(text);
            }

            outputText.setText(result);
        });
    }
}
