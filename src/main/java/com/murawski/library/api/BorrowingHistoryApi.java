package com.murawski.library.api;

import com.murawski.library.model.BorrowingHistoryView;
import com.murawski.library.service.BorrowingHistoryService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/borrowingHistory")
@Produces(MediaType.APPLICATION_JSON)
public class BorrowingHistoryApi {

    private BorrowingHistoryService borrowingHistoryService;

    @Inject
    public BorrowingHistoryApi(BorrowingHistoryService borrowingHistoryService) {
        this.borrowingHistoryService = borrowingHistoryService;
    }

    @GET
    @RolesAllowed("ADMIN")
    public List<BorrowingHistoryView> getBorrowingHistoryList() {
        return borrowingHistoryService.getBorrowingHistoryList();
    }

    @GET
    @Path("/my")
    @RolesAllowed({"ADMIN", "USER"})
    public List<BorrowingHistoryView> getMyBorrowingHistoryList(@Context SecurityContext securityContext) {
        return borrowingHistoryService.getMyBorrowingHistoryList(securityContext);
    }
}
