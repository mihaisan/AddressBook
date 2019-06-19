package ro.as;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SplashDemo extends JWindow {
    static JProgressBar progressBar = new JProgressBar();
    static int count = 1, TIMER_PAUSE = 1, PROGBAR_MAX = 100;
    static Timer progressBarTimer;
    ActionListener al = new ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            progressBar.setValue(count);
            if (PROGBAR_MAX == count) {
                progressBarTimer.stop();
                SplashDemo.this.setVisible(false);
                createAndShowFrame();
            }
            count++;
        }
    };

    public SplashDemo() {
        this.setSize(new Dimension(500, 500));
        Container container = getContentPane();
        
        JPanel panel = new JPanel();
        panel.setBorder(new EtchedBorder());
        container.add(panel, BorderLayout.CENTER);

        try {
            BufferedImage img = ImageIO.read(new File(getClass().getClassLoader().getResource("images/address-book.jpg").getFile()));
            Image dimg = img.getScaledInstance(500, 400, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(dimg);

            JLabel label = new JLabel(imageIcon);
            panel.add(label);
        } catch (IOException e) {
            JLabel label = new JLabel("Hello World!");
            label.setFont(new Font("Verdana", Font.BOLD, 14));
            panel.add(label);
        }

        progressBar.setMaximum(PROGBAR_MAX);
        container.add(progressBar, BorderLayout.SOUTH);
        pack();
        setVisible(true);
        startProgressBar();
    }
    
    
    private void startProgressBar() {
        progressBarTimer = new Timer(TIMER_PAUSE, al);
        progressBarTimer.start();
    }
    private void createAndShowFrame() {
//        JFrame frame = new JFrame();
//        frame.setSize(500, 500);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
        new AddressBookList();
    }

    public static void main (String args[]) {
        SplashDemo test = new SplashDemo();
    }
}