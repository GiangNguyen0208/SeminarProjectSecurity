package view;

import controller.SymmetricController;
import java.awt.*;
import java.io.File;
import javax.swing.*;

public class SymmetricPanel extends JPanel {

    private JComboBox<String> algorithmOption;
    private JComboBox<String> modeOption;
    private JComboBox<String> paddingOption;
    private JComboBox<String> sizeOption;

    private JTextArea inputText;
    private JTextArea outputText;
    private JTabbedPane inputTabbedPane;
    private JTextField filePathField;

    private JButton browseButton;
    private JButton encryptBtn;
    private JButton decryptBtn;
    private JButton saveKeyBtn;
    private JButton encryptFileBtn, decryptFileBtn;

    private JTextField keyField;
    private JTextField enterKeyField;
    private JButton createKeyBtn, loadKeyBtn;

    public SymmetricPanel() {
        setLayout(new BorderLayout(10, 10));
        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        // Options
        algorithmOption = new JComboBox<>(new String[]{"AES", "DES", "3DES", "Blowfish"});
        modeOption = new JComboBox<>(new String[]{"ECB", "CBC", "CFB", "OFB"});
        paddingOption = new JComboBox<>(new String[]{"PKCS5Padding", "PKCS7Padding", "ISO10126Padding", "NoPadding"});
        sizeOption = new JComboBox<>(new String[]{"56", "128", "256", "512"});

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
        decryptBtn = new JButton("Giải mã");
        saveKeyBtn = new JButton("Lưu khóa");
        loadKeyBtn = new JButton("Load Key");
        encryptFileBtn = new JButton("Mã hóa File");
        decryptFileBtn = new JButton("Giải mã File");

        // Key components
        keyField = new JTextField(20);
        enterKeyField = new JTextField(20);
        createKeyBtn = new JButton("Tạo Khóa Mới");
    }

