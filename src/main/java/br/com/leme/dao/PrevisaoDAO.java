package br.com.leme.dao;

import br.com.leme.connections.ConnectionFactory;
import br.com.leme.entities.Previsao;
import br.com.leme.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PrevisaoDAO {
    private final ConnectionFactory factory;

    public PrevisaoDAO() {
        this.factory = new ConnectionFactory();
    }

    /**
     * Lista todas as previsões
     */
    public List<Previsao> findAll() {
        try {
            Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT * FROM lm_previsoes ORDER BY data_previsao DESC"
            );
            ResultSet resultSet = statement.executeQuery();
            List<Previsao> previsoes = new ArrayList<>();

            while (resultSet.next()) {
                Previsao previsao = new Previsao();
                previsao.setId(UUID.fromString(resultSet.getString("id_previsao")));
                previsao.setIdUsuario(UUID.fromString(resultSet.getString("id_usuario")));
                previsao.setTaxaSucesso(resultSet.getDouble("taxa_sucesso"));
                previsao.setCategoria(resultSet.getString("categoria"));
                previsao.setDataPrevisao(resultSet.getDate("data_previsao").toLocalDate());
                previsoes.add(previsao);
            }

            statement.close();
            conn.close();
            return previsoes;

        } catch (SQLException e) {
            throw new DatabaseException("listar previsões", e);
        }
    }

    /**
     * Busca previsão por ID
     */
    public Previsao findById(String id) {
        try {
            Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT * FROM lm_previsoes WHERE id_previsao = ?"
            );
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            Previsao previsao = null;
            if (resultSet.next()) {
                previsao = new Previsao();
                previsao.setId(UUID.fromString(resultSet.getString("id_previsao")));
                previsao.setIdUsuario(UUID.fromString(resultSet.getString("id_usuario")));
                previsao.setTaxaSucesso(resultSet.getDouble("taxa_sucesso"));
                previsao.setCategoria(resultSet.getString("categoria"));
                previsao.setDataPrevisao(resultSet.getDate("data_previsao").toLocalDate());
            }

            statement.close();
            conn.close();
            return previsao;

        } catch (SQLException e) {
            throw new DatabaseException("encontrar previsão", e);
        }
    }

    /**
     * Busca a previsão MAIS RECENTE de um usuário específico
     * IMPORTANTE: Este metodo é usado pelo Dashboard para mostrar a previsão atual do usuário
     */
    public Previsao findMaisRecentePorUsuario(UUID idUsuario) {
        try {
            Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT * FROM lm_previsoes " +
                            "WHERE id_usuario = ? " +
                            "ORDER BY data_previsao DESC " +
                            "FETCH FIRST 1 ROW ONLY"  // Oracle SQL para LIMIT 1
            );
            statement.setString(1, idUsuario.toString());
            ResultSet resultSet = statement.executeQuery();

            Previsao previsao = null;
            if (resultSet.next()) {
                previsao = new Previsao();
                previsao.setId(UUID.fromString(resultSet.getString("id_previsao")));
                previsao.setIdUsuario(UUID.fromString(resultSet.getString("id_usuario")));
                previsao.setTaxaSucesso(resultSet.getDouble("taxa_sucesso"));
                previsao.setCategoria(resultSet.getString("categoria"));
                previsao.setDataPrevisao(resultSet.getDate("data_previsao").toLocalDate());
            }

            statement.close();
            conn.close();
            return previsao;

        } catch (SQLException e) {
            throw new DatabaseException("encontrar previsão mais recente", e);
        }
    }

    /**
     * Busca todas as previsões de um usuário (histórico)
     */
    public List<Previsao> findByUsuario(UUID idUsuario) {
        try {
            Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT * FROM lm_previsoes " +
                            "WHERE id_usuario = ? " +
                            "ORDER BY data_previsao DESC"
            );
            statement.setString(1, idUsuario.toString());
            ResultSet resultSet = statement.executeQuery();

            List<Previsao> previsoes = new ArrayList<>();
            while (resultSet.next()) {
                Previsao previsao = new Previsao();
                previsao.setId(UUID.fromString(resultSet.getString("id_previsao")));
                previsao.setIdUsuario(UUID.fromString(resultSet.getString("id_usuario")));
                previsao.setTaxaSucesso(resultSet.getDouble("taxa_sucesso"));
                previsao.setCategoria(resultSet.getString("categoria"));
                previsao.setDataPrevisao(resultSet.getDate("data_previsao").toLocalDate());
                previsoes.add(previsao);
            }

            statement.close();
            conn.close();
            return previsoes;

        } catch (SQLException e) {
            throw new DatabaseException("listar previsões do usuário", e);
        }
    }

    /**
     * Salva nova previsão
     */
    public void register(Previsao previsao) {
        try {
            Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO lm_previsoes " +
                            "(id_previsao, id_usuario, taxa_sucesso, categoria, data_previsao) " +
                            "VALUES (?, ?, ?, ?, ?)"
            );
            statement.setString(1, previsao.getId().toString());
            statement.setString(2, previsao.getIdUsuario().toString());
            statement.setDouble(3, previsao.getTaxaSucesso());
            statement.setString(4, previsao.getCategoria());
            statement.setDate(5, java.sql.Date.valueOf(previsao.getDataPrevisao()));
            statement.execute();
            statement.close();
            conn.close();

        } catch (SQLException e) {
            throw new DatabaseException("registrar previsão", e);
        }
    }

    /**
     * Atualiza previsão existente
     */

    public void update(Previsao previsao) {
        try {
            Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                    "UPDATE lm_previsoes " +
                            "SET taxa_sucesso = ?, categoria = ?, data_previsao = ? " +
                            "WHERE id_previsao = ?"
            );
            statement.setDouble(1, previsao.getTaxaSucesso());
            statement.setString(2, previsao.getCategoria());
            statement.setDate(3, java.sql.Date.valueOf(previsao.getDataPrevisao()));
            statement.setString(4, previsao.getId().toString());
            statement.executeUpdate();
            statement.close();
            conn.close();

        } catch (SQLException e) {
            throw new DatabaseException("atualizar previsão", e);
        }
    }

    /**
     * Remove previsão
     */

    public void delete(String id) {
        try {
            Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                    "DELETE FROM lm_previsoes WHERE id_previsao = ?"
            );
            statement.setString(1, id);
            statement.execute();
            statement.close();
            conn.close();

        } catch (SQLException e) {
            throw new DatabaseException("remover previsão", e);
        }
    }

    /**
     * Remove todas as previsões de um usuário
     * Útil quando usuário é deletado
     */

    public void deleteByUsuario(UUID idUsuario) {
        try {
            Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                    "DELETE FROM lm_previsoes WHERE id_usuario = ?"
            );
            statement.setString(1, idUsuario.toString());
            statement.execute();
            statement.close();
            conn.close();

        } catch (SQLException e) {
            throw new DatabaseException("remover previsões do usuário", e);
        }
    }
}
