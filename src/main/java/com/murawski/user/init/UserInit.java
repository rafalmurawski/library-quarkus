package com.murawski.user.init;

import com.murawski.user.model.Role;
import com.murawski.user.model.entity.User;
import com.murawski.user.service.UserService;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class UserInit {

    private UserService userService;


    @Inject
    public UserInit(UserService userService) {
        this.userService = userService;
    }

    public void initUsers(@Observes StartupEvent startupEvent) {
        userService.addUser(new User("user@gmail.com", "userpass", Role.USER));
        userService.addUser(new User("admin@gmail.com", "adminpass", Role.ADMIN));
    }
}
