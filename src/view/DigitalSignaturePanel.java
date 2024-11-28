package view;

import controller.DigitalSignatureController;
import java.awt.*;
import javax.swing.*;

public class DigitalSignaturePanel extends JPanel {

    private JComboBox<String> algorithmOption, sizeOption;
    private JTextArea inputText, inputVerifyText;
    private JTextArea outputText, outputVerifyText;
    private JTabbedPane inputTabbedPane, verifyTabbedPane;
    private JTextField filePathField, fileVerifyPathField;
    private JButton browseButton, browseVerifyButton;
    private JButton signBtn, signFileBtn;
    private JButton verifyBtn, verifyFileBtn, loadKeyBtn, saveKeyBtn;

    private JTextField keyPulicField, loadKeyField;
    private JButton createKeyPair;

    public DigitalSignaturePanel() {
        setLayout(new BorderLayout(10, 10));
        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        // SHA512withRSA
        algorithmOption = new JComboBox<>(new String[]{"SHA256withRSA", "SHA512withRSA", "SHA256withECDSA", "SHA512withECDSA", "DSA"});
        sizeOption = new JComboBox<>(new String[]{"521", "1024", "2048", "4096"});

        // Input/Output
        inputText = new JTextArea();
        outputText = new JTextArea();
        outputVerifyText = new JTextArea();
        inputVerifyText = new JTextArea();
        outputText.setEditable(false);
        outputVerifyText.setEditable(false);
        inputTabbedPane = new JTabbedPane();
        verifyTabbedPane = new JTabbedPane();

        // File components
        filePathField = new JTextField();
        filePathField.setEditable(false);
        fileVerifyPathField = new JTextField();
        fileVerifyPathField.setEditable(false);
        browseButton = new JButton("File");
        browseVerifyButton = new JButton("File Verify");

        // Buttons
        signBtn = new JButton("Sign Text");
        verifyBtn = new JButton("Verify Text");
        signFileBtn = new JButton("Sign File");
        verifyFileBtn = new JButton("Verify File");
        loadKeyBtn = new JButton("Load Key");
        saveKeyBtn = new JButton("Save Key");

        // Key components
        keyPulicField = new JTextField(20);
        loadKeyField = new JTextField(20);
        createKeyPair = new JButton("Gen Key Pair");
    }

