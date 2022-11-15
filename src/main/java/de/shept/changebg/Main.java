package de.shept.changebg;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

public class Main {

    private JFrame frame;
    public String file;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Main window = new Main();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public Main() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        LafManager.install(new DarculaTheme());

        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setLayout(null);

        JButton selectBtn = new JButton("Select");
        selectBtn.setBounds(165, 47, 85, 21);
        selectBtn.addActionListener(e -> {
            if (e.getSource() == selectBtn) {

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(false);
                fileChooser.showOpenDialog(frame);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg");
                fileChooser.setFileFilter(filter);
                file = fileChooser.getSelectedFile().getAbsolutePath();

            }
        });
        frame.getContentPane().add(selectBtn);

        JButton changeBtn = new JButton("Change");
        changeBtn.setBounds(165, 129, 85, 21);
        changeBtn.addActionListener(e -> {
            if (e.getSource() == changeBtn) {
                change(file);
                System.exit(0);
            }
        });
        frame.getContentPane().add(changeBtn);

        JButton defaultBtn = new JButton("Default");
        defaultBtn.setBounds(165, 211, 85, 21);
        defaultBtn.addActionListener(e -> {
            if (e.getSource() == defaultBtn) {
                change("C:\\Windows\\Web\\Wallpaper\\Windows\\img0.jpg");
                System.exit(0);
            }
        });
        frame.getContentPane().add(defaultBtn);
    }

    private void change(String file) {
        User32.INSTANCE.SystemParametersInfo(0x0014, 0, file , 1);
    }

    @SuppressWarnings("deprecation")
    public interface User32 extends Library {
        User32 INSTANCE = Native.loadLibrary("user32",User32.class, W32APIOptions.DEFAULT_OPTIONS);
        void SystemParametersInfo (int one, int two, String s , int three);
    }

}
