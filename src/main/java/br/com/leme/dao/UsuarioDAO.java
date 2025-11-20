package br.com.leme.dao;

import br.com.leme.connections.ConnectionFactory;
import br.com.leme.entities.Usuario;
import br.com.leme.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UsuarioDAO {
    private final ConnectionFactory factory;

    public UsuarioDAO() {
        this.factory = new ConnectionFactory();
    }

    public List<Usuario> findAll () {
        try {
            Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM lm_usuarios");
            ResultSet resultSet = statement.executeQuery();
            List<Usuario> usuarios = new ArrayList<>();
            while (resultSet.next()) {
                Usuario usuario = new Usuario(
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getInt(8),
                    resultSet.getInt(9)
                );
                usuario.setId(UUID.fromString(resultSet.getString(1)));
                usuario.setDataCadastro(resultSet.getDate(10).toLocalDate());
                usuarios.add(usuario);
            }
            statement.close();
            conn.close();
            return usuarios;

        } catch (SQLException e) {
            throw new DatabaseException("listar usuários", e);
        }
    }

    public Usuario findById (String id) {
        try {
            Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM lm_usuarios WHERE id_usuario = ?");
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            Usuario usuario = new Usuario();
            if (resultSet.next()) {
                usuario = new Usuario (
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getInt(8),
                    resultSet.getInt(9)
                );
                usuario.setId(UUID.fromString(resultSet.getString(1)));
                usuario.setDataCadastro(resultSet.getDate(10).toLocalDate());
            }
            statement.close();
            conn.close();
            return usuario;

        } catch (SQLException e) {
            throw new DatabaseException("encontrar usuário", e);
        }
    }

    public void register (Usuario usuario) {
        try {
            Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO lm_usuarios (id_usuario, nome, username, email, senha, area, acessibilidade, modulos_concluidos, xp_total, data_cadastro) VALUES (?,?,?,?,?,?,?,?,?,?)");
            statement.setString(1, usuario.getId().toString());
            statement.setString(2, usuario.getNome());
            statement.setString(3, usuario.getUsername());
            statement.setString(4, usuario.getEmail());
            statement.setString(5, usuario.getSenha());
            statement.setString(6, usuario.getArea());
            statement.setString(7, usuario.getAcessibilidade());
            statement.setInt(8, usuario.getModulosConcluidos());
            statement.setInt(9, usuario.getXpTotal());
            statement.setDate(10, java.sql.Date.valueOf(usuario.getDataCadastro()));
            statement.execute();
            statement.close();
            conn.close();

        } catch (SQLException e) {
            throw new DatabaseException("registrar usuário", e);
        }
    }

    public void update (Usuario usuario) {
        try {
            Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement("UPDATE lm_usuarios SET nome = ?, username = ?, email = ?, senha = ?, area = ?, acessibilidade = ?, modulos_concluidos = ?, xp_total = ?, data_cadastro = ? WHERE id_usuario = ?");
            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getUsername());
            statement.setString(3, usuario.getEmail());
            statement.setString(4, usuario.getSenha());
            statement.setString(5, usuario.getArea());
            statement.setString(6, usuario.getAcessibilidade());
            statement.setInt(7, usuario.getModulosConcluidos());
            statement.setInt(8, usuario.getXpTotal());
            statement.setDate(9, java.sql.Date.valueOf(usuario.getDataCadastro()));
            statement.setString(10, usuario.getId().toString());
            statement.executeUpdate();
            statement.close();
            conn.close();

        } catch (SQLException e) {
            throw new DatabaseException("atualizar usuário", e);
        }
    }

    public void delete (String id) {
        try {
            Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement("DELETE FROM lm_usuarios WHERE id_usuario = ?");
            statement.setString(1, id);
            statement.execute();
            statement.close();
            conn.close();

        } catch (SQLException e) {
            throw new DatabaseException("remover usuário", e);
        }
    }
}
