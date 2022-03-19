package com.example.demoApp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employee {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;

    private String name;
    private String dept;

    public Employee() {
    }

    public Employee(int id, String name, String dept) {
        this.id = id;
        this.name = name;
        this.dept = dept;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
