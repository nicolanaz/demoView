package com.example.demoview.controller;

import com.example.demoview.model.Person;
import com.example.demoview.model.PersonRepository;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

@Controller
public class PersonController {
    private PersonRepository repository;
    private RSocketRequester requester;

    public PersonController(PersonRepository repository, RSocketRequester.Builder builder) {
        this.repository = repository;
        this.requester = builder.tcp("localhost", 7634);
    }

    @GetMapping("/people")
    public String getPeople(Model model) {
        model.addAttribute("listOfPeople", repository.findAll());
        return "people";
    }

    @ResponseBody
    @GetMapping(value = "/peopleStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Person> getPeopleStream() {
        return requester.route("peopleStream")
                .data("Retrieve People")
                .retrieveFlux(Person.class);
    }
}
