package br.com.leme.services;

import br.com.leme.dao.UsuarioDAO;
import br.com.leme.dto.PrevisaoRequestDTO;
import br.com.leme.entities.Usuario;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.time.LocalDate;
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
    private final PrevisaoService service;

    /**
     * Construtor padrão (Quarkus vai usar este)
     */
    public PrevisaoScheduler() {
        this.usuarioDAO = new UsuarioDAO();
        this.service = new PrevisaoService();
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
                // 1. Calcular dias na plataforma
                long diasNaPlataforma = java.time.temporal.ChronoUnit.DAYS.between(
                        usuario.getDataCadastro(),
                        LocalDate.now()
                );

                // 2. Montar request com dados ATUALIZADOS
                PrevisaoRequestDTO request = new PrevisaoRequestDTO(
                    usuario.getArea(),
                    usuario.getAcessibilidade(),
                    usuario.getModulosConcluidos(),
                    (int) diasNaPlataforma
                );

                service.register(request, usuario.getId().toString());
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
    }
}