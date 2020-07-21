package com.murawski.library.model.entity;

import com.murawski.user.model.entity.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Copy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne
    private User user;

    @ManyToOne
    @NotNull
    private Book book;

    public Copy() {
    }

    public Copy(User user, @NotNull Book book) {
        this.user = user;
        this.book = book;
    }

    public Copy(@NotNull Book book) {
        this.book = book;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
