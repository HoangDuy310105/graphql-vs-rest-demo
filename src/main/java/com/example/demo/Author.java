package com.example.demo;

public class Author {
    private String id;
    private String name;
    private int age;
    private String phone;
    private String email;

    public Author(String id, String name, int age, String phone, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.email = email;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
}
