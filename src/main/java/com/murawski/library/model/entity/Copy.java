package com.murawski.library.model.entity;

import com.murawski.user.model.entity.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Copy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String signature;

    @OneToOne
    private User user;

    @ManyToOne
    @NotNull
    private Book book;

    public Copy() {
    }

    public Copy(@NotNull @NotEmpty String signature, User user, @NotNull Book book) {
        this.signature = signature;
        this.user = user;
        this.book = book;
    }

    public Copy(@NotNull @NotEmpty String signature, @NotNull Book book) {
        this.signature = signature;
        this.book = book;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
