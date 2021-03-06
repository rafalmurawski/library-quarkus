package com.murawski.library.model.repository;

import com.murawski.library.model.CopyViewForMe;
import com.murawski.library.model.entity.Book;
import com.murawski.library.model.entity.Copy;
import com.murawski.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CopyRepository extends JpaRepository<Copy, Long> {

    List<Copy> findAllByBook(Book book);

    Optional<Copy> findAllById(Long id);

    List<Copy> findAllByUser(User user);

}
