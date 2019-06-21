package ro.as.ui;

import ro.as.service.PersonService;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MenuBar  implements ActionListener {
    private JMenuBar menuBar;

    private JMenuItem saveMenu;
    private JMenuItem openMenu;
    private JMenuItem exitMenu;

    private JMenuItem registerMenu;
    private JMenuItem helpMenu;
    private AddressBookList abl;

    public MenuBar(AddressBookList abl) {
        this.abl = abl;
        initMenuBar();
    }

    public void initMenuBar(){
        menuBar = new JMenuBar();

        abl.getAppFrame().setJMenuBar(menuBar); // Add the menu bar to the window

        JMenu fileMenu = new JMenu("Fisiere"); // Create File menu
        saveMenu = new JMenuItem("Salvare");
        saveMenu.setVisible(false);
        saveMenu.addActionListener(this);
        openMenu = new JMenuItem("Deschidere");
        openMenu.addActionListener(this);
        openMenu.setVisible(false);
        exitMenu = new JMenuItem("Iesire");
        exitMenu.addActionListener(this);

        fileMenu.add(saveMenu);
        fileMenu.add(openMenu);
        fileMenu.add(exitMenu);

        JMenu elementMenu = new JMenu("Ajutor"); // Create Elements menu
        registerMenu = new JMenuItem("Inregistrare");
        registerMenu.addActionListener(this);
        helpMenu = new JMenuItem("Despre");
        helpMenu.addActionListener(this);

        elementMenu.add(registerMenu);
        elementMenu.add(helpMenu);

        menuBar.add(fileMenu); // Add the file menu
        menuBar.add(elementMenu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveMenu){
            saveToFile();
        } else if (e.getSource() == openMenu){
            openFile();
        } else if (e.getSource() == exitMenu){
            abl.getAppFrame().dispose();
        } else if (e.getSource() == registerMenu){
            registerDialog();
        } else if (e.getSource() == helpMenu){
            helpDialog();
        }
    }

    private void helpDialog() {
        JOptionPane.showMessageDialog(new JFrame(), "Help", "Ajutor",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void registerDialog() {
        RegisterDialog dialog = new RegisterDialog(abl.getAppFrame(), "Inregistrare", saveMenu, openMenu, registerMenu, abl.getAdvertisingLabel(), abl.getAdvertising());
        dialog.pack();
        dialog.setVisible(true);
    }

    private void openFile() {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            abl.getService().loadFile(selectedFile);
            abl.refreshList();
        }
    }

    protected void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        int retval = fileChooser.showSaveDialog(saveMenu);
        if (retval == JFileChooser.APPROVE_OPTION) {
            abl.getService().saveToFile(fileChooser.getSelectedFile());
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
