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
import io.zipcoder.crudapp.Person;

@RestController
@RequestMapping("/people")
public class PersonController {

    private final Logger LOG = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    public PersonRepo personRepo;

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Person> createPerson(@RequestBody Person p) {
        Person created = new Person(p.getFirstName(), p.getLastName());
        personRepo.save(created);
        return new ResponseEntity<Person>(created, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Person> getPerson(@PathVariable Integer id) {
        Person byId = personRepo.findOne(id);
        if (byId == null) {
            return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Person>(byId, HttpStatus.OK);
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Iterable<Person>> getPersonList() {
        Iterable<Person> people = personRepo.findAll();
        if (people == null) {
            return new ResponseEntity<Iterable<Person>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Iterable<Person>>(people, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Person> updatePerson(@PathVariable("id") Integer id, @RequestBody Person p) {
        Person updated = personRepo.findOne(id);
        if (updated == null) {
            return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
        }
        updated.setId(p.getId());
        updated.setFirstName(p.getFirstName());
        updated.setLastName(p.getLastName());

        personRepo.save(updated);
        return new ResponseEntity<Person>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deletePerson(@PathVariable("id") Integer id) {
        Person toDelete = personRepo.findOne(id);
        if (toDelete == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        personRepo.delete(toDelete);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
