package de.shept.changebg;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.*;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {

    private JFrame frame;
    public String file;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main window = new Main();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        frame.getContentPane().setLayout(null);

        JButton selectBtn = new JButton("Select");
        selectBtn.setBounds(155, 57, 85, 21);
        selectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == selectBtn) {

                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setMultiSelectionEnabled(false);
                    fileChooser.showOpenDialog(frame);
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg");
                    fileChooser.setFileFilter(filter);
                    file = fileChooser.getSelectedFile().getAbsolutePath();

                }
            }
        });
        frame.getContentPane().add(selectBtn);

        JButton changeBtn = new JButton("Change");
        changeBtn.setBounds(155, 139, 85, 21);
        changeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == changeBtn) {
                    change(file);
                    System.exit(0);
                }
            }
        });
        frame.getContentPane().add(changeBtn);
    }

    private void change(String file) {
        User32.INSTANCE.SystemParametersInfo(0x0014, 0, file , 1);
    }

    public static interface User32 extends Library {
        User32 INSTANCE = (User32) Native.loadLibrary("user32",User32.class, W32APIOptions.DEFAULT_OPTIONS);
        boolean SystemParametersInfo (int one, int two, String s ,int three);
    }

}
