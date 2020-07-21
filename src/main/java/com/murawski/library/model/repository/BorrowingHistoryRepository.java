package com.murawski.library.model.repository;

import com.murawski.library.model.entity.BorrowingHistory;
import com.murawski.library.model.entity.Copy;
import com.murawski.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowingHistoryRepository extends JpaRepository<BorrowingHistory, Long> {

    Optional<BorrowingHistory> findByUserAndCopy(User loggedUser, Copy copy);

    List<BorrowingHistory> findAllByUser(User user);
}
