package br.com.leme.services;

import br.com.leme.dao.UsuarioDAO;
import br.com.leme.dto.*;
import br.com.leme.entities.Usuario;
import org.jboss.logging.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UsuarioService {

    private static final Logger LOG = Logger.getLogger(UsuarioService.class);

    private final UsuarioDAO dao;
    private final PrevisaoService previsaoService;

    public UsuarioService() {
        this.dao = new UsuarioDAO();
        this.previsaoService = new PrevisaoService();
    }

    public List<UsuarioResponseDTO> findAll() {
        return dao.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO findById(String id) {
        Usuario usuario = dao.findById(id);
        return toResponseDTO(usuario);
    }

    /**
     * Registra novo usuário E cria previsão inicial
     * MUDANÇA: Agora também chama PrevisaoService para criar previsão inicial
     */
    public UsuarioResponseDTO register(CadastroRequestDTO request) {
        LOG.info("📝 Cadastrando novo usuário: " + request.email());

        try {
            // 1. Criar entidade Usuario
            Usuario usuario = new Usuario(
                    request.nome(),
                    request.username(),
                    request.email(),
                    request.senha(),
                    request.area(),
                    request.acessibilidade(),
                    0,  // modulosConcluidos = 0
                    0   // xpTotal = 0
            );
            usuario.setId(UUID.randomUUID());
            usuario.setDataCadastro(LocalDate.now());

            // 2. Salvar no banco
            dao.register(usuario);
            LOG.info("✅ Usuário salvo no banco: " + usuario.getId());

            // 3. NOVO: Criar previsão inicial
            try {
                PrevisaoAPIResponseDTO previsao = previsaoService.criarPrevisaoInicial(usuario.getId());
                LOG.info(String.format("✅ Previsão inicial criada: %.1f%% (%s)",
                        previsao.taxaSucesso() * 100,
                        previsao.categoria()));
            } catch (Exception e) {
                // Se falhar, apenas loga (não bloqueia cadastro)
                LOG.error("⚠️ Erro ao criar previsão inicial (usuário foi cadastrado): " + e.getMessage());
            }

            // 4. Retornar DTO
            return toResponseDTO(usuario);

        } catch (Exception e) {
            LOG.error("❌ Erro ao registrar usuário: " + e.getMessage(), e);
            throw new RuntimeException("Erro ao registrar usuário: " + e.getMessage());
        }
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        // Sua lógica de login aqui
        // ...
        return null; // Placeholder
    }

    public UsuarioResponseDTO update(CadastroRequestDTO request, String id) {
        Usuario usuario = dao.findById(id);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        usuario.setNome(request.nome());
        usuario.setUsername(request.username());
        usuario.setEmail(request.email());
        usuario.setSenha(request.senha());
        usuario.setArea(request.area());
        usuario.setAcessibilidade(request.acessibilidade());

        dao.update(usuario);
        return toResponseDTO(usuario);
    }

    public void delete(String id) {
        dao.delete(id);
    }

    // Metodo auxiliar para converter Entity → DTO
    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId().toString(),
                usuario.getNome(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getArea(),
                usuario.getAcessibilidade(),
                usuario.getModulosConcluidos(),
                usuario.getXpTotal(),
                usuario.getDataCadastro()
        );
    }
}