package com.praktikum;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.javafaker.Faker;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCredentials2 {

    public static Faker faker = new Faker();
    public String email;
    public String password;
    public String name;

    public UserCredentials2() {}

    public UserCredentials2 (String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static UserCredentials2 from (User user) {
        return new UserCredentials2(user.email, user.password, user.name);
    }

    public UserCredentials2 setEmail (String email){
        this.email = email;
        return this;
    }
    public UserCredentials2 setName (String name) {
        this.name = name;
        return this;
    }
    public UserCredentials2 setPassword(String password) {
        this.password = password;
        return this;
    }

    public static UserCredentials2 getUserWithEmail (User user) {
        return new UserCredentials2().setEmail(user.email);
    }
    public static UserCredentials2 getUserWithPassword (User user) {
        return new UserCredentials2().setPassword(user.password);
    }
    public static UserCredentials2 getUserWithName (User user) {
        return new UserCredentials2().setName(user.name);
    }

    public static UserCredentials2 getUserWithRandomEmailAndPassword () {
        return new UserCredentials2().setEmail(faker.internet().emailAddress())
                .setPassword(faker.internet().password());
    }
    public static UserCredentials2 getUserWithRandomEmail () {
        return new UserCredentials2().setEmail(faker.internet().emailAddress());
    }
    public static UserCredentials2 getUserWithRandomPassword() {
        return new UserCredentials2().setPassword(faker.internet().password());
    }
    public static UserCredentials2 getUserWithRandomName () {
        return new UserCredentials2().setName(faker.name().name());
    }

    public static UserCredentials2 getUserCredentials() {
        return new UserCredentials2().setEmail(faker.internet().emailAddress())
                .setPassword(faker.internet().password())
                .setName(faker.name().name());
    }
}