    private void setupLayout() {
        // Label Panel
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));

        JLabel titleLabel = new JLabel("MÃ HÓA CHỮ KÝ ĐIỆN TỬ");
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
        optionsPanel.add(new JLabel("Key Size (bytes): "));
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
        fileSelectionPanel.add(browseButton, BorderLayout.EAST);

        JTextArea filePreview = new JTextArea();
        filePreview.setEditable(false);
        filePreview.setRows(10);

        fileInputPanel.add(fileSelectionPanel, BorderLayout.NORTH);
        fileInputPanel.add(new JScrollPane(filePreview), BorderLayout.CENTER);

        // Setup Input Tabs
        inputTabbedPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Input"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        inputTabbedPane.addTab("Văn bản", textInputPanel);
        inputTabbedPane.addTab("File", fileInputPanel);

        // Verify Tab Pane
        verifyTabbedPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Verify"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        // Text Verify Panel
        JPanel textVerifyPanel = new JPanel(new BorderLayout());
        textVerifyPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        textVerifyPanel.add(new JScrollPane(inputVerifyText), BorderLayout.CENTER);

        // File Input Panel
        JPanel fileVerifyPanel = new JPanel(new BorderLayout(5, 5));
        fileVerifyPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JPanel fileVerifySelectionPanel = new JPanel(new BorderLayout(5, 0));

        fileVerifySelectionPanel.add(new JLabel("File Path: "), BorderLayout.WEST);
        fileVerifySelectionPanel.add(fileVerifyPathField, BorderLayout.CENTER);
        fileVerifySelectionPanel.add(browseVerifyButton, BorderLayout.EAST);

        JTextArea fileVerifyPreview = new JTextArea();
        fileVerifyPreview.setEditable(false);
        fileVerifyPreview.setRows(10);

        fileVerifyPanel.add(fileVerifySelectionPanel, BorderLayout.NORTH);
        fileVerifyPanel.add(new JScrollPane(fileVerifyPreview), BorderLayout.CENTER);

        // Setup Verify Tabs
        verifyTabbedPane.addTab("Text", textVerifyPanel);
        verifyTabbedPane.addTab("File", fileVerifyPanel);

        // Output Panel
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Kết quả"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        outputPanel.add(new JScrollPane(outputText), BorderLayout.CENTER);

        // Output Verify Panel
        JPanel outputVerifyPanel = new JPanel(new BorderLayout());
        outputVerifyPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Kết quả Verify"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        outputVerifyPanel.add(new JScrollPane(outputVerifyText), BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        buttonPanel.add(signBtn);
        buttonPanel.add(verifyBtn);
        buttonPanel.add(createKeyPair);
        buttonPanel.add(saveKeyBtn);
        buttonPanel.add(loadKeyBtn);

        // Main Layout
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.add(inputTabbedPane);
        centerPanel.add(outputPanel);
        centerPanel.add(verifyTabbedPane);
        centerPanel.add(outputVerifyPanel);

        // Add to main panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(labelPanel, BorderLayout.NORTH);
        topPanel.add(optionsPanel, BorderLayout.CENTER);

        // Key Input Panel
        JPanel keyInputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        keyInputPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        keyInputPanel.add(new JLabel("Public Key: "));
        keyInputPanel.add(keyPulicField);
        keyInputPanel.add(new JLabel("Load Private Key: "));
        keyInputPanel.add(loadKeyField);

        topPanel.add(keyInputPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add padding to main panel
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void setupListeners() {

        loadKeyBtn.addActionListener(e -> {
            try {
                // Open a file chooser to select the file to load the private key
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(this);

                if (result == JFileChooser.APPROVE_OPTION) {
                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    // Read the private key from the selected file
                    String privateKey = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(path)));
                    loadKeyField.setText(privateKey); // Set the loaded key to the loadKeyField
                    JOptionPane.showMessageDialog(this, "Private key loaded successfully!", "Anounce", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error Anounce", JOptionPane.ERROR_MESSAGE);
            }
        });

        saveKeyBtn.addActionListener(e -> {
            if (keyPulicField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Public Key null!", "Anounce", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    // Open a file chooser to select the file to save the private key
                    JFileChooser fileChooser = new JFileChooser();
                    int result = fileChooser.showSaveDialog(this);

                    if (result == JFileChooser.APPROVE_OPTION) {
                        String path = fileChooser.getSelectedFile().getAbsolutePath();
                        String alg = algorithmOption.getSelectedItem().toString();
                        String keySize = sizeOption.getSelectedItem().toString();
                        DigitalSignatureController controller = new DigitalSignatureController(alg, keySize);
                        String privateKey = controller.getPrivateKey();
                        // Save the private key to the selected file, overwriting existing content
                        java.nio.file.Files.write(java.nio.file.Paths.get(path), privateKey.getBytes(),
                                java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.TRUNCATE_EXISTING);
                        JOptionPane.showMessageDialog(this, "Private key saved successfully!", "Anounce", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error Anounce", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        createKeyPair.addActionListener(e -> {
            try {
                String alg = algorithmOption.getSelectedItem().toString();
                String keySize = sizeOption.getSelectedItem().toString();
                DigitalSignatureController controller = new DigitalSignatureController(alg, keySize);
                keyPulicField.setText(controller.getPublicKey());
                if (keyPulicField.getText().equals(controller.getPublicKey())) {
                    JOptionPane.showMessageDialog(this, "Create Pair successfully.!", "Anounce", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Create Pair Fail.!", "Anounce", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Eror: " + ex.getMessage(), " Error Anounce", JOptionPane.ERROR_MESSAGE);
            }
        });

        signBtn.addActionListener(e -> {
            if (keyPulicField.getText().isEmpty() || loadKeyField.getText().isEmpty() || inputText.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Chưa nhập đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    String alg = algorithmOption.getSelectedItem().toString();
                    String keySize = sizeOption.getSelectedItem().toString();
                    DigitalSignatureController controller = new DigitalSignatureController(alg, keySize);
                    String input = inputText.getText();
                    String encryptedText = controller.signText(alg, input);
                    outputText.setText(encryptedText);
                    if (outputText.getText().equals(encryptedText)) {
                        JOptionPane.showMessageDialog(this, "Mã hóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Private Key không đúng", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        verifyBtn.addActionListener(e -> {
            if (keyPulicField.getText().isEmpty() || loadKeyField.getText().isEmpty() || inputText.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Chưa nhập đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    String alg = algorithmOption.getSelectedItem().toString();
                    String keySize = sizeOption.getSelectedItem().toString();
                    DigitalSignatureController controller = new DigitalSignatureController(alg, keySize);
                    String input = inputText.getText();
                    String verifyText = inputVerifyText.getText();
                    boolean isVerified = controller.verifySigned(alg, verifyText, input);
                    String anounceSuccess = "TEXT VALID.!";
                    String anounceFail = "TEXT INVALID.!";
                    if (isVerified == true) {
                        outputVerifyText.setText(anounceSuccess);
                        JOptionPane.showMessageDialog(this, "Ký thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        outputVerifyText.setText(anounceFail);
                        JOptionPane.showMessageDialog(this, "Ký thất bại, Sai chữ ký!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                filePathField.setText(path);

                // Đọc và hiển thị preview của file
                try {
                    java.nio.file.Path filePath = java.nio.file.Paths.get(path);
                    String content = new String(java.nio.file.Files.readAllBytes(filePath));
                    JTextArea filePreview = (JTextArea) ((JScrollPane) ((JPanel) inputTabbedPane
                            .getComponentAt(1)).getComponent(1)).getViewport().getView();

                    // Giới hạn preview để tránh lag với file lớn
                    if (content.length() > 1000) {
                        content = content.substring(0, 1000) + "...\n(File quá lớn, chỉ hiển thị 1000 ký tự đầu)";
                    }
                    filePreview.setText(content);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Không thể đọc file: " + ex.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        browseVerifyButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                fileVerifyPathField.setText(path);

                // Đọc và hiển thị preview của file
                try {
                    java.nio.file.Path filePath = java.nio.file.Paths.get(path);
                    String content = new String(java.nio.file.Files.readAllBytes(filePath));
                    JTextArea filePreview = (JTextArea) ((JScrollPane) ((JPanel) verifyTabbedPane
                            .getComponentAt(1)).getComponent(1)).getViewport().getView();

                    // Giới hạn preview để tránh lag với file lớn
                    if (content.length() > 1000) {
                        content = content.substring(0, 1000) + "...\n(File quá lớn, chỉ hiển thị 1000 ký tự đầu)";
                    }
                    filePreview.setText(content);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Không thể đọc file: " + ex.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
