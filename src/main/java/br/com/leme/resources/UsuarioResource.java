package br.com.leme.resources;

import br.com.leme.dto.*;
import br.com.leme.services.UsuarioService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {
    private final UsuarioService service;

    public UsuarioResource() {
        this.service = new UsuarioService();
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

    @POST
    @Path("/cadastrar")
    public Response register (CadastroRequestDTO request, @Context UriInfo info) {
        UsuarioResponseDTO response = service.register(request);
        UriBuilder builder = info.getAbsolutePathBuilder().path(response.id());
        return Response.created(builder.build()).entity(response).build();
    }

    @POST
    @Path("/login")
    public Response login (LoginRequestDTO request) {
        return Response.ok(service.login(request)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update (CadastroRequestDTO request, @PathParam("id") String id) {
        return Response.ok(service.update(request, id)).build();
    }
    @DELETE
    @Path("/{id}")
    public Response delete (@PathParam("id") String id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
