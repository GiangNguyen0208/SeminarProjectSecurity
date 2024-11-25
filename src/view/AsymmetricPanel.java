package view;

import controller.AsymmetricController;
import java.awt.*;
import javax.swing.*;

public class AsymmetricPanel extends JPanel {

    private JComboBox<String> algorithmOption;
    private JComboBox<String> modeOption;
    private JComboBox<String> paddingOption;
    private JComboBox<String> sizeOption;

    private JTextArea inputText;
    private JTextArea outputText;
    private JTabbedPane inputTabbedPane;
    private JTextField filePathField;

    private JButton encryptBtn;
    private JButton decryptBtn;
    private JButton saveKeyBtn;

    private JTextField keyPulicField, keyPrivateField;
    private JButton createKeyPair;

    public AsymmetricPanel() {
        setLayout(new BorderLayout(10, 10));
        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        // Options
        algorithmOption = new JComboBox<>(new String[]{"RSA", "DSA", "EC", "DH"});
        modeOption = new JComboBox<>(new String[]{"ECB", "CBC"});
        paddingOption = new JComboBox<>(new String[]{"PKCS1Padding", "OAEPWithSHA256", "OAEPWithSHA-1AndMGF1Padding", "NoPadding"});
        sizeOption = new JComboBox<>(new String[]{"112", "256", "512", "1024", "2048", "4096"});

        // Input/Output
        inputText = new JTextArea();
        outputText = new JTextArea();
        outputText.setEditable(false);
        inputTabbedPane = new JTabbedPane();

        // File components
        filePathField = new JTextField();
        filePathField.setEditable(false);

        // Buttons
        encryptBtn = new JButton("Mã hóa");
        decryptBtn = new JButton("Giải mã");
        saveKeyBtn = new JButton("Lưu khóa");

        // Key components
        keyPulicField = new JTextField(20);
        keyPrivateField = new JTextField(20);
        createKeyPair = new JButton("Tạo Cặp Khóa mới.");
    }

    private void setupLayout() {
        // Label Panel
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));

        JLabel titleLabel = new JLabel("MÃ HÓA BẤT ĐỐI XỨNG");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 102, 153)); // Màu xanh dương đậm

        JLabel descLabel = new JLabel("\"Mã hóa và giải mã sử dụng các thuật toán\"");
        descLabel.setFont(new Font("Arial", Font.ITALIC, 12));

        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 5, 5));
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel);
        titlePanel.add(descLabel);

        labelPanel.add(titlePanel);

        // Options Panel
        JPanel optionsPanel = new JPanel(new GridLayout(4, 2, 5, 5)); // Changed from 3 to 4 rows
        optionsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Tùy chọn"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        optionsPanel.add(new JLabel("Thuật toán: "));
        optionsPanel.add(algorithmOption);
        optionsPanel.add(new JLabel("Mode: "));
        optionsPanel.add(modeOption);
        optionsPanel.add(new JLabel("Padding: "));
        optionsPanel.add(paddingOption);
        optionsPanel.add(new JLabel("Size Key (Bytes): "));
        optionsPanel.add(sizeOption);

        // Text Input Panel
        JPanel textInputPanel = new JPanel(new BorderLayout());
        textInputPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        textInputPanel.add(new JScrollPane(inputText), BorderLayout.CENTER);

        // File Input Panel
        JPanel fileInputPanel = new JPanel(new BorderLayout(5, 5));
        fileInputPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel fileSelectionPanel = new JPanel(new BorderLayout(5, 0));
        fileSelectionPanel.add(new JLabel("File Path: "), BorderLayout.WEST);
        fileSelectionPanel.add(filePathField, BorderLayout.CENTER);

        JTextArea filePreview = new JTextArea();
        filePreview.setEditable(false);
        filePreview.setRows(10);

        fileInputPanel.add(fileSelectionPanel, BorderLayout.NORTH);
        fileInputPanel.add(new JScrollPane(filePreview), BorderLayout.CENTER);

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
        buttonPanel.add(saveKeyBtn);

        // Main Layout
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.add(inputTabbedPane);
        centerPanel.add(outputPanel);

        // Add to main panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(labelPanel, BorderLayout.NORTH);
        topPanel.add(optionsPanel, BorderLayout.CENTER);

        // Key Input Panel
        JPanel keyInputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        keyInputPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        keyInputPanel.add(new JLabel("Public Key: "));
        keyInputPanel.add(keyPulicField);
        keyInputPanel.add(new JLabel("Private Key: "));
        keyInputPanel.add(keyPrivateField);
        keyInputPanel.add(createKeyPair);

        topPanel.add(keyInputPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add padding to main panel
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void setupListeners() {

        // Generate Public/Private Key Button Event.
        createKeyPair.addActionListener(e -> {
            try {
                String alg = algorithmOption.getSelectedItem().toString();
                String size = sizeOption.getSelectedItem().toString();

                AsymmetricController controller = new AsymmetricController(alg, size);
                String publicKey = controller.getPublicKey();
                String privateKey = controller.getPrivateKey();

                keyPulicField.setText(publicKey);
                keyPrivateField.setText(privateKey);
                JOptionPane.showMessageDialog(this, "Tạo cặp khóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Encrypt Button Event.
        encryptBtn.addActionListener(e -> {
            try {
                String alg = algorithmOption.getSelectedItem().toString();
                String size = sizeOption.getSelectedItem().toString();
                String mode = modeOption.getSelectedItem().toString();
                String padding = paddingOption.getSelectedItem().toString();
                String input = inputText.getText();

                AsymmetricController controller = new AsymmetricController(alg, size);
                String encryptedText = controller.encrypt(alg, mode, padding, input);
                outputText.setText(encryptedText);

                JOptionPane.showMessageDialog(this, "Mã hóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Decrypt Button Event.
        decryptBtn.addActionListener(e -> {
            try {
                String alg = algorithmOption.getSelectedItem().toString();
                String size = sizeOption.getSelectedItem().toString();
                String mode = modeOption.getSelectedItem().toString();
                String padding = paddingOption.getSelectedItem().toString();
                String input = inputText.getText();

                AsymmetricController controller = new AsymmetricController(alg, size);
                String decryptedText = controller.decrypt(alg, mode, padding, input);
                outputText.setText(decryptedText);

                JOptionPane.showMessageDialog(this, "Giải mã thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
