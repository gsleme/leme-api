package br.com.leme.services;

import br.com.leme.dao.UsuarioDAO;
import br.com.leme.entities.Usuario;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.util.List;

/**
 * Job Scheduler - Recalcula previsões a cada 24h
 * IMPORTANTE:
 * - Este é um Bean gerenciado pelo Quarkus (ApplicationScoped)
 * - Não precisa instanciar com 'new', o Quarkus gerencia automaticamente
 * - O metodo executarPrevisaoDiaria() roda automaticamente às 3h AM
 * PARA FUNCIONAR:
 * 1. Adicione a dependência no pom.xml (veja abaixo)
 * 2. Configure application.properties (veja abaixo)
 */
@ApplicationScoped
public class PrevisaoScheduler {

    private static final Logger LOG = Logger.getLogger(PrevisaoScheduler.class);

    private final UsuarioDAO usuarioDAO;
    private final PrevisaoService previsaoService;

    /**
     * Construtor padrão (Quarkus vai usar este)
     */
    public PrevisaoScheduler() {
        this.usuarioDAO = new UsuarioDAO();
        this.previsaoService = new PrevisaoService();
    }

    /**
     * Job que executa DIARIAMENTE às 3h da manhã
     * Cron: "0 0 3 * * ?" = TodoDia às 3:00 AM
     * Para testar, mude temporariamente para:
     * @Scheduled (cron = "0 * /5 * * * ?")  // A cada 5 minutos
            */
    @Scheduled(cron = "0 0 3 * * ?")
    public void executarPrevisaoDiaria() {
        LOG.info("🤖 ========================================");
        LOG.info("🤖 Iniciando Job de Previsão Diária");
        LOG.info("🤖 ========================================");

        try {
            // 1. Buscar todos os usuários
            List<Usuario> usuarios = usuarioDAO.findAll();
            LOG.info(String.format("📊 Total de usuários: %d", usuarios.size()));

            if (usuarios.isEmpty()) {
                LOG.warn("⚠️ Nenhum usuário encontrado. Job finalizado.");
                return;
            }

            int sucessos = 0;
            int falhas = 0;
            int pulos = 0;

            // 2. Processar cada usuário
            for (Usuario usuario : usuarios) {
                try {
                    // Validar dados obrigatórios
                    if (usuario.getArea() == null || usuario.getAcessibilidade() == null) {
                        pulos++;
                        LOG.warn(String.format("⏭️ Pulando usuário %s (dados incompletos)",
                                usuario.getId()));
                        continue;
                    }

                    // Atualizar previsão
                    previsaoService.atualizarPrevisao(usuario.getId());
                    sucessos++;

                } catch (Exception e) {
                    falhas++;
                    LOG.error(String.format("❌ Erro ao processar usuário %s: %s",
                            usuario.getId(), e.getMessage()), e);
                }
            }

            // 3. Relatório final
            LOG.info("🤖 ========================================");
            LOG.info(String.format("🎯 Job Concluído - Total: %d usuários", usuarios.size()));
            LOG.info(String.format("✅ Sucessos: %d", sucessos));
            LOG.info(String.format("❌ Falhas: %d", falhas));
            LOG.info(String.format("⏭️ Pulados: %d", pulos));
            LOG.info("🤖 ========================================");

        } catch (Exception e) {
            LOG.error("❌ Erro crítico no Job de Previsão", e);
        }
    }
}


/**
 * =============================================
 * CONFIGURAÇÃO NECESSÁRIA
 * =============================================
 * 1. pom.xml - Adicionar dependência do Scheduler:
 * <dependency>
 *   <groupId>io.quarkus</groupId>
 *   <artifactId>quarkus-scheduler</artifactId>
 * </dependency>
 * 2. application.properties - Habilitar scheduler:
 * # Habilitar scheduler
 * quarkus.scheduler.enabled=true
 * # Timezone (opcional)
 * quarkus.scheduler.timezone=America/Sao_Paulo
 * # Para desabilitar em desenvolvimento (opcional)
 * # %dev.quarkus.scheduler.enabled=false
 * =============================================
 * COMO TESTAR
 * =============================================
 * Opção 1: Criar endpoint de teste em PrevisaoResource.java
 *
 * @Inject
 * PrevisaoScheduler scheduler;
 * @POST
 * @Path("/admin/executar-job")
 * public Response executarJobManual() {
 *     scheduler.executarPrevisaoDiaria();
 *     return Response.ok("Job executado").build();
 * }
 * Depois testar:
 * curl -X POST http://localhost:8080/api/previsoes/admin/executar-job
 * Opção 2: Mudar cron temporariamente para testar
 *
 * @Scheduled(cron = "0 * /1 * * * ?")  // A cada 1 minuto
        * Depois voltar para:
        * @Scheduled(cron = "0 0 3 * * ?")  // TodoDia às 3h
 */