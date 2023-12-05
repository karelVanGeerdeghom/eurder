package com.example.eurder.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class Customer extends User {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;

    public Customer(String username, String password, Collection<? extends GrantedAuthority> authorities, String firstName, String lastName, String email, String phoneNumber, String address) {
        super(username, password, authorities);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
