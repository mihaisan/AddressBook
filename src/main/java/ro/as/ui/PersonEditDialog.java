package ro.as.ui;

import ro.as.util.BussinesException;
import ro.as.dao.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonEditDialog extends JDialog implements ActionListener {

    private JButton saveButton;

    private JTextField firstName;
    private JTextField lastName;
    private JRadioButton mobile;
    private JRadioButton fPhone;
    private JTextField phone;
    private JTextField birthDate;

    private Integer id;

    private AddressBookList abl;

    public PersonEditDialog(Frame owner, String title, Person person, AddressBookList abl) {
        super(owner, title);
        this.abl = abl;
        initEditPersonDialog();
        if (person != null){
            firstName.setText(person.getFirstName());
            lastName.setText(person.getLastName());
            phone.setText(person.getPhone());
            birthDate.setText(person.getBirthDate());
            if (!person.isMobile()){
                fPhone.setSelected(true);
            }
            id = person.getId();
        } else {
            id = null;
        }
    }

    private void initEditPersonDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JLabel firstNameLabel = new JLabel("Prenume");
        firstName = new JTextField(20);

        GridBagConstraints gridBagConstraintsx01 = new GridBagConstraints();
        gridBagConstraintsx01.gridx = 0;
        gridBagConstraintsx01.gridy = 0;
        gridBagConstraintsx01.insets = new Insets(5, 5, 5, 5);
        panel.add(firstNameLabel, gridBagConstraintsx01);

        GridBagConstraints gridBagConstraintsx02 = new GridBagConstraints();
        gridBagConstraintsx02.gridx = 1;
        gridBagConstraintsx02.insets = new Insets(5, 5, 5, 5);
        gridBagConstraintsx02.gridy = 0;
        gridBagConstraintsx02.gridwidth = 2;
        gridBagConstraintsx02.fill = GridBagConstraints.BOTH;
        panel.add(firstName, gridBagConstraintsx02);

        JLabel nameLabel = new JLabel("Nume");
        lastName = new JTextField(20);

        GridBagConstraints gridBagConstraintsx03 = new GridBagConstraints();
        gridBagConstraintsx03.gridx = 0;
        gridBagConstraintsx03.gridy = 1;
        gridBagConstraintsx03.insets = new Insets(5, 5, 5, 5);
        panel.add(nameLabel, gridBagConstraintsx03);

        GridBagConstraints gridBagConstraintsx04 = new GridBagConstraints();
        gridBagConstraintsx04.gridx = 1;
        gridBagConstraintsx04.insets = new Insets(5, 5, 5, 5);
        gridBagConstraintsx04.gridy = 1;
        gridBagConstraintsx04.gridwidth = 2;
        gridBagConstraintsx04.fill = GridBagConstraints.BOTH;
        panel.add(lastName, gridBagConstraintsx04);

        mobile = new JRadioButton("Mobil", true);
        fPhone = new JRadioButton("Fix");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(mobile);
        buttonGroup.add(fPhone);

        JPanel phonePanel = new JPanel();
        phonePanel.add(mobile);
        phonePanel.add(fPhone);

        GridBagConstraints gridBagConstraintsx05 = new GridBagConstraints();
        gridBagConstraintsx05.gridx = 1;
        gridBagConstraintsx05.insets = new Insets(5, 5, 5, 5);
        gridBagConstraintsx05.gridy = 2;
        gridBagConstraintsx05.gridwidth = 3;
        gridBagConstraintsx05.fill = GridBagConstraints.BOTH;
        panel.add(phonePanel, gridBagConstraintsx05);

        JLabel phoneLabel = new JLabel("Telefon");
        phone = new JTextField(20);

        GridBagConstraints gridBagConstraintsx06 = new GridBagConstraints();
        gridBagConstraintsx06.gridx = 0;
        gridBagConstraintsx06.gridy = 3;
        gridBagConstraintsx06.insets = new Insets(5, 5, 5, 5);
        panel.add(phoneLabel, gridBagConstraintsx06);

        GridBagConstraints gridBagConstraintsx07 = new GridBagConstraints();
        gridBagConstraintsx07.gridx = 1;
        gridBagConstraintsx07.insets = new Insets(5, 5, 5, 5);
        gridBagConstraintsx07.gridy = 3;
        gridBagConstraintsx07.gridwidth = 2;
        gridBagConstraintsx07.fill = GridBagConstraints.BOTH;
        panel.add(phone, gridBagConstraintsx07);

        JLabel birthDateLabel = new JLabel("Data nasterii");
        birthDate = new JTextField(20);

        GridBagConstraints gridBagConstraintsx08 = new GridBagConstraints();
        gridBagConstraintsx08.gridx = 0;
        gridBagConstraintsx08.gridy = 4;
        gridBagConstraintsx08.insets = new Insets(5, 5, 5, 5);
        panel.add(birthDateLabel, gridBagConstraintsx08);

        GridBagConstraints gridBagConstraintsx09 = new GridBagConstraints();
        gridBagConstraintsx09.gridx = 1;
        gridBagConstraintsx09.insets = new Insets(5, 5, 5, 5);
        gridBagConstraintsx09.gridy = 4;
        gridBagConstraintsx09.gridwidth = 2;
        gridBagConstraintsx09.fill = GridBagConstraints.BOTH;
        panel.add(birthDate, gridBagConstraintsx09);

        saveButton = new JButton("Salveza");
        saveButton.addActionListener(this);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);

        GridBagConstraints gridBagConstraintsx10 = new GridBagConstraints();
        gridBagConstraintsx10.gridx = 0;
        gridBagConstraintsx10.insets = new Insets(5, 5, 5, 5);
        gridBagConstraintsx10.gridy = 5;
        gridBagConstraintsx10.gridwidth = 3;
        gridBagConstraintsx10.fill = GridBagConstraints.BOTH;
        panel.add(buttonPanel, gridBagConstraintsx10);

        this.setContentPane(panel);
    }

    private void save() {
        Person person = new Person();
        person.setId(id);
        person.setFirstName(firstName.getText());
        person.setLastName(lastName.getText());
        if (mobile.isSelected()){
            person.setMobile(true);
        }
        person.setPhone(phone.getText());
        person.setBirthDate(birthDate.getText());

        try {
            abl.getService().savePerson(person);
            this.dispose();
            abl.refreshList();
        } catch (BussinesException e) {
            showErrorDialog(e.getMessage());
        }

    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message, "Eroare",
                JOptionPane.ERROR_MESSAGE);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton){
            save();
        }
    }
}
