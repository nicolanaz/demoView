package com.example.demoview.controller;

import com.example.demoview.model.Person;
import com.example.demoview.model.PersonService;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;


@Controller
@RequestMapping("/people")
public class PersonController {
    private PersonService service;
    private RSocketRequester requester;

    public PersonController(PersonService service, RSocketRequester requester) {
        this.service = service;
        this.requester = requester;
    }

    @GetMapping
    public String getPeople(Model model) {
        model.addAttribute("listOfPeople", service.getPeople());
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
        return service.getPeopleOlder(age);
    }
}
