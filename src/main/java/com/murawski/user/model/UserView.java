package com.murawski.user.model;

import com.murawski.user.model.entity.User;

public class UserView {
    private String email;
    private Role role;


    private UserView(User user) {
        this.email = user.getEmail();
        this.role = user.getRole();
    }

    public static UserView fromEntity(User user) {
        return new UserView(user);
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}
