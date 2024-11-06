package view;

import java.awt.*;
import java.io.*;

import javax.swing.*;

public class FileInputPanel extends JPanel {
	private JTextField filePath;
    private JButton browseButton;
    private JTextArea filePreview;
    private File selectedFile;
    
    public FileInputPanel() {
        setLayout(new BorderLayout(5, 5));
        initComponents();
        setupLayout();
        setupListeners();
    }
    
    private void initComponents() {
        filePath = new JTextField();
        filePath.setEditable(false);
        
        browseButton = new JButton("Chọn File");
        
        filePreview = new JTextArea(5, 40);
        filePreview.setEditable(false);
        filePreview.setFont(new Font("Monospaced", Font.PLAIN, 12));
    }
    
    private void setupLayout() {
        // Panel cho chọn file
        JPanel fileSelectionPanel = new JPanel(new BorderLayout(5, 0));
        fileSelectionPanel.add(new JLabel("File: "), BorderLayout.WEST);
        fileSelectionPanel.add(filePath, BorderLayout.CENTER);
        fileSelectionPanel.add(browseButton, BorderLayout.EAST);
        
        // Panel cho preview
        JPanel previewPanel = new JPanel(new BorderLayout());
        previewPanel.setBorder(BorderFactory.createTitledBorder("Xem trước nội dung file"));
        previewPanel.add(new JScrollPane(filePreview), BorderLayout.CENTER);
        
        // Thêm vào panel chính
        add(fileSelectionPanel, BorderLayout.NORTH);
        add(previewPanel, BorderLayout.CENTER);
        
        // Thêm padding
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }
    
    private void setupListeners() {
        browseButton.addActionListener(e -> selectFile());
    }
    
    private void selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            filePath.setText(selectedFile.getAbsolutePath());
            updatePreview();
        }
    }
    
    private void updatePreview() {
        try {
            // Đọc và hiển thị 1000 ký tự đầu tiên của file
            java.nio.file.Path path = selectedFile.toPath();
            String content = new String(java.nio.file.Files.readAllBytes(path));
            if (content.length() > 1000) {
                content = content.substring(0, 1000) + "...";
            }
            filePreview.setText(content);
        } catch (Exception e) {
            filePreview.setText("Không thể đọc file: " + e.getMessage());
        }
    }
    
    public File getSelectedFile() {
        return selectedFile;
    }
    
    public String getFilePath() {
        return filePath.getText();
    }
    
    public void clearSelection() {
        selectedFile = null;
        filePath.setText("");
        filePreview.setText("");
    }
}
