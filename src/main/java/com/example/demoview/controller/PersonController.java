package com.example.demoview.controller;

import com.example.demoview.model.Person;
import com.example.demoview.model.PersonRepository;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Controller
@RequestMapping("/people")
public class PersonController {
    private PersonRepository repository;
    private RSocketRequester requester;
    private WebClient client = WebClient.create("http://localhost:8080/people");

    public PersonController(PersonRepository repository, RSocketRequester.Builder builder) {
        this.repository = repository;
        this.requester = builder.tcp("localhost", 7634);
    }

    @GetMapping
    public String getPeople(Model model) {
        model.addAttribute("listOfPeople", repository.findAll());
        return "people";
    }

    @ResponseBody
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Person> getPeopleStream() {
        return requester.route("peopleStream")
                .data("Retrieve People")
                .retrieveFlux(Person.class);
    }

    @ResponseBody
    @GetMapping("/{age}")
    public Flux<Person> getPeopleOlder(@PathVariable(name = "age") int age) {
        return client.get()
                .retrieve()
                .bodyToFlux(Person.class)
                .filter(person -> person.getAge() >= age);
    }
}
