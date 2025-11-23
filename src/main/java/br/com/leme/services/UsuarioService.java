package br.com.leme.services;

import br.com.leme.dao.UsuarioDAO;
import br.com.leme.dto.CadastroRequestDTO;
import br.com.leme.dto.LoginRequestDTO;
import br.com.leme.dto.LoginResponseDTO;
import br.com.leme.dto.UsuarioResponseDTO;
import br.com.leme.entities.Usuario;
import br.com.leme.exceptions.EntityNotFoundException;
import br.com.leme.exceptions.IncorrectPasswordException;
import br.com.leme.exceptions.InvalidIdFormatException;
import br.com.leme.utils.JwtUtils;
import br.com.leme.utils.PasswordUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.leme.utils.PasswordUtils.verifyHash;

public class UsuarioService {
    private final UsuarioDAO dao;

    public UsuarioService() {
        this.dao = new UsuarioDAO();
    }

    public List<UsuarioResponseDTO> findAll () {
        return dao.findAll().stream().map(this::toResponse).toList();
    }

    public UsuarioResponseDTO findById (String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Usuario usuario = dao.findById(id);
            if (usuario == null) throw  new EntityNotFoundException("usuário");
            return toResponse(usuario);

        } catch (IllegalArgumentException e) {
            throw new InvalidIdFormatException();
        }
    }

    public UsuarioResponseDTO register (CadastroRequestDTO request) {
        String senhaHashed = PasswordUtils.generateHash(request.senha().toCharArray());
        Usuario usuario = new Usuario(
                request.nome(),
                request.username(),
                request.email(),
                senhaHashed,
                request.area(),
                request.acessibilidade(),
                request.modulosConcluidos(),
                request.xpTotal()
        );

        dao.register(usuario);
        return toResponse(usuario);
    }

    public LoginResponseDTO login (LoginRequestDTO request) {
        Optional<Usuario> usuario = dao.findAll().stream()
                .filter(user -> user.getEmail().equals(request.email())).findAny();

        if (usuario.isEmpty()) {
            throw new EntityNotFoundException();
        }

        boolean senhaCorreta = verifyHash(request.senha(), usuario.get().getSenha());

        if (!senhaCorreta) {
            throw new IncorrectPasswordException();
        }

        String token = JwtUtils.generateToken(
                usuario.get().getId().toString(),
                usuario.get().getArea(),
                usuario.get().getAcessibilidade(),
                usuario.get().getModulosConcluidos(),
                ChronoUnit.DAYS.between(usuario.get().getDataCadastro(), LocalDateTime.now())
        );

        return new LoginResponseDTO(token);
    }

    public UsuarioResponseDTO update (CadastroRequestDTO request, String id) {
        try {
            UUID uuid = UUID.fromString(id);

            if (findById(id) == null) {
                throw new EntityNotFoundException("usuário");
            }

            Usuario usuario = new Usuario(
                    request.nome(),
                    request.username(),
                    request.email(),
                    request.senha(),
                    request.area(),
                    request.acessibilidade(),
                    request.modulosConcluidos(),
                    request.xpTotal()
            );
            usuario.setId(UUID.fromString(id));

            dao.update(usuario);
            return toResponse(usuario);

        } catch (IllegalArgumentException e) {
            throw new InvalidIdFormatException();
        }
    }

    public void delete (String id) {
        try {
            UUID uuid = UUID.fromString(id);
            if (findById(id) == null) {
                throw new EntityNotFoundException("usuário");
            }
            dao.delete(id);

        } catch (IllegalArgumentException e) {
            throw new InvalidIdFormatException();
        }
    }

    private UsuarioResponseDTO toResponse (Usuario usuario) {
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