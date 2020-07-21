package com.murawski.user.service;

import com.murawski.user.model.UserCommand;
import com.murawski.user.model.entity.User;
import com.murawski.user.model.repo.UserRepsitory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Inject
    private UserRepsitory userRepsitory;


    public List<User> showAllUsers() {
        return userRepsitory.findAll();
    }

    public void addUser(User user) {
        userRepsitory.save(new User(user.getEmail(), user.getPassword(), user.getRole()));
    }

    public void deleteUser(String email) {
        User user = findByEmail(email);
        userRepsitory.delete(user);
    }

    public void changeUserPassword(UserCommand userCommand) {
        User user = findByEmail(userCommand.getEmail());
        user.setPassword(userCommand.getPassword());
    }

    public User findByEmail(String email) {
        Optional<User> user = userRepsitory.findByEmail(email);
        return user.orElseThrow(() -> new NotFoundException("User not found"));
    }
}
