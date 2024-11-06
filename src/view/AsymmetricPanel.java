package view;

import javax.swing.*;
import java.awt.*;

public class AsymmetricPanel extends JPanel {
	private JComboBox<String> algorithmOption;
    private JComboBox<String> modeOption;
    private JComboBox<String> paddingOption;
    private JTextArea inputText;
    private JTextArea outputText;
    private JTabbedPane inputTabbedPane;
    private JTextField filePathField;
    private JButton browseButton;
    private JButton encryptBtn;
    private JButton decryptBtn;
    private JButton generateKeyBtn;
    private JButton loadKeyBtn;
    private JButton saveKeyBtn;
	public AsymmetricPanel() {
		setLayout(new BorderLayout(10, 10));
        initComponents();
        setupLayout();
        setupListeners();
    }
	private void initComponents() {
        // Options
        algorithmOption = new JComboBox<>(new String[]{"AES", "DES", "3DES", "Blowfish"});
        modeOption = new JComboBox<>(new String[]{"ECB", "CBC", "CFB", "OFB"});
        paddingOption = new JComboBox<>(new String[]{"PKCS5Padding", "NoPadding"});

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
        generateKeyBtn = new JButton("Tạo khóa");
        loadKeyBtn = new JButton("Nạp khóa");
        saveKeyBtn = new JButton("Lưu khóa");
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
        JPanel optionsPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        optionsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Tùy chọn"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        optionsPanel.add(new JLabel("Thuật toán: "));
        optionsPanel.add(algorithmOption);
        optionsPanel.add(new JLabel("Mode: "));
        optionsPanel.add(modeOption);
        optionsPanel.add(new JLabel("Padding: "));
        optionsPanel.add(paddingOption);

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
        buttonPanel.add(generateKeyBtn);
        buttonPanel.add(loadKeyBtn);
        buttonPanel.add(saveKeyBtn);

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
