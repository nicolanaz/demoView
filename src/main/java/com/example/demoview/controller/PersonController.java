package com.example.demoview.controller;

import com.example.demoview.model.PersonRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class PersonController {
    @NonNull
    private PersonRepository repository;

    @GetMapping("/people")
    public String getPeople(Model model) {
        model.addAttribute("listOfPeople", repository.findAll());
        return "people";
    }
}
