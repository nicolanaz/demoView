package com.example.demoview.model;

import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {
    Person findPersonByName(String name);
}
