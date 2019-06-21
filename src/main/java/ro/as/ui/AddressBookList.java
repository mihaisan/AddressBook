package ro.as.ui;

import ro.as.dao.Person;
import ro.as.service.Advertising;
import ro.as.service.PersonService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddressBookList implements ActionListener {
    private JFrame appFrame;
    private Container cPane;



    private JComboBox orderField;
    private JButton orderButton;

    private JList jContactList;

    private JButton addButton;
    private JButton deleteButton;
    private JButton editButton;

    private JLabel advertisingLabel;

    private JButton filterButton;
    private JComboBox filterField;
    private JTextField filter;

    private JCheckBox isMobile;
    private JCheckBox isPhone;
    private JCheckBox birthDateToday;
    private JCheckBox birthDateThisMonth;

    private Thread advertising;
    private PersonService service = new PersonService();


    public AddressBookList() {
        createGUI();
    }

    private void createGUI(){

        /*Create a frame, get its contentpane and set layout*/
        appFrame = new JFrame("Address Book List");
        cPane = appFrame.getContentPane();
        cPane.setLayout(new GridBagLayout());
        //Arrange components on contentPane and set Action Listeners to each JButton

        service.init();
        arrangeComponents();
        appFrame.setSize(500,500);
        appFrame.setResizable(false);
        appFrame.setVisible(true);

    }

    public void arrangeComponents() {
        new MenuBar(this);
        initFilterRow1();
        initFilterRow2();
        initOrderRow();
        initContactList();
        initButtons();
        initAdvertising();
    }

    private void initFilterRow1() {
        JLabel filterFieldLabel = new JLabel("Filtrare:");
        filterField = new JComboBox();
        filterField.setModel(new DefaultComboBoxModel(FilterFields.values()));
        filterField.addActionListener(this);

        JLabel filterLabel = new JLabel("filtru:");
        filter = new JTextField(10);
        filter.setEnabled(false);

        filterButton = new JButton("Filtrare");
        filterButton.addActionListener(this);

        GridBagConstraints gridBagConstraintsx01 = new GridBagConstraints();
        gridBagConstraintsx01.gridx = 0;
        gridBagConstraintsx01.gridy = 0;
        gridBagConstraintsx01.insets = new Insets(5, 5, 5, 5);
        cPane.add(filterFieldLabel, gridBagConstraintsx01);

        GridBagConstraints gridBagConstraintsx02 = new GridBagConstraints();
        gridBagConstraintsx02.gridx = 1;
        gridBagConstraintsx02.insets = new Insets(5, 5, 5, 5);
        gridBagConstraintsx02.gridy = 0;
        gridBagConstraintsx02.gridwidth = 2;
        gridBagConstraintsx02.fill = GridBagConstraints.BOTH;
        cPane.add(filterField, gridBagConstraintsx02);

        GridBagConstraints gridBagConstraintsx03 = new GridBagConstraints();
        gridBagConstraintsx03.gridx = 3;
        gridBagConstraintsx03.insets = new Insets(5, 5, 5, 5);
        gridBagConstraintsx03.gridy = 0;
        gridBagConstraintsx03.fill = GridBagConstraints.BOTH;
        cPane.add(filterLabel, gridBagConstraintsx03);

        GridBagConstraints gridBagConstraintsx04 = new GridBagConstraints();
        gridBagConstraintsx04.gridx =4;
        gridBagConstraintsx04.insets = new Insets(5, 5, 5, 5);
        gridBagConstraintsx04.gridy = 0;
        gridBagConstraintsx04.gridwidth = 2;
        gridBagConstraintsx04.fill = GridBagConstraints.BOTH;
        cPane.add(filter, gridBagConstraintsx04);

        GridBagConstraints gridBagConstraintsx05 = new GridBagConstraints();
        gridBagConstraintsx05.gridx = 6;
        gridBagConstraintsx05.insets = new Insets(5, 5, 5, 5);
        gridBagConstraintsx05.gridy = 0;
        gridBagConstraintsx05.fill = GridBagConstraints.BOTH;
        cPane.add(filterButton, gridBagConstraintsx05);

    }

    private void initFilterRow2() {
        isMobile = new JCheckBox("Mobil");
        isMobile.addActionListener(this);
        isPhone = new JCheckBox("Fix");
        isPhone.addActionListener(this);
        birthDateToday = new JCheckBox("Zi de nastere azi");
        birthDateToday.addActionListener(this);
        birthDateThisMonth = new JCheckBox("Zi de nastere in aceasta luna");
        birthDateThisMonth.addActionListener(this);

        JPanel panel = new JPanel();
        panel.add(isMobile);
        panel.add(isPhone);
        panel.add(birthDateToday);
        panel.add(birthDateThisMonth);

        GridBagConstraints gridBagConstraintsx01 = new GridBagConstraints();
        gridBagConstraintsx01.gridx = 0;
        gridBagConstraintsx01.gridy = 1;
        gridBagConstraintsx01.insets = new Insets(5, 5, 5, 5);
        gridBagConstraintsx01.gridwidth = 7;
        gridBagConstraintsx01.fill = GridBagConstraints.BOTH;
        cPane.add(panel, gridBagConstraintsx01);
    }

    private void initAdvertising() {
        advertisingLabel = new JLabel();

        GridBagConstraints gridBagConstraintsx02 = new GridBagConstraints();
        gridBagConstraintsx02.gridx = 0;
        gridBagConstraintsx02.insets = new Insets(5, 5, 5, 5);
        gridBagConstraintsx02.gridy = 6;
        gridBagConstraintsx02.gridwidth = 7;
        gridBagConstraintsx02.fill = GridBagConstraints.BOTH;
        cPane.add(advertisingLabel, gridBagConstraintsx02);

        advertising = new Thread(new Advertising(advertisingLabel));
        advertising.start();
    }

    private void initButtons() {
        addButton = new JButton("Adauga");
        addButton.addActionListener(this);
        editButton = new JButton("Editeaza");
        editButton.addActionListener(this);
        editButton.setEnabled(false);
        deleteButton = new JButton("Sterge");
        deleteButton.addActionListener(this);
        deleteButton.setEnabled(false);

        JPanel panel = new JPanel();
        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);

        GridBagConstraints gridBagConstraintsx02 = new GridBagConstraints();
        gridBagConstraintsx02.gridx = 0;
        gridBagConstraintsx02.insets = new Insets(5, 5, 5, 5);
        gridBagConstraintsx02.gridy = 5;
        gridBagConstraintsx02.gridwidth = 7;
        gridBagConstraintsx02.fill = GridBagConstraints.BOTH;
        cPane.add(panel, gridBagConstraintsx02);
    }

    private void initContactList() {
        jContactList = new JList(service.getFilteredList().toArray());

        jContactList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
            }
        });

        JScrollPane jScrollPane = new JScrollPane(jContactList);

        GridBagConstraints gridBagConstraintsx02 = new GridBagConstraints();
        gridBagConstraintsx02.gridx = 0;
        gridBagConstraintsx02.insets = new Insets(5, 5, 5, 5);
        gridBagConstraintsx02.gridy = 4;
        gridBagConstraintsx02.gridwidth = 7;
        gridBagConstraintsx02.fill = GridBagConstraints.BOTH;
        cPane.add(jScrollPane, gridBagConstraintsx02);
    }

    private void initOrderRow() {
        JLabel orderFieldLabel = new JLabel("Ordonare:");

        orderField = new JComboBox();
        orderField.setModel(new DefaultComboBoxModel(FilterFields.values()));

        orderButton = new JButton("Ordonare");
        orderButton.addActionListener(this);

        GridBagConstraints gridBagConstraintsx01 = new GridBagConstraints();
        gridBagConstraintsx01.gridx = 0;
        gridBagConstraintsx01.gridy = 2;
        gridBagConstraintsx01.insets = new Insets(5, 5, 5, 5);
        cPane.add(orderFieldLabel, gridBagConstraintsx01);

        GridBagConstraints gridBagConstraintsx02 = new GridBagConstraints();
        gridBagConstraintsx02.gridx = 1;
        gridBagConstraintsx02.insets = new Insets(5, 5, 5, 5);
        gridBagConstraintsx02.gridy = 2;
        gridBagConstraintsx02.gridwidth = 5;
        gridBagConstraintsx02.fill = GridBagConstraints.BOTH;
        cPane.add(orderField, gridBagConstraintsx02);

        GridBagConstraints gridBagConstraintsx03 = new GridBagConstraints();
        gridBagConstraintsx03.gridx = 6;
        gridBagConstraintsx03.insets = new Insets(5, 5, 5, 5);
        gridBagConstraintsx03.gridy = 2;
        gridBagConstraintsx03.fill = GridBagConstraints.BOTH;
        cPane.add(orderButton, gridBagConstraintsx03);

    }



    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == orderButton){
            refreshList();
        } else if (e.getSource() == addButton){
            editAction(null);
        } else if (e.getSource() == editButton){
            editAction((Person) jContactList.getSelectedValue());
        } else if (e.getSource() == deleteButton){
            delete((Person) jContactList.getSelectedValue());
        } else if (e.getSource() == filterButton) {
            refreshList();
        } else if (e.getSource() == filterField){
            enableFilterFields();
        } else if (e.getSource() == isMobile){
            filter(FilterEnum.MOBILE, isMobile.isSelected());
        } else if (e.getSource() == isPhone){
            filter(FilterEnum.FIX_PHONE, isPhone.isSelected());
        } else if (e.getSource() == birthDateToday){
            filter(FilterEnum.BIRTH_DATE_TODAY, birthDateToday.isSelected());
        } else if (e.getSource() == birthDateThisMonth){
            filter(FilterEnum.BIRTH_DATE_THIS_MONTH, birthDateThisMonth.isSelected());
        }
    }

    private void filter(FilterEnum key, boolean selected) {
        if (selected){
            service.getFilterMap().put(key, true);
        } else {
            service.getFilterMap().remove(key, true);
        }

        refreshList();
    }

    private void enableFilterFields() {
        if (filterField.getSelectedItem().equals(FilterFields.Selecteaza)){
            filter.setEnabled(false);
        } else {
            filter.setEnabled(true);
        }
    }

    private void delete(Person person) {
        int input = JOptionPane.showConfirmDialog(null, "Sunteti sigur ca doriti sa stergeti contactul?");
        // 0=yes, 1=no, 2=cancel
        if (input == 0){
            service.deletePerson(person);
        }
    }

    private void editAction(Person person) {
        JDialog addDialog = new PersonEditDialog(appFrame, "Adauga contact", person, this);
        addDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addDialog.pack();
        addDialog.setVisible(true);
    }

    private void orderContacts() {
        service.order((FilterFields) orderField.getSelectedItem());
    }

    private void filterContacts() {
        service.filter((FilterFields) filterField.getSelectedItem(), filter.getText());
    }

    public void refreshList(){
        orderContacts();
        filterContacts();
        jContactList.setListData(service.getFilteredList().toArray());
    }

    public JFrame getAppFrame() {
        return appFrame;
    }

    public PersonService getService() {
        return service;
    }

    public JLabel getAdvertisingLabel() {
        return advertisingLabel;
    }

    public Thread getAdvertising() {
        return advertising;
    }
}
