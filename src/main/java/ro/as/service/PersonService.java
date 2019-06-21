package ro.as.service;

import ro.as.ui.FilterEnum;
import ro.as.ui.FilterFields;
import ro.as.util.BussinesException;
import ro.as.dao.Person;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PersonService {

    private final String phoneRegex = "[0][7|2|3][0-9]{8}$";
    private final String dateRegex = "[\\d]{1,2}\\.[\\d]{1,2}\\.[\\d]{4}$";
    private final String patternDate = "dd.MM.yyyy";

    private List<Person> personList = new ArrayList<>();
    private List<Person> filteredList = new ArrayList<>();
    private ReadWriteCsv readWriteCsv = new ReadWriteCsv();
    private FilterFields orderField = FilterFields.Selecteaza;
    private FilterFields filterField = FilterFields.Selecteaza;
    private String filterValue;
    private int id = 999999;
    private Map<FilterEnum, Boolean> filterMap = new HashMap<>();

    public void init(){
    }

    public void savePerson(Person person) throws BussinesException {
        validatePerson(person);
        if (person.getId() != null){
            personList.removeIf(x->x.getId() == person.getId());
            filteredList.removeIf(x->x.getId() == person.getId());
        } else {
            person.setId(id);
            id++;
        }

        personList.add(person);
        filter();
        order();

        readWriteCsv.setListIsModified(true);
    }

    public void deletePerson(Person person) {
        personList.removeIf(x->x.getId() == person.getId());
        filteredList.removeIf(x->x.getId() == person.getId());

        filter();
        order();

        readWriteCsv.setListIsModified(true);
    }

    public void order(FilterFields orderField){
        this.orderField = orderField;
        order();
    }

    private void order(){
        if (orderField.equals(FilterFields.Prenume)){
            filteredList.sort(Comparator.comparing(Person::getFirstName));
        } else if (orderField.equals(FilterFields.Nume)){
            filteredList.sort(Comparator.comparing(Person::getLastName));
        } else if (orderField.equals(FilterFields.Telefon)){
            filteredList.sort(Comparator.comparing(Person::getPhone));
        } else {
            filteredList = new ArrayList<>(personList);
        }
    }

    public void filter(FilterFields filterField, String filterValue){
        this.filterField = filterField;
        this.filterValue = filterValue;
        filter();
    }

    private void filter() {
        if (filterField.equals(FilterFields.Prenume)){
            filteredList =  personList.stream().filter(x->x.getFirstName().contains(filterValue)).collect(Collectors.toList());
        } else if (filterField.equals(FilterFields.Nume)){
            filteredList =  personList.stream().filter(x->x.getLastName().contains(filterValue)).collect(Collectors.toList());
        } else if (filterField.equals(FilterFields.Telefon)){
            filteredList =  personList.stream().filter(x->x.getPhone().contains(filterValue)).collect(Collectors.toList());
        } else {
            filteredList = personList;
        }

        Predicate<Person> predicate = null;
        for (Map.Entry<FilterEnum, Boolean> entry : filterMap.entrySet()){
            if (predicate == null){
                predicate = createPredicate(entry.getKey());
            } else {
                predicate = predicate.and(createPredicate(entry.getKey()));
            }
        }

        if (predicate != null){
            filteredList = filteredList.stream().filter(predicate).collect(Collectors.toList());
        }
    }

    private Predicate<Person> createPredicate(FilterEnum key) {
        Predicate<Person> predicate = null;
        if (key.equals(FilterEnum.MOBILE)){
            predicate = x -> x.isMobile();
        } else if (key.equals(FilterEnum.FIX_PHONE)){
            predicate = x -> !x.isMobile();
        } else if (key.equals(FilterEnum.BIRTH_DATE_TODAY)){
            int curentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            predicate = x -> x.getBirthDate().startsWith((curentDay < 10 ? "0" + curentDay:curentDay + ""));
        } else if (key.equals(FilterEnum.BIRTH_DATE_THIS_MONTH)){
            int curentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
            predicate = x -> x.getBirthDate().startsWith((curentMonth < 10 ? "0" + curentMonth:curentMonth + ""), 3);
        }

        return predicate;
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

    public void loadFile(File file) {
        readWriteCsv.loadFile(file);
        personList.addAll(readWriteCsv.getPersonList());
        readWriteCsv.setPersonList(personList);
        Thread thread = new Thread(readWriteCsv);
        thread.start();

        filter();
        order();
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void saveToFile(File selectedFile) {
        readWriteCsv.saveToFile(selectedFile);
    }

    public List<Person> getFilteredList() {
        return filteredList;
    }

    public Map<FilterEnum, Boolean> getFilterMap() {
        return filterMap;
    }
}
