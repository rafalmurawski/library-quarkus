package com.murawski.library.service;

import com.murawski.library.model.BorrowingHistoryView;
import com.murawski.library.model.repository.BorrowingHistoryRepository;
import com.murawski.user.model.entity.User;
import com.murawski.user.model.repo.UserRepsitory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowingHistoryService {

    private BorrowingHistoryRepository borrowingHistoryRepository;
    private UserRepsitory userRepsitory;

    @Inject
    public BorrowingHistoryService(BorrowingHistoryRepository borrowingHistoryRepository, UserRepsitory userRepsitory) {
        this.borrowingHistoryRepository = borrowingHistoryRepository;
        this.userRepsitory = userRepsitory;
    }

    public List<BorrowingHistoryView> getBorrowingHistoryList() {
        return borrowingHistoryRepository.findAll().stream().map(history -> new BorrowingHistoryView(history)).collect(Collectors.toList());

    }


    public List<BorrowingHistoryView> getMyBorrowingHistoryList(SecurityContext securityContext) {
        User loggedUser = userRepsitory.findByEmail(securityContext.getUserPrincipal().getName())
                .orElseThrow(() -> new IllegalStateException("User must exist"));
        return borrowingHistoryRepository.findAllByUser(loggedUser).stream().map(history -> new BorrowingHistoryView(history)).collect(Collectors.toList());
    }
}
