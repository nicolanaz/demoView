package com.example.demoview.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    private Long id;
    private String name;
    private int age;
    private String profession;
    private boolean married;
}
