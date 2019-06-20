package ro.as.service;

import ro.as.BussinesException;
import ro.as.ReadCsv;
import ro.as.dao.Person;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonService {

    private final String phoneRegex = "[0][7|2|3][0-9]{8}$";
    private final String dateRegex = "[\\d]{1,2}\\.[\\d]{1,2}\\.[\\d]{4}$";
    private final String patternDate = "dd.MM.yyyy";

    private List<Person> personList;
    private ReadCsv readCsv = new ReadCsv();

    public void init(){
        readCsv.read();
        personList = readCsv.getPersonList();
    }

    public void savePerson(Person person) throws BussinesException {
        validatePerson(person);
        readCsv.savePerson(person);
    }

    public void deletePerson(Person person) {
        readCsv.deletePerson(person);
    }

    private void validatePerson(Person person) throws BussinesException {
        if (person.getFirstName().length() < 2 || person.getLastName().length() < 2){
            throw new BussinesException("Numele si prenumele trebuie sa aiba cel putin doua litere");
        }

        Pattern pattern = Pattern.compile(phoneRegex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(person.getPhone());


        if (!matcher.find()){
            throw new BussinesException("numarul de telefon:\n" +
                    "◦ trebuie sa aiba 10 cifre\n" +
                    "◦ numerele mobile incep cu 07\n" +
                    "◦ numerele fixe incep cu 02 sau 03");
        }

        pattern = Pattern.compile(dateRegex, Pattern.MULTILINE);
        matcher = pattern.matcher(person.getBirthDate());

        if (!matcher.find()){
            throw new BussinesException("data nasterii trebuie sa fie una valida, in format ZZ.LL.AAAA");
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patternDate);
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(person.getBirthDate());
        } catch (ParseException e) {
            throw new BussinesException("Data nasterii trebuie sa fie una valida, in format ZZ.LL.AAAA");
        }

        Predicate<Person> firstNameFilter = x -> x.getFirstName().toLowerCase().equals(person.getFirstName().toLowerCase());
        Predicate<Person> lastNameFilter = x -> x.getLastName().toLowerCase().equals(person.getLastName().toLowerCase());

        if (person.getId() == null && getPersonList().stream().anyMatch(firstNameFilter.and(lastNameFilter))){
            throw new BussinesException("Persoana " + person.getFirstName() + " " + person.getLastName() + " exista");
        }
    }

    public String getPatternDate() {
        return patternDate;
    }

    public ReadCsv getReadCsv() {
        return readCsv;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void saveToFile(File selectedFile) {
        readCsv.saveToFile(selectedFile);
    }
}
