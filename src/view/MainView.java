package view;

import java.awt.Font;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class MainView extends JFrame {

    private JTabbedPane tabbedPane;

    public MainView() throws HeadlessException {
        init();
        setUpMenuBar();
        setUpLayout();

    }

    private void init() {
        setTitle("Ứng dụng Mã hóa");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    private void setUpMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        // Menu Help
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    private void setUpLayout() {
        JPanel basicPanel = new BasicCipherPanel();
        JPanel symmetricPanel = new SymmetricPanel();
        JPanel asymmetricPanel = new AsymmetricPanel();
        JPanel hashPanel = new HashPanel();
        JPanel signaturePanel = new DigitalSignaturePanel();

        // Thêm các panel vào tabbedPane
        tabbedPane.addTab("Mã hóa cơ bản", basicPanel);
        tabbedPane.addTab("Mã hóa đối xứng", symmetricPanel);
        tabbedPane.addTab("Mã hóa bất đối xứng", asymmetricPanel);
        tabbedPane.addTab("Hash", hashPanel);
        tabbedPane.addTab("Chữ ký số", signaturePanel);

        // Thêm tabbedPane vào frame
        add(tabbedPane);
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
                "Ứng dụng Mã hóa v1.0\n"
                + "Môn học: An toàn và bảo mật thông tin\n"
                + "© 2024",
                "About",
                JOptionPane.INFORMATION_MESSAGE);
    }

}
