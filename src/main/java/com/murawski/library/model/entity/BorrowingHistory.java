package com.murawski.library.model.entity;

import com.murawski.user.model.entity.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class BorrowingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @NotNull
    private User user;

    @OneToOne
    @NotNull
    private Copy copy;

    @NotNull
    private LocalDate borrowingDate;

    private LocalDate dateOfReturn;

    public BorrowingHistory() {
    }

    public BorrowingHistory(@NotNull User user, @NotNull Copy copy) {
        this.user = user;
        this.copy = copy;
        this.borrowingDate = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Copy getCopy() {
        return copy;
    }

    public LocalDate getBorrowingDate() {
        return borrowingDate;
    }

    public LocalDate getDateOfReturn() {
        return dateOfReturn;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCopy(Copy copy) {
        this.copy = copy;
    }

    public void setBorrowingDate(LocalDate borrowingDate) {
        this.borrowingDate = borrowingDate;
    }

    public void setDateOfReturn(LocalDate dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }
}
