package io.zipcoder.crudapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.zipcoder.crudapp.Person;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PersonController {

    @Autowired
    public PersonRepo personRepo;

    Person createPerson(Person p) {
        return personRepo.save(p);
    }

    @RequestMapping("/{id}")
    Person getPerson(int id) {
        return personRepo.findOne(id);
    }

    Iterable<Person> getPersonList() {
        return personRepo.findAll();
    }

//    @RequestMapping("/{id}")
//    Person updatePerson(Person p) {
//        for (Person person : personRepo.findAll()) {
//            if (person.getId() == p.getId()) {
//                person = p;
//            }
//        }
//        return p;
//    }


}
