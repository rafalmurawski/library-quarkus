package com.murawski.library.api;

import com.murawski.library.model.AddCopyCommand;
import com.murawski.library.model.CopyViewForAll;
import com.murawski.library.model.CopyViewForMe;
import com.murawski.library.service.CopyService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/copies")
@Produces(MediaType.APPLICATION_JSON)
public class CopyApi {

    private CopyService copyService;

    @Inject
    public CopyApi(CopyService copyService) {
        this.copyService = copyService;
    }

    @POST
    @RolesAllowed("ADMIN")
    public void addCopy(AddCopyCommand addCopyCommand) {
        copyService.addCopy(addCopyCommand);
    }

    @GET
    @RolesAllowed({"USER", "ADMIN"})
    public List<CopyViewForAll> showAllCopies() {
        return copyService.showAllCopies();
    }

    @GET
    @Path("/my")
    @RolesAllowed({"USER", "ADMIN"})
    public List<CopyViewForMe> showMyCopies(@Context SecurityContext securityContext) {
        return copyService.showMyCopies(securityContext);
    }

    @PATCH
    @Path("/rent")
    @RolesAllowed({"ADMIN", "USER"})
    public void rentCopy(@Context SecurityContext securityContext, @QueryParam("isbn") String isbn) {
        copyService.rentCopy(securityContext, isbn);
    }

    @PATCH
    @Path("/return/{signature}")
    @RolesAllowed({"USER", "ADMIN"})
    public void returnCopy(@Context SecurityContext securityContext, @PathParam("signature") Long signature) {
        copyService.returnCopy(securityContext, signature);
    }


}
