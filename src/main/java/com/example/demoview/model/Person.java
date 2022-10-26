package com.example.demoview.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Random;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    private Long id = new Random().nextLong(1000000);
    private String name;
    private int age;
    private String profession;
    private boolean married;
}