    private void setupLayout() {
        // Label Panel
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));

        JLabel titleLabel = new JLabel("MÃ HÓA ĐỐI XỨNG");
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
        fileSelectionPanel.add(browseButton, BorderLayout.EAST);

        JTextArea filePreview = new JTextArea();
        filePreview.setEditable(false);
        filePreview.setRows(10);

        fileInputPanel.add(fileSelectionPanel, BorderLayout.NORTH);
        fileInputPanel.add(new JScrollPane(filePreview), BorderLayout.CENTER);

        // Setup Input Tabs
        inputTabbedPane.addTab("Văn bản", textInputPanel);
        inputTabbedPane.addTab("File", fileInputPanel);

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
        buttonPanel.add(encryptFileBtn);
        buttonPanel.add(decryptFileBtn);
        buttonPanel.add(createKeyBtn);
        buttonPanel.add(saveKeyBtn);
        buttonPanel.add(loadKeyBtn);

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
        keyInputPanel.add(new JLabel("Secret Key: "));
        keyInputPanel.add(keyField);

        keyInputPanel.add(new JLabel("Load Key:"));
        keyInputPanel.add(enterKeyField);

        // Add key input panel to the main layout
        topPanel.add(keyInputPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add padding to main panel
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void setupListeners() {

        // Load Key
        loadKeyBtn.addActionListener(e -> {
            {
                try {
                    // Open a file chooser to select the file to load the private key
                    JFileChooser fileChooser = new JFileChooser();
                    int result = fileChooser.showOpenDialog(this);

                    if (result == JFileChooser.APPROVE_OPTION) {
                        String path = fileChooser.getSelectedFile().getAbsolutePath();
                        // Read the private key from the selected file
                        String keySec = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(path)));
                        enterKeyField.setText(keySec); // Set the loaded key to the loadKeyField
                        JOptionPane.showMessageDialog(this, "Secret key loaded successfully!", "Anounce", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error Anounce", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Save Key 
        saveKeyBtn.addActionListener(e -> {
            if (keyField.getText().isEmpty()) {
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
                        String enteredKey = enterKeyField.getText().trim();
                        SymmetricController controller = new SymmetricController(alg, keySize, enteredKey);
                        String keySec = controller.generateKey(alg, keySize);
                        // Save the private key to the selected file, overwriting existing content
                        java.nio.file.Files.write(java.nio.file.Paths.get(path), keySec.getBytes(),
                                java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.TRUNCATE_EXISTING);
                        JOptionPane.showMessageDialog(this, "Saved Secret key successfully!", "Anounce", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error Anounce", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Generate Secret Key.
        createKeyBtn.addActionListener(e -> {
            String alg = (String) algorithmOption.getSelectedItem();
            String keySize = (String) sizeOption.getSelectedItem();
            String enteredKey = enterKeyField.getText().trim();
            try {
                SymmetricController controller = new SymmetricController(alg, keySize, enteredKey);
                String keyText = controller.generateKey(alg, keySize);
                keyField.setText(keyText);
                JOptionPane.showMessageDialog(this, "Khóa mới đã được tạo: " + keyText);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Encrypt Text Button Event. 
        encryptBtn.addActionListener(e -> {
            String enteredKey = enterKeyField.getText().trim();

            if (enteredKey.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập khóa để mã hóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    String alg = (String) algorithmOption.getSelectedItem();
                    String mode = (String) modeOption.getSelectedItem();
                    String padding = (String) paddingOption.getSelectedItem();
                    String input = inputText.getText();

                    // Check for NoPadding
                    if ("NoPadding".equals(padding) && (input.length() % 16 != 0)) {
                        JOptionPane.showMessageDialog(this, "Input length must be a multiple of 16 bytes when using NoPadding.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    SymmetricController controller = new SymmetricController(alg, sizeOption.getSelectedItem().toString(), enteredKey);
                    String encryptedText = controller.encrypt(alg, mode, padding, input);
                    outputText.setText(encryptedText);

                    if (outputText.getText().equals(encryptedText)) {
                        JOptionPane.showMessageDialog(this, "Mã hóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }

                    // Open file
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Decrypt Text Button Event.
        decryptBtn.addActionListener(e -> {
            String enteredKey = enterKeyField.getText().trim();

            if (enteredKey.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập khóa để mã hóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    String alg = (String) algorithmOption.getSelectedItem();
                    String mode = (String) modeOption.getSelectedItem();
                    String padding = (String) paddingOption.getSelectedItem();
                    String input = inputText.getText();

                    // Debugging information
                    System.out.println("Decrypting with:");
                    System.out.println("Algorithm: " + alg);
                    System.out.println("Mode: " + mode);
                    System.out.println("Padding: " + padding);
                    System.out.println("Input: " + input);
                    System.out.println("Entered Key: " + enteredKey);

                    SymmetricController controller = new SymmetricController(alg, sizeOption.getSelectedItem().toString(), enteredKey);
                    String decryptedText = controller.decrypt(alg, mode, padding, input);
                    outputText.setText(decryptedText);

                    if (outputText.getText().equals(decryptedText)) {
                        JOptionPane.showMessageDialog(this, "Giải mã thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Encrypt File Button Event.
        encryptFileBtn.addActionListener(e -> {
            String enteredKey = enterKeyField.getText().trim();
            if (enteredKey.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập khóa để mã hóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    String alg = (String) algorithmOption.getSelectedItem();
                    String mode = (String) modeOption.getSelectedItem();
                    String padding = (String) paddingOption.getSelectedItem();

                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Chọn file để mã hóa");
                    int userSelection = fileChooser.showOpenDialog(this);

                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File inputFile = fileChooser.getSelectedFile();

                        JFileChooser outputFileChooser = new JFileChooser();
                        outputFileChooser.setDialogTitle("Chọn file để lưu kết quả mã hóa");
                        int outputSelection = outputFileChooser.showSaveDialog(this);

                        if (outputSelection == JFileChooser.APPROVE_OPTION) {
                            File outputFile = outputFileChooser.getSelectedFile();

                            if (!outputFile.exists()) {
                                outputFile.createNewFile();
                            }
                            SymmetricController controller = new SymmetricController(alg, sizeOption.getSelectedItem().toString(), enteredKey);
                            controller.encryptFile(alg, mode, padding, inputFile, outputFile); // Mã hóa file
                            JOptionPane.showMessageDialog(this, "Mã hóa thành công! Kết quả đã được lưu vào: " + outputFile.getAbsolutePath(), "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Decrypt File Button Event
        decryptFileBtn.addActionListener(e -> {
            String enteredKey = enterKeyField.getText().trim();
            if (enteredKey.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập khóa để giải mã.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    String alg = (String) algorithmOption.getSelectedItem();
                    String mode = (String) modeOption.getSelectedItem();
                    String padding = (String) paddingOption.getSelectedItem();
                    // Open file chooser to select input file for decryption
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Chọn file để giải mã");
                    int userSelection = fileChooser.showOpenDialog(this);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File inputFile = fileChooser.getSelectedFile(); // Chọn file đầu vào
                        // Open file chooser to select output file for saving decrypted result
                        JFileChooser outputFileChooser = new JFileChooser();
                        outputFileChooser.setDialogTitle("Chọn file để lưu kết quả giải mã");
                        int outputSelection = outputFileChooser.showSaveDialog(this);
                        if (outputSelection == JFileChooser.APPROVE_OPTION) {
                            File outputFile = outputFileChooser.getSelectedFile(); // Chọn file đầu ra
                            // Kiểm tra xem file đầu ra có tồn tại không
                            if (!outputFile.exists()) {
                                // Nếu không tồn tại, tạo file mới
                                outputFile.createNewFile();
                            }
                            // Giải mã file
                            SymmetricController controller = new SymmetricController(alg, sizeOption.getSelectedItem().toString(), enteredKey);
                            controller.decryptFile(alg, mode, padding, inputFile, outputFile); // Giải mã file
                            JOptionPane.showMessageDialog(this, "Giải mã thành công! Kết quả đã được lưu vào: " + outputFile.getAbsolutePath(), "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        }
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
    }
}
