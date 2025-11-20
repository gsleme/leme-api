package br.com.leme.resources;

import br.com.leme.dto.SugestaoRequestDTO;
import br.com.leme.dto.SugestaoResponseDTO;
import br.com.leme.services.SugestaoService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("/sugestoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SugestaoResource {
    private final SugestaoService service;

    public SugestaoResource() {
        this.service = new SugestaoService();
    }

    @GET
    @Path("/{id}")
    public Response findById (@PathParam("id") String id) {
        return Response.ok(service.findById(id)).build();
    }

    @POST
    @Path("/{id}")
    @Transactional
    public Response register (SugestaoRequestDTO request, @PathParam("id") String idUsuario, @Context UriInfo info) {
        SugestaoResponseDTO response = service.register(request, idUsuario);
        UriBuilder builder = info.getAbsolutePathBuilder().path(response.id());
        return Response.created(builder.build()).entity(response).build();
    }
}
