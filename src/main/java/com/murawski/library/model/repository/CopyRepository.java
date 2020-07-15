package com.murawski.library.model.repository;

import com.murawski.library.model.entity.Book;
import com.murawski.library.model.entity.Copy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CopyRepository extends JpaRepository<Copy, Long> {

    List<Copy> findAllByBook(Book book);

}
