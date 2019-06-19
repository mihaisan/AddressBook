package ro.as;

import ro.as.dao.Person;
import ro.as.dao.RandomPerson;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AddressBookList implements ActionListener {
    ArrayList personsList;
    PersonDAO pDAO;
    JFrame appFrame;
    JLabel jlbName, jlbAddress, jlbPhone, jlbEmail;
    JTextField jtfName, jtfAddress, jtfPhone, jtfEmail;
    JButton jbbSave, jbnDelete, jbnClear, jbnUpdate, jbnSearch,
            jbnForward, jbnBack, jbnExit;
    String name, address, email;
    int phone;
    int recordNumber; // used to naviagate using &gt;&gt; and  buttons
    Container cPane;

    //todo
    private JButton filterButton;
    private JComboBox filterField;
    private JLabel filterFieldLabel;
    private JLabel filterLabel;
    private JTextField filter;

    private JLabel orderFieldLabel;
    private JComboBox orderField;
    private JButton orderButton;

    private JList jContactList;
    private List<Person> allPersonList;
    private List<Person> filterPersonList;

    private JButton addButton;
    private JButton deleteButton;
    private JButton editButton;

    private JMenuBar menuBar;


    public AddressBookList() {
        createGUI();
    }
    public void createGUI(){

        /*Create a frame, get its contentpane and set layout*/
        appFrame = new JFrame("Address Book List");
        cPane = appFrame.getContentPane();
        cPane.setLayout(new GridBagLayout());
        //Arrange components on contentPane and set Action Listeners to each JButton

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
        editButton = new JButton("Editeaza");
        editButton.setEnabled(false);
        deleteButton = new JButton("Sterge");
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
        allPersonList = initPersonList();
        filterPersonList = new ArrayList<>(allPersonList);
        jContactList = new JList(allPersonList.toArray());

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
            personList.add(RandomPerson.randomPerson());
        }

        return personList;
    }

    private void initOrderRow() {
        orderFieldLabel = new JLabel("Ordonare:");

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
        filterFieldLabel = new JLabel("Filtrare:");
        filterField = new JComboBox();
        filterField.setModel(new DefaultComboBoxModel(new String[] { "", "Nume", "Prenume", "Telefon", "Mobil", "Fix", "Zi de nastere" }));
        filterField.addActionListener(this);

        filterLabel = new JLabel("filtru:");
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
        }
    }

    private void orderContacts() {
        if (orderField.getSelectedItem().equals("Prenume")){
            filterPersonList.sort(Comparator.comparing(Person::getFirstName));
        } else if (orderField.getSelectedItem().equals("Nume")){
            filterPersonList.sort(Comparator.comparing(Person::getLastName));
        } else if (orderField.getSelectedItem().equals("Telefon")){
            filterPersonList.sort(Comparator.comparing(Person::getPhone));
        } else {
            filterPersonList = allPersonList;
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
            filterPersonList =  allPersonList.stream().filter(x->x.getFirstName().contains(filter.getText())).collect(Collectors.toList());
        } else if (filterField.getSelectedItem().equals("Nume")){
            filterPersonList =  allPersonList.stream().filter(x->x.getLastName().contains(filter.getText())).collect(Collectors.toList());
        } else if (filterField.getSelectedItem().equals("Telefon")){
            filterPersonList =  allPersonList.stream().filter(x->x.getPhone().contains(filter.getText())).collect(Collectors.toList());
        } else if (filterField.getSelectedItem().equals("Mobil")){
            filterPersonList =  allPersonList.stream().filter(x->x.getPhone().startsWith("07")).collect(Collectors.toList());
        } else if (filterField.getSelectedItem().equals("Fix")){
            filterPersonList =  allPersonList.stream().filter(x->!x.getPhone().startsWith("07"))
                    .collect(Collectors.toList());
        } else if (filterField.getSelectedItem().equals("Zi de nastere")){
            String pattern = "dd.MM.yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            filterPersonList =  allPersonList.stream().filter(x->x.getBirthDate().startsWith(simpleDateFormat.format(new Date()))).collect(Collectors.toList());
        } else {
            filterPersonList = allPersonList;
        }

        jContactList.setListData(filterPersonList.toArray());
    }

    // Save the Person into the Address Book
    public void savePerson() {
        name = jtfName.getText();
        name = name.toUpperCase(); //Save all names in Uppercase
        address = jtfAddress.getText();
        try {
            phone = Integer.parseInt("" + jtfPhone.getText());
        } catch (Exception e) {
			/*System.out.print("Input is a string");

			 JOptionPane.showMessageDialog(null, "Please enter Phone Number");*/
        }
        email = jtfEmail.getText();
        if (name.equals("")) {
            JOptionPane.showMessageDialog(null,
                    "Please enter person name.");
        } else {
            //create a ro.as.PersonInfo object and pass it to ro.as.PersonDAO to save it
            PersonInfo person = new PersonInfo(name, address, phone, email);
            pDAO.savePerson(person);
            JOptionPane.showMessageDialog(null, "Person Saved");
        }
    }
    public void deletePerson() {
        name = jtfName.getText();
        name = name.toUpperCase();
        if (name.equals("")) {
            JOptionPane.showMessageDialog(null,
                    "Please enter person name to delete.");
        } else {
            //remove Person of the given name from the Address Book database
            int numberOfDeleted = pDAO.removePerson(name);
            JOptionPane.showMessageDialog(null, numberOfDeleted
                    + " Record(s) deleted.");
        }
    }
    public void updatePerson() {
        if (recordNumber >= 0 && recordNumber < personsList.size()){
            PersonInfo person = (PersonInfo) personsList.get(recordNumber);
            int id = person.getId();
            /*get values from text fields*/
            name = jtfName.getText();
            address = jtfAddress.getText();
            phone = Integer.parseInt(jtfPhone.getText());
            email = jtfEmail.getText();
            /*update data of the given person name*/
            person = new PersonInfo(id, name, address, phone, email);
            pDAO.updatePerson(person);
            JOptionPane.showMessageDialog(null,
                    "Person info record updated successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "No record to Update");
        }
    }
    //Perform a Case-Insensitive Search to find the Person
    public void searchPerson() {
        name = jtfName.getText();
        name = name.toUpperCase();
        /*clear contents of arraylist if there are any from previous search*/
        personsList.clear();
        recordNumber = 0;
        if (name.equals("")) {
            JOptionPane.showMessageDialog(null,
                    "Please enter person name to search.");
        } else {
            /*get an array list of searched persons using ro.as.PersonDAO*/
            personsList = pDAO.searchPerson(name);
            if (personsList.size() == 0) {
                JOptionPane.showMessageDialog(null, "No records found.");
                //Perform a clear if no records are found.
                clear();
            } else {
                /*downcast the object from array list to ro.as.PersonInfo*/
                PersonInfo person = (PersonInfo) personsList
                        .get(recordNumber);
                // displaying search record in text fields
                jtfName.setText(person.getName());
                jtfAddress.setText(person.getAddress());
                jtfPhone.setText("" + person.getPhone());
                jtfEmail.setText(person.getEmail());
            }
        }
    }
    public void displayNextRecord() {
        // inc in recordNumber to display next person info, already stored in
        //  personsList during search
        recordNumber++;
        if (recordNumber >= personsList.size()) {
            JOptionPane.showMessageDialog(null, "You have reached end of "
                    + "search results");
            /*if user has reached the end of results, disable forward button*/
            jbnForward.setEnabled(false);
            jbnBack.setEnabled(true);
            // dec by one to counter last inc
            recordNumber--;
        } else {
            jbnBack.setEnabled(true);
            PersonInfo person = (PersonInfo) personsList.get(recordNumber);
            // displaying search record in text fields
            jtfName.setText(person.getName());
            jtfAddress.setText(person.getAddress());
            jtfPhone.setText("" + person.getPhone());
            jtfEmail.setText(person.getEmail());
        }
    }
    public void displayPreviousRecord() {
        // dec in recordNumber to display previous person info, already
        //stored in personsList during search
        recordNumber--;
        if (recordNumber < 0) {
            JOptionPane.showMessageDialog(null,
                    "You have reached begining " + "of search results");
            /*if user has reached the begining of results, disable back button*/
            jbnForward.setEnabled(true);
            jbnBack.setEnabled(false);
            // inc by one to counter last dec
            recordNumber++;
        } else {
            jbnForward.setEnabled(true);
            PersonInfo person = (PersonInfo) personsList.get(recordNumber);
            // displaying search record in text fields
            jtfName.setText(person.getName());
            jtfAddress.setText(person.getAddress());
            jtfPhone.setText("" + person.getPhone());
            jtfEmail.setText(person.getEmail());
        }
    }
    public void clear() {
        jtfName.setText("");
        jtfAddress.setText("");
        jtfPhone.setText("");
        jtfEmail.setText("");
        /*clear contents of arraylist*/
        recordNumber = -1;
        personsList.clear();
        jbnForward.setEnabled(true);
        jbnBack.setEnabled(true);
    }
}
