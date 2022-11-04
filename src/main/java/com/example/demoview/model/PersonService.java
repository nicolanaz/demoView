package com.example.demoview.model;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


@Service
public class PersonService {
    private WebClient client = WebClient.create("http://localhost:8080/people");
    private PersonRepository repo;

    public PersonService(PersonRepository repo) {
        this.repo = repo;
    }

    public Iterable<Person> getPeople() {
        return repo.findAll();
    }

    public Flux<Person> getPeopleOlder(int age) {
        return client.get()
                .retrieve()
                .bodyToFlux(Person.class)
                .filter(person -> person.getAge() >= age);
    }
}
