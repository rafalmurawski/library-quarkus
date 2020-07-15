package com.murawski.user.api;

import com.murawski.user.model.Role;
import com.murawski.user.model.UserCommand;
import com.murawski.user.model.UserView;
import com.murawski.user.model.entity.User;
import com.murawski.user.service.UserService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserApi {

    private UserService userService;

    @Inject
    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    public List<UserView> getUserList() {
        return userService.showAllUsers().stream().map(UserView::fromEntity).collect(Collectors.toList());
    }


    @POST
    @RolesAllowed("ADMIN")
    @Transactional
    public void addUser(UserCommand userCommand) {
        userService.addUser(new User(userCommand.getEmail(), userCommand.getPassword(), Role.USER));
    }

    @DELETE
    @Transactional
    @RolesAllowed("ADMIN")
    public void deleteUser(@QueryParam("email") String email) {
        System.out.println(email);
        userService.deleteUser(email);
    }

    @PATCH
    @Transactional
    @RolesAllowed("ADMIN")
    public void changeUserPassword(UserCommand userCommand) {
        userService.changeUserPassword(userCommand);
    }

    @Inject
    public void addFirstUsers() {
        userService.addUser(new User("user@gmail.com", "userpass", Role.USER));
        userService.addUser(new User("admin@gmail.com", "adminpass", Role.ADMIN));
    }


}