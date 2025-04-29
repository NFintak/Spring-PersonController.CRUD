package io.zipcoder.crudapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody Person person) {
        LOG.info("creating new person {}", person);
        if (personRepo.exists(person.getId())) {
            LOG.info("person with id {} already exists", person.getId());
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        personRepo.save(person);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

//    @GetMapping("/{id}")
//    public Person getPerson(@PathVariable int id) {
//        return personRepo.findOne(id);
//    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Person> getById(@PathVariable int id) {
        LOG.info("Getting by id");
        Person personById = personRepo.findOne(id);
        if (personById == null) {
            LOG.info("person with id not found");
            return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Person>(personById, HttpStatus.OK);
    }

//    @GetMapping
//    public Iterable<Person> getPersonList() {
//        return personRepo.findAll();
//    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Person>> getAll() {
        LOG.info("Getting person list");
        Iterable<Person> people = personRepo.findAll();
        if (people == null) {
            LOG.info("empty repo");
            return new ResponseEntity<Iterable<Person>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Iterable<Person>>(people, HttpStatus.OK);
    }

//    @PostMapping("/{id}")
//    public Person updatePerson(@RequestBody Person p) {
//        return personRepo.save(p);
//    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Person> updateById(@PathVariable("id") int id, @RequestBody Person person) {
        LOG.info("updating person - {}", person);
        Person current = personRepo.findOne(id);

        if (current == null) {
            LOG.info("person with id {} not found", id);
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

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        LOG.info("deleting person with id - {}", id);
        Person person = personRepo.findOne(id);

        if (person == null) {
            LOG.info("unable to delete, person with id {} not found", id);
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        personRepo.delete(person);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
