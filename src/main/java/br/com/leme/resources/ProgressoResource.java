package br.com.leme.resources;

import br.com.leme.dto.ProgressoRequestDTO;
import br.com.leme.dto.ProgressoResponseDTO;
import br.com.leme.services.ProgressoService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("/progressos")
@Produces(MediaType.APPLICATION_JSON)
public class ProgressoResource {
    private final ProgressoService service;

    public ProgressoResource() {
        this.service = new ProgressoService();
    }

    @POST
    @Transactional
    public Response register (ProgressoRequestDTO request, @Context UriInfo info) {
        ProgressoResponseDTO response = service.register(request);
        UriBuilder builder = info.getAbsolutePathBuilder().path(response.id());
        return Response.created(builder.build()).entity(response).build();
    }
}
