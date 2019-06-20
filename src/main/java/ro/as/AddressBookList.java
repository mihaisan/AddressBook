package ro.as;

import ro.as.dao.Person;
import ro.as.dao.RandomPerson;
import ro.as.service.PersonService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AddressBookList implements ActionListener {
    JFrame appFrame;
    Container cPane;

    private JButton filterButton;
    private JComboBox filterField;
    private JTextField filter;

    private JComboBox orderField;
    private JButton orderButton;

    private JList jContactList;
    private List<Person> filterPersonList;

    private JButton addButton;
    private JButton deleteButton;
    private JButton editButton;

    private JMenuBar menuBar;

    private JDialog addDialog;
    private JButton saveButton;

    private JTextField firstName;
    private JTextField lastName;
    private JRadioButton mobile;
    private JRadioButton fPhone;
    private JTextField phone;
    private JTextField birthDate;

    private Integer id;

    private PersonService service = new PersonService();

    private JMenuItem saveMenu;
    private JMenuItem openMenu;
    private JMenuItem exitMenu;


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
        initMenuBar();
        initFilterRow();
        initOrderRow();
        initContactList();
        initButtons();
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
        gridBagConstraintsx02.gridy = 4;
        gridBagConstraintsx02.gridwidth = 7;
        gridBagConstraintsx02.fill = GridBagConstraints.BOTH;
        cPane.add(panel, gridBagConstraintsx02);
    }

    private void initContactList() {
        filterPersonList = new ArrayList<>(service.getPersonList());
        jContactList = new JList(service.getPersonList().toArray());

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
        gridBagConstraintsx02.gridy = 3;
        gridBagConstraintsx02.gridwidth = 7;
        gridBagConstraintsx02.fill = GridBagConstraints.BOTH;
        cPane.add(jScrollPane, gridBagConstraintsx02);
    }

    private List<Person> initPersonList(){
        List<Person> personList = new ArrayList<Person>();
        for (int i = 0; i < 30; i++) {
            Person person  = RandomPerson.randomPerson();
            person.setId(i+1);
            personList.add(person);
        }

        return personList;
    }

    private void initOrderRow() {
        JLabel orderFieldLabel = new JLabel("Ordonare:");

        orderField = new JComboBox();
        orderField.setModel(new DefaultComboBoxModel(new String[] { "", "Nume", "Prenume", "Telefon" }));

        orderButton = new JButton("Ordonare");
        orderButton.addActionListener(this);

        GridBagConstraints gridBagConstraintsx01 = new GridBagConstraints();
        gridBagConstraintsx01.gridx = 0;
        gridBagConstraintsx01.gridy = 1;
        gridBagConstraintsx01.insets = new Insets(5, 5, 5, 5);
        cPane.add(orderFieldLabel, gridBagConstraintsx01);

        GridBagConstraints gridBagConstraintsx02 = new GridBagConstraints();
        gridBagConstraintsx02.gridx = 1;
        gridBagConstraintsx02.insets = new Insets(5, 5, 5, 5);
        gridBagConstraintsx02.gridy = 1;
        gridBagConstraintsx02.gridwidth = 5;
        gridBagConstraintsx02.fill = GridBagConstraints.BOTH;
        cPane.add(orderField, gridBagConstraintsx02);

        GridBagConstraints gridBagConstraintsx03 = new GridBagConstraints();
        gridBagConstraintsx03.gridx = 6;
        gridBagConstraintsx03.insets = new Insets(5, 5, 5, 5);
        gridBagConstraintsx03.gridy = 1;
        gridBagConstraintsx03.fill = GridBagConstraints.BOTH;
        cPane.add(orderButton, gridBagConstraintsx03);
    }

    private void initFilterRow() {
        JLabel filterFieldLabel = new JLabel("Filtrare:");
        filterField = new JComboBox();
        filterField.setModel(new DefaultComboBoxModel(new String[] { "", "Nume", "Prenume", "Telefon", "Mobil", "Fix", "Zi de nastere" }));
        filterField.addActionListener(this);

        JLabel filterLabel = new JLabel("filtru:");
        filter = new JTextField(10);

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

    private void initMenuBar(){
        menuBar = new JMenuBar();

        appFrame.setJMenuBar(menuBar); // Add the menu bar to the window

        JMenu fileMenu = new JMenu("Fisiere"); // Create File menu
        saveMenu = new JMenuItem("Salvare");
        saveMenu.addActionListener(this);
        openMenu = new JMenuItem("Deschidere");
        openMenu.addActionListener(this);
        exitMenu = new JMenuItem("Iesire");
        exitMenu.addActionListener(this);

        fileMenu.add(saveMenu);
        fileMenu.add(openMenu);
        fileMenu.add(exitMenu);

        JMenu elementMenu = new JMenu("Ajutor"); // Create Elements menu
        menuBar.add(fileMenu); // Add the file menu
        menuBar.add(elementMenu);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == filterButton) {
            filterContacts();
        } else if (e.getSource() == filterField){
            enableFilterFields();
        } else if (e.getSource() == orderButton){
            orderContacts();
        } else if (e.getSource() == addButton){
            editAction(null);
        } else if (e.getSource() == editButton){
            editAction((Person) jContactList.getSelectedValue());
        } else if (e.getSource() == deleteButton){
            delete((Person) jContactList.getSelectedValue());
        } else if (e.getSource() == saveButton){
            save();
        } else if (e.getSource() == saveMenu){
            saveToFile();
        } else if (e.getSource() == openMenu){
            openFile();
        } else if (e.getSource() == exitMenu){
            appFrame.dispose();
        }
    }

    private void openFile() {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(null);
        // int returnValue = jfc.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
        }
    }

    private void delete(Person person) {
        int input = JOptionPane.showConfirmDialog(null, "Sunteti sigur ca doriti sa stergeti contactul?");
        // 0=yes, 1=no, 2=cancel
        if (input == 0){
            service.deletePerson(person);
        }
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
            service.savePerson(person);
            addDialog.setVisible(false);
            filterContacts();
            orderContacts();
        } catch (BussinesException e) {
            showErrorDialog(e.getMessage());
        }

    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message, "Eroare",
                JOptionPane.ERROR_MESSAGE);
    }




    private void editAction(Person person) {
        addDialog = new JDialog(appFrame, "Adauga contact");
        addDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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

        addDialog.pack();
        addDialog.setVisible(true);
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

        addDialog.setContentPane(panel);
    }

    private void orderContacts() {
        if (orderField.getSelectedItem().equals("Prenume")){
            filterPersonList.sort(Comparator.comparing(Person::getFirstName));
        } else if (orderField.getSelectedItem().equals("Nume")){
            filterPersonList.sort(Comparator.comparing(Person::getLastName));
        } else if (orderField.getSelectedItem().equals("Telefon")){
            filterPersonList.sort(Comparator.comparing(Person::getPhone));
        } else {
            filterPersonList = service.getPersonList();
        }

        jContactList.setListData(filterPersonList.toArray());
    }

    private void enableFilterFields() {
        if (filterField.getSelectedItem().equals("Mobil") ||
                filterField.getSelectedItem().equals("Fix")||
                filterField.getSelectedItem().equals("Zi de nastere")){
            filter.setEnabled(false);
        } else {
            filter.setEnabled(true);
        }
    }

    private void filterContacts() {
        if (filterField.getSelectedItem().equals("Prenume")){
            filterPersonList =  service.getPersonList().stream().filter(x->x.getFirstName().contains(filter.getText())).collect(Collectors.toList());
        } else if (filterField.getSelectedItem().equals("Nume")){
            filterPersonList =  service.getPersonList().stream().filter(x->x.getLastName().contains(filter.getText())).collect(Collectors.toList());
        } else if (filterField.getSelectedItem().equals("Telefon")){
            filterPersonList =  service.getPersonList().stream().filter(x->x.getPhone().contains(filter.getText())).collect(Collectors.toList());
        } else if (filterField.getSelectedItem().equals("Mobil")){
            filterPersonList =  service.getPersonList().stream().filter(x->x.getPhone().startsWith("07")).collect(Collectors.toList());
        } else if (filterField.getSelectedItem().equals("Fix")){
            filterPersonList =  service.getPersonList().stream().filter(x->!x.getPhone().startsWith("07"))
                    .collect(Collectors.toList());
        } else if (filterField.getSelectedItem().equals("Zi de nastere")){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(service.getPatternDate());
            filterPersonList =  service.getPersonList().stream().filter(x->x.getBirthDate().startsWith(simpleDateFormat.format(new Date()))).collect(Collectors.toList());
        } else {
            filterPersonList = service.getPersonList();
        }

        jContactList.setListData(filterPersonList.toArray());
    }

    protected void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        int retval = fileChooser.showSaveDialog(saveMenu);
        if (retval == JFileChooser.APPROVE_OPTION) {
            service.saveToFile(fileChooser.getSelectedFile());
            File file = fileChooser.getSelectedFile();
            if (file == null) {
                return;
            }
            if (!file.getName().toLowerCase().endsWith(".csv")) {
                file = new File(file.getParentFile(), file.getName() + ".csv");
            }

        }
    }


}
