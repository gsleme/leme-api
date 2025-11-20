package br.com.leme.resources;

import br.com.leme.services.TrilhaService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("/trilhas")
@Produces(MediaType.APPLICATION_JSON)
public class TrilhaResource {
    private final TrilhaService service;

    public TrilhaResource() {
        this.service = new TrilhaService();
    }

    @GET
    public Response findAll () {
        return Response.ok(service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response findById (@PathParam("id") String id) {
        return Response.ok(service.findById(id)).build();
    }
}
