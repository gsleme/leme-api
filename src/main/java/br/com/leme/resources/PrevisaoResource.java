package br.com.leme.resources;

import br.com.leme.dto.PrevisaoRequestDTO;
import br.com.leme.dto.PrevisaoResponseDTO;
import br.com.leme.services.PrevisaoScheduler;
import br.com.leme.services.PrevisaoService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.HashMap;
import java.util.Map;

@Path("/previsoes")
@Produces(MediaType.APPLICATION_JSON)
public class PrevisaoResource {
    @Inject
    PrevisaoScheduler scheduler;
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

    @POST
    @Path("/admin/executar-job")
    public Response executarJobManual() {
        // Chamar o job manualmente
        scheduler.executarPrevisaoDiaria();
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Job de previsão executado com sucesso! Verifique os logs do servidor para ver o resultado");

        return Response.ok(resposta).build();
    }
}