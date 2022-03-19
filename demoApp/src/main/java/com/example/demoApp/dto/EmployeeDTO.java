package com.example.demoApp.dto;

public class EmployeeDTO {

    private String firstName;
    private String lastName;
    private String dept;

    public EmployeeDTO() {
    }

    public EmployeeDTO(String firstName, String lastName, String dept) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dept = dept;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
