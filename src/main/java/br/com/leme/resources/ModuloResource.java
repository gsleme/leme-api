package br.com.leme.resources;

import br.com.leme.services.ModuloService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/modulos")
@Produces(MediaType.APPLICATION_JSON)
public class ModuloResource {
    private final ModuloService service;

    public ModuloResource() {
        this.service = new ModuloService();
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
