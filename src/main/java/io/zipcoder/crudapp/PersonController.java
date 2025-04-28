package io.zipcoder.crudapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//import io.zipcoder.crudapp.Person;

//import java.util.List;

@RestController
@RequestMapping("/people")
public class PersonController {

    @Autowired
    public PersonRepo personRepo;

    @PostMapping
    public Person createPerson(@RequestBody Person p) {
        return personRepo.save(p);
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable int id) {
        return personRepo.findOne(id);
    }

    @GetMapping
    public Iterable<Person> getPersonList() {
        return personRepo.findAll();
    }

    @PostMapping("/{id}")
    public Person updatePerson(@RequestBody Person p) {
        return personRepo.save(p);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable int id) {
        personRepo.delete(id);
    }


}
