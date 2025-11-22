package br.com.leme.dao;

import br.com.leme.connections.ConnectionFactory;
import br.com.leme.entities.Sugestao;
import br.com.leme.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SugestaoDAO {
    private final ConnectionFactory factory;

    public SugestaoDAO() {
        this.factory = new ConnectionFactory();
    }

    public Sugestao findById (String id) {
        try {
            Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM lm_sugestoes WHERE id_sugestao = ?");
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            Sugestao sugestao = null;

            if (resultSet.next()) {
                sugestao = new Sugestao (
                    UUID.fromString(resultSet.getString(2)),
                    UUID.fromString(resultSet.getString(3))
                );
                sugestao.setId(UUID.fromString(resultSet.getString(1)));
                sugestao.setDataSugestao(resultSet.getDate(4).toLocalDate());
            }
            statement.close();
            conn.close();
            return sugestao;

        } catch (SQLException e) {
            throw new DatabaseException("encontrar sugestão", e);
        }
    }

    public void register (Sugestao sugestao) {
        try {
            Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO lm_sugestoes (id_sugestao, id_usuario, nome_trilha_sugerida, data_sugestao) VALUES (?,?,?,?)");
            statement.setString(1, sugestao.getId().toString());
            statement.setString(2, sugestao.getIdUsuario().toString());
            statement.setString(3, sugestao.getIdTrilha().toString());
            statement.setDate(4, java.sql.Date.valueOf(sugestao.getDataSugestao()));
            statement.execute();
            statement.close();
            conn.close();

        } catch (SQLException e) {
            throw new DatabaseException("registrar sugestão", e);
        }
    }
}
