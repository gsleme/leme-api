package br.com.leme.services;

import br.com.leme.clients.PrevisaoAPIClient;
import br.com.leme.dao.PrevisaoDAO;
import br.com.leme.dao.UsuarioDAO;
import br.com.leme.dto.PrevisaoRequestDTO;
import br.com.leme.dto.PrevisaoAPIResponseDTO;
import br.com.leme.entities.Previsao;
import br.com.leme.entities.Usuario;
import org.jboss.logging.Logger;

import java.time.LocalDate;
import java.util.UUID;

/**
 * PrevisaoService - Gerencia previsões de sucesso
 * Responsabilidades:
 * 1. Criar previsão inicial no cadastro
 * 2. Buscar previsão mais recente (para dashboard)
 * 3. Atualizar previsões (usado pelo Job Scheduler)
 */
public class PrevisaoService {

    private static final Logger LOG = Logger.getLogger(PrevisaoService.class);

    private final PrevisaoDAO previsaoDAO;
    private final UsuarioDAO usuarioDAO;

    public PrevisaoService() {
        PrevisaoAPIClient previsaoAPIClient = new PrevisaoAPIClient();
        this.previsaoDAO = new PrevisaoDAO();
        this.usuarioDAO = new UsuarioDAO();
    }

    /**
     * Cria previsão INICIAL quando usuário se cadastra
     * Chamado por: UsuarioResource.cadastrar()
     *
     * @param idUsuario UUID do usuário recém-cadastrado
     * @return PrevisaoAPIResponseDTO com taxa e categoria
     */
    public PrevisaoAPIResponseDTO criarPrevisaoInicial(UUID idUsuario) {
        LOG.info("🔮 Criando previsão inicial para usuário: " + idUsuario);

        try {
            // 1. Buscar usuário
            Usuario usuario = usuarioDAO.findById(idUsuario.toString());
            if (usuario == null) {
                throw new RuntimeException("Usuário não encontrado: " + idUsuario);
            }

            // 2. Montar request (dados iniciais: 0 módulos, 0 dias)
            PrevisaoRequestDTO request = new PrevisaoRequestDTO(
                    usuario.getArea(),
                    usuario.getAcessibilidade(),
                    0,  // Novo usuário, 0 módulos concluídos
                    0   // Dia 0 na plataforma
            );

            // 3. Chamar API Python
            PrevisaoAPIResponseDTO response = PrevisaoAPIClient.getPrevisao(request);

            // 4. Salvar no banco
            Previsao previsao = new Previsao();
            previsao.setId(UUID.randomUUID());
            previsao.setIdUsuario(idUsuario);
            previsao.setTaxaSucesso(response.taxaSucesso());
            previsao.setCategoria(response.categoria());
            previsao.setDataPrevisao(LocalDate.now());

            previsaoDAO.register(previsao);

            LOG.info(String.format("✅ Previsão inicial salva: %.1f%% (%s)",
                    response.taxaSucesso() * 100,
                    response.categoria()));

            return response;

        } catch (Exception e) {
            LOG.error("❌ Erro ao criar previsão inicial: " + e.getMessage(), e);
            throw new RuntimeException("Erro ao criar previsão: " + e.getMessage());
        }
    }

    /**
     * Busca previsão MAIS RECENTE de um usuário
     * Chamado por: Dashboard do frontend
     *
     * @param idUsuario UUID do usuário
     * @return Previsao mais recente ou null se não houver
     */
    public Previsao buscarPrevisaoAtual(UUID idUsuario) {
        LOG.info("📊 Buscando previsão atual para usuário: " + idUsuario);
        return previsaoDAO.findMaisRecentePorUsuario(idUsuario);
    }

    /**
     * Atualiza previsão de um usuário
     * Chamado por: PrevisaoScheduler (Job de 24h)
     *
     * @param idUsuario UUID do usuário
     * @return PrevisaoAPIResponseDTO atualizado
     */
    public PrevisaoAPIResponseDTO atualizarPrevisao(UUID idUsuario) {
        LOG.info("🔄 Atualizando previsão para usuário: " + idUsuario);

        try {
            // 1. Buscar usuário
            Usuario usuario = usuarioDAO.findById(idUsuario.toString());
            if (usuario == null) {
                throw new RuntimeException("Usuário não encontrado: " + idUsuario);
            }

            // 2. Calcular dias na plataforma
            long diasNaPlataforma = java.time.temporal.ChronoUnit.DAYS.between(
                    usuario.getDataCadastro(),
                    LocalDate.now()
            );

            // 3. Montar request com dados ATUALIZADOS
            PrevisaoRequestDTO request = new PrevisaoRequestDTO(
                    usuario.getArea(),
                    usuario.getAcessibilidade(),
                    usuario.getModulosConcluidos(),
                    (int) diasNaPlataforma
            );

            // 4. Chamar API Python
            PrevisaoAPIResponseDTO response = PrevisaoAPIClient.getPrevisao(request);

            // 5. Salvar nova previsão no banco
            Previsao previsao = new Previsao();
            previsao.setId(UUID.randomUUID());
            previsao.setIdUsuario(idUsuario);
            previsao.setTaxaSucesso(response.taxaSucesso());
            previsao.setCategoria(response.categoria());
            previsao.setDataPrevisao(LocalDate.now());

            previsaoDAO.register(previsao);

            LOG.info(String.format("✅ Previsão atualizada: %.1f%% (%s)",
                    response.taxaSucesso() * 100,
                    response.categoria()));

            return response;

        } catch (Exception e) {
            LOG.error("❌ Erro ao atualizar previsão: " + e.getMessage(), e);
            throw new RuntimeException("Erro ao atualizar previsão: " + e.getMessage());
        }
    }
}