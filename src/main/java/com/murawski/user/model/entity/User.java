package com.murawski.user.model.entity;


import com.murawski.user.model.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"email", "role"}))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    private String email;


    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public User() {
    }

    public User(@Email String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(@Email String email, String password) {
        this.email = email;
        this.password = password;
        this.role = Role.USER;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

