package ro.as.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterDialog extends JDialog implements ActionListener {
    private JTextField register;
    private JButton registerButton;
    private static final String SECURITY_CODE = "1979";
    private JMenuItem saveMenu;
    private JMenuItem openMenu;
    private JMenuItem registerMenu;
    private JLabel advertisingLabel;
    private Thread advertising;

    public RegisterDialog(Frame owner, String title, JMenuItem saveMenu, JMenuItem openMenu, JMenuItem registerMenu, JLabel advertisingLabel, Thread advertising) {
        super(owner, title);

        this.saveMenu = saveMenu;
        this.openMenu = openMenu;
        this.registerMenu = registerMenu;
        this.advertisingLabel = advertisingLabel;
        this.advertising = advertising;

        init();
    }

    private void init() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JLabel registerLabel = new JLabel("Cod");
        register = new JTextField(20);

        GridBagConstraints gridBagConstraintsx01 = new GridBagConstraints();
        gridBagConstraintsx01.gridx = 0;
        gridBagConstraintsx01.gridy = 0;
        gridBagConstraintsx01.insets = new Insets(5, 5, 5, 5);
        panel.add(registerLabel, gridBagConstraintsx01);

        GridBagConstraints gridBagConstraintsx02 = new GridBagConstraints();
        gridBagConstraintsx02.gridx = 1;
        gridBagConstraintsx02.insets = new Insets(5, 5, 5, 5);
        gridBagConstraintsx02.gridy = 0;
        panel.add(register, gridBagConstraintsx02);

        registerButton = new JButton("Inregistrare");
        registerButton.addActionListener(this);

        GridBagConstraints gridBagConstraintsx04 = new GridBagConstraints();
        gridBagConstraintsx04.gridx = 0;
        gridBagConstraintsx04.insets = new Insets(5, 5, 5, 5);
        gridBagConstraintsx04.gridy = 1;
        gridBagConstraintsx04.gridwidth = 2;
        gridBagConstraintsx04.fill = GridBagConstraints.BOTH;
        panel.add(registerButton, gridBagConstraintsx04);

        this.setContentPane(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            register();
        }
    }

    private void register(){
        if (register.getText().equals(SECURITY_CODE)){
            advertising.interrupt();
            this.saveMenu.setVisible(true);
            this.openMenu.setVisible(true);
            this.advertisingLabel.setVisible(false);
            this.registerMenu.setVisible(false);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "Codul introdus nu este corect", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
