package test;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import view.MainView;

public class RunProject {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.getStackTrace();
            }

            MainView mainView = new MainView();
            mainView.setVisible(true);
        });
    }
}
