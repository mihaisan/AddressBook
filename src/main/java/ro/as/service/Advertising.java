package ro.as.service;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Advertising implements Runnable {
    private JLabel label;
    private List<ImageIcon> imageIconList= new ArrayList<>();

    public Advertising(JLabel label) {
        this.label = label;
    }

    @Override
    public void run() {
        try {
            File folder = new File(getClass().getClassLoader().getResource("advertising").getFile());
            for (final File fileEntry : folder.listFiles()) {
                BufferedImage img = ImageIO.read(fileEntry);
                Image dimg = img.getScaledInstance(425, 100, Image.SCALE_SMOOTH);
                imageIconList.add(new ImageIcon(dimg));
            }

            int max = imageIconList.size();
            while (true){
                Random random = new Random();
                int idx = random.nextInt(max);
                label.setIcon(imageIconList.get(idx));

                Thread.currentThread().sleep(2000);
            }

        } catch (IOException|InterruptedException e) {
            e.printStackTrace();
        }
    }
}
