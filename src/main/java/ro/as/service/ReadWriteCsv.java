package ro.as.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import ro.as.dao.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadWriteCsv implements Runnable {
    private List<Person> personList = new ArrayList<>();
    final private static String FILE_NAME = "d:/work/book.csv";
    private File file;
    private boolean listIsModified;

    @Override
    public void run() {
        while (true) {
            try {
                Thread.currentThread().sleep(60000);
                if (listIsModified){
                    writeCSV(file);
                    listIsModified = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadFile(File file){
        this.file = file;
        try {
            FileInputStream fstream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            CSVParser csvParser = new CSVParser(br, CSVFormat.DEFAULT.withHeader());

            int i = 1;
            for (CSVRecord csvRecord : csvParser) {
                Person person = new Person();
                person.setFirstName(csvRecord.get("firstName").trim());
                person.setLastName(csvRecord.get("lastName").trim());
                person.setPhone(csvRecord.get("phone"));
                person.setBirthDate(csvRecord.get("birthDate"));
                person.setMobile(Boolean.parseBoolean(csvRecord.get("mobile")));
                person.setId(i);

                personList.add(person);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeCSV(File file){
        try {
            FileWriter fw = new FileWriter(file, false);
            BufferedWriter writer = new BufferedWriter(fw);
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("firstName", "lastName", "phone", "birthDate", "mobile"));

            for (Person person : personList){
                csvPrinter.printRecord(person.getFirstName(),
                        person.getLastName(),
                        person.getPhone(),
                        person.getBirthDate(),
                        person.isMobile());
            }
            csvPrinter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public void saveToFile(File selectedFile) {
        writeCSV(selectedFile);
    }

    public void setListIsModified(boolean listIsModified) {
        this.listIsModified = listIsModified;
    }
}
