package io.zipcoder.crudapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
//import io.zipcoder.crudapp.Person;

//import java.util.List;

@RestController
@RequestMapping("/people")
public class PersonController {

    private final Logger LOG = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    public PersonRepo personRepo;

//    @PostMapping
//    public Person createPerson(@RequestBody Person p) {
//        return personRepo.save(p);
//    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person createdPerson = new Person(person.getFirstName(), person.getLastName());
        personRepo.save(createdPerson);
        return new ResponseEntity<Person>(createdPerson, HttpStatus.CREATED);
    }

//    @GetMapping("/{id}")
//    public Person getPerson(@PathVariable int id) {
//        return personRepo.findOne(id);
//    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Person> getById(@PathVariable int id) {
        Person personById = personRepo.findOne(id);
        if (personById == null) {
            return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Person>(personById, HttpStatus.OK);
    }

//    @GetMapping
//    public Iterable<Person> getPersonList() {
//        return personRepo.findAll();
//    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Iterable<Person>> getAll() {
        Iterable<Person> people = personRepo.findAll();
        if (people == null) {
            return new ResponseEntity<Iterable<Person>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Iterable<Person>>(people, HttpStatus.OK);
    }

//    @PostMapping("/{id}")
//    public Person updatePerson(@RequestBody Person p) {
//        return personRepo.save(p);
//    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Person> updateById(@PathVariable("id") int id, @RequestBody Person person) {
        Person current = personRepo.findOne(id);

        if (current == null) {
            return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
        }
        current.setId(person.getId());
        current.setFirstName(person.getFirstName());
        current.setLastName(person.getLastName());

        personRepo.save(current);
        return new ResponseEntity<Person>(current, HttpStatus.OK);
    }

//    @DeleteMapping("/{id}")
//    public void deletePerson(@PathVariable int id) {
//        personRepo.delete(id);
//    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        Person person = personRepo.findOne(id);

        if (person == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        personRepo.delete(person);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
