package br.com.leme.resources;

import br.com.leme.services.PrevisaoScheduler;
import br.com.leme.services.PrevisaoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Path("/previsoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PrevisaoResource {

    /**
     * IMPORTANTE: Use @Inject para injetar o Scheduler
     *
     * Isso permite que o Quarkus gerencie o Bean automaticamente.
     * Não use 'new PrevisaoScheduler()', isso causaria problemas.
     */
    @Inject
    PrevisaoScheduler scheduler;

    private final PrevisaoService previsaoService;

    public PrevisaoResource() {
        this.previsaoService = new PrevisaoService();
    }

    /**
     * Endpoint para TESTAR o job manualmente
     * USAR APENAS EM DESENVOLVIMENTO!
     * GET http://localhost:8080/api/previsoes/admin/executar-job
     */
    @POST
    @Path("/admin/executar-job")
    public Response executarJobManual() {
        try {
            // Chamar o job manualmente
            scheduler.executarPrevisaoDiaria();

            Map<String, Object> resposta = new HashMap<>();
            resposta.put("status", "ok");
            resposta.put("mensagem", "Job de previsão executado com sucesso!");
            resposta.put("info", "Verifique os logs do servidor para ver o resultado");

            return Response.ok(resposta).build();

        } catch (Exception e) {
            Map<String, Object> erro = new HashMap<>();
            erro.put("status", "erro");
            erro.put("mensagem", "Erro ao executar job: " + e.getMessage());

            return Response.status(500).entity(erro).build();
        }
    }

    /**
     * Endpoint para buscar a previsão ATUAL de um usuário
     *
     * GET /api/previsoes/usuario/{idUsuario}
     */
    @GET
    @Path("/usuario/{idUsuario}")
    public Response buscarPrevisaoAtual(@PathParam("idUsuario") String idUsuario) {
        try {
            var previsao = previsaoService.buscarPrevisaoAtual(UUID.fromString(idUsuario));

            if (previsao == null) {
                Map<String, Object> resposta = new HashMap<>();
                resposta.put("mensagem", "Nenhuma previsão encontrada para este usuário");
                resposta.put("info", "A previsão será criada no próximo job (às 3h AM)");
                return Response.status(404).entity(resposta).build();
            }

            // Criar resposta formatada
            Map<String, Object> resposta = new HashMap<>();
            resposta.put("id_previsao", previsao.getId().toString());
            resposta.put("taxa_sucesso", previsao.getTaxaSucesso());
            resposta.put("categoria", previsao.getCategoria());
            resposta.put("data_previsao", previsao.getDataPrevisao().toString());
            resposta.put("mensagem", String.format(
                    "Você tem %.0f%% de chance de concluir esta trilha!",
                    previsao.getTaxaSucesso() * 100
            ));

            return Response.ok(resposta).build();

        } catch (IllegalArgumentException e) {
            return Response.status(400)
                    .entity(Map.of("erro", "ID de usuário inválido"))
                    .build();
        } catch (Exception e) {
            return Response.status(500)
                    .entity(Map.of("erro", "Erro ao buscar previsão: " + e.getMessage()))
                    .build();
        }
    }
}