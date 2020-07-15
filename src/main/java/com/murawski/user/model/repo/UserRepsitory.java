package com.murawski.user.model.repo;

import com.murawski.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepsitory extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);



}
