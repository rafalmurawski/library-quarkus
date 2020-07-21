package com.murawski.library.service;

import com.murawski.library.model.AddCopyCommand;
import com.murawski.library.model.CopyViewForAll;
import com.murawski.library.model.CopyViewForMe;
import com.murawski.library.model.entity.Book;
import com.murawski.library.model.entity.BorrowingHistory;
import com.murawski.library.model.entity.Copy;
import com.murawski.library.model.repository.BookRepository;
import com.murawski.library.model.repository.BorrowingHistoryRepository;
import com.murawski.library.model.repository.CopyRepository;
import com.murawski.user.model.entity.User;
import com.murawski.user.model.repo.UserRepsitory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.SecurityContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Service
public class CopyService {

    private CopyRepository copyRepository;
    private BookRepository bookRepository;
    private BorrowingHistoryRepository borrowingHistoryRepository;
    private UserRepsitory userRepsitory;

    @Inject
    public CopyService(CopyRepository copyRepository, BookRepository bookRepository, BorrowingHistoryRepository borrowingHistoryRepository, UserRepsitory userRepsitory) {
        this.copyRepository = copyRepository;
        this.bookRepository = bookRepository;
        this.borrowingHistoryRepository = borrowingHistoryRepository;
        this.userRepsitory = userRepsitory;
    }

    @Transactional
    public void addCopy(AddCopyCommand addCopyCommand) {
        bookRepository.findAllByIsbn(addCopyCommand.getIsbn())
                .ifPresent(book -> addCopies(addCopyCommand.getQuantity(), book));
    }

    @Transactional
    private void addCopies(int quantity, Book book) {
        Random random = new Random();
        LocalDate date = LocalDate.now();
        IntStream.range(0, quantity)
                .forEach(notUsed ->
                        copyRepository.save(new Copy(book)));
    }

    private Copy rentBook(User loggedUser, Copy copy) {
        copy.setUser(loggedUser);
        borrowingHistoryRepository.save(new BorrowingHistory(loggedUser, copy));
        return copyRepository.save(copy);
    }


    public List<CopyViewForAll> showAllCopies() {

        Map<Book, List<Copy>> copies = copyRepository.findAll().stream()
                .collect(Collectors.groupingBy(Copy::getBook));

        return copies.entrySet().stream()
                .map(entry -> new CopyViewForAll(entry.getKey().getIsbn(),
                        entry.getKey().getTitle(),
                        entry.getValue().size(),
                        (int) entry.getValue().stream().filter(copy -> copy.getUser() == null).count()))
                .collect(toList());
    }

    public List<BorrowingHistory> getBorrowingHistoryList() {
        return borrowingHistoryRepository.findAll();
    }

    public void returnCopy(SecurityContext securityContext, Long signature) {
        User loggedUser = userRepsitory.findByEmail(securityContext.getUserPrincipal().getName())
                .orElseThrow(() -> new IllegalStateException(""));

        copyRepository.findAllById(signature)
                .map(copy -> {
                    borrowingHistoryRepository.findByUserAndCopy(loggedUser, copy)
                            .map(history -> {
                                history.setDateOfReturn(LocalDate.now());
                                return borrowingHistoryRepository.save(history);
                            }).orElseThrow(() -> new RuntimeException("History not found"));

                    copy.setUser(null);
                    return copyRepository.save(copy);
                }).orElseThrow(() -> new RuntimeException("Copy not found"));
    }

    @Transactional
    public void rentCopy(SecurityContext securityContext, String isbn) {

        User loggedUser = userRepsitory.findByEmail(securityContext.getUserPrincipal().getName())
                .orElseThrow(() -> new IllegalStateException("User must exist"));
        bookRepository.findAllByIsbn(isbn)
                .map(book -> copyRepository.findAllByBook(book))
                .flatMap(copies -> copies.stream()
                        .filter(copy -> copy.getUser() == null)
                        .findFirst())
                .map(copy -> rentBook(loggedUser, copy))
                .orElseThrow(() -> new RuntimeException("no books to rent"));
    }

    public List<CopyViewForMe> showMyCopies(SecurityContext securityContext) {
        User loggedUser = userRepsitory.findByEmail(securityContext.getUserPrincipal().getName()).
                orElseThrow(() -> new IllegalStateException("User not exists"));
        return copyRepository.findAllByUser(loggedUser).stream().map(copy -> new CopyViewForMe(copy)).collect(toList());
    }
}
