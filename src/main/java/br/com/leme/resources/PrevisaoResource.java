package br.com.leme.resources;

import br.com.leme.dto.PrevisaoRequestDTO;
import br.com.leme.dto.PrevisaoResponseDTO;
import br.com.leme.services.PrevisaoService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("/previsoes")
@Produces(MediaType.APPLICATION_JSON)
public class PrevisaoResource {
    private final PrevisaoService service;

    public PrevisaoResource() {
        this.service = new PrevisaoService();
    }

    @POST
    @Path("/{id}")
    @Transactional
    public Response register (PrevisaoRequestDTO request, @PathParam("id") String idUsuario, @Context UriInfo info) {
        PrevisaoResponseDTO response = service.register(request, idUsuario);
        UriBuilder builder = info.getAbsolutePathBuilder().path(response.id());
        return Response.created(builder.build()).entity(response).build();
    }
}
