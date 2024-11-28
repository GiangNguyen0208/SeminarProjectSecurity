package view;

import controller.HashController;
import java.awt.*;
import javax.swing.*;

public class HashPanel extends JPanel {

    private JComboBox<String> algorithmOption;

    private JTextArea inputText, inputVerifyText;
    private JTextArea outputText, outputVerifyText;
    private JTabbedPane inputTabbedPane;
    private JTabbedPane verifyTabbedPane;
    private JTextField filePathField;
    private JTextField fileVerifyPathField;

    private JButton browseButton, browseVerifyButton;
    private JButton encryptBtn, verifyBtn;
    private JButton encryptFileBtn, verifyFileBtn;

    public HashPanel() {
        setLayout(new BorderLayout(10, 10));
        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        // Options
        algorithmOption = new JComboBox<>(new String[]{"MD5", "SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512", "SHA3-256", "SHA3-512"});

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
        browseButton = new JButton("Chọn File");
        browseVerifyButton = new JButton("Chọn File Verify");

        // Buttons
        encryptBtn = new JButton("Mã hóa");
        encryptFileBtn = new JButton("Mã hóa file");
        verifyBtn = new JButton("Verify Text");
        verifyFileBtn = new JButton("Verify File");

    }

    private void setupLayout() {
        // Label Panel
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));

        JLabel titleLabel = new JLabel("MÃ HÓA HASH");
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
        verifyTabbedPane.addTab("Văn bản", textVerifyPanel);
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
        buttonPanel.add(encryptBtn);
        buttonPanel.add(encryptFileBtn);
        buttonPanel.add(verifyBtn);
        buttonPanel.add(verifyFileBtn);

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

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add padding to main panel
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void setupListeners() {

        // Encrypt Text Button Event.
        encryptBtn.addActionListener(e -> {
            String input = inputText.getText();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập nội dung để mã hóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    String alg = algorithmOption.getSelectedItem().toString();
                    HashController controller = new HashController(alg);
                    input = inputText.getText();
                    String encryptText = controller.hashPlainText(input);
                    outputText.setText(encryptText);

                    if (outputText.getText().equals(encryptText)) {
                        JOptionPane.showMessageDialog(this, "Giải mã thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        // Encrypt File Button Event
        encryptFileBtn.addActionListener(e -> {
            String path = filePathField.getText();
            if (path.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập nội dung để mã hóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    String alg = algorithmOption.getSelectedItem().toString();
                    HashController controller = new HashController(alg);
                    path = filePathField.getText();
                    String encryptText = controller.hashFile(path);
                    outputText.setText(encryptText);

                    if (outputText.getText().equals(encryptText)) {
                        JOptionPane.showMessageDialog(this, "Giải mã thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        // Verify Text Button Event
        verifyBtn.addActionListener(e -> {
            String inputT = inputVerifyText.getText();
            if (inputT.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập nội dung để verify.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    String alg = algorithmOption.getSelectedItem().toString();
                    HashController controller = new HashController(alg);
                    String input = inputVerifyText.getText();
                    String inputVerify = inputText.getText();
                    String valid = "TEXT HỢP LỆ !!!";
                    String inValid = "TEXT KHÔNG HỢP LỆ !!!";
                    boolean verify = controller.verifyPlainText(input, inputVerify);
                    if (verify) {
                        JOptionPane.showMessageDialog(this, "Thông tin chính xác", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        outputVerifyText.setText(valid);
                    } else {
                        JOptionPane.showMessageDialog(this, "Thông tin sai lệch", "Thông báo", JOptionPane.WARNING_MESSAGE);
                        outputVerifyText.setText(inValid);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Verify File Button Event
        verifyFileBtn.addActionListener(e -> {
            String path = filePathField.getText();
            if (path.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng Nhập File để verify.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    String alg = algorithmOption.getSelectedItem().toString();
                    HashController controller = new HashController(alg);
                    String file = filePathField.getText();
                    String fileVerify = fileVerifyPathField.getText();
                    String valid = "FILE HỢP LỆ !!!";
                    String inValid = "FILE KHÔNG HỢP LỆ !!!";
                    boolean verify = controller.verifyFile(file, fileVerify);
                    if (verify == true) {
                        JOptionPane.showMessageDialog(this, "Thông tin chính xác", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        outputVerifyText.setText(valid);
                    } else {
                        JOptionPane.showMessageDialog(this, "Thông tin sai lệch", "Thông báo", JOptionPane.WARNING_MESSAGE);
                        outputVerifyText.setText(inValid);
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
