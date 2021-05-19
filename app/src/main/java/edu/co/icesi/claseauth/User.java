package edu.co.icesi.claseauth;

public class User {

    public String id;
    public String name;
    public String email;
    public String city;

    public User(){}

    public User(String id, String name, String email, String city) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.city = city;
    }
}
