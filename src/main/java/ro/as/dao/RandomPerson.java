package ro.as.dao;

import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RandomPerson {
    public static Person randomPerson() {
        Faker faker = new Faker();

        Person person = new Person();
        person.setFirstName(faker.name().firstName());
        person.setLastName(faker.name().lastName());
        Random rn = new Random();
        String pattern = "dd.MM.yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        person.setBirthDate(simpleDateFormat.format(faker.date().past(rn.nextInt(10000) + 1, TimeUnit.DAYS)));
        person.setPhone(faker.phoneNumber().phoneNumber());

        return person;
    }
}
