package br.com.leme.dao;

import br.com.leme.connections.ConnectionFactory;
import br.com.leme.entities.Trilha;
import br.com.leme.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TrilhaDAO {
    private final ConnectionFactory factory;

    public TrilhaDAO() {
        this.factory = new ConnectionFactory();
    }

    public List<Trilha> findAll () {
        try {
            Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM lm_trilhas");
            ResultSet resultSet = statement.executeQuery();
            List<Trilha> trilhas = new ArrayList<>();
            while (resultSet.next()) {
                Trilha trilha = new Trilha(
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getInt(5)
                );
                trilha.setId(UUID.fromString(resultSet.getString(1)));
                trilha.setDataCriacao(resultSet.getDate(6).toLocalDate());
                trilhas.add(trilha);
            }
            statement.close();
            conn.close();
            return trilhas;

        } catch (SQLException e) {
            throw new DatabaseException("listar trilhas", e);
        }
    }

    public Trilha findById (String id) {
        try {
            Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM lm_trilhas WHERE id_trilha = ?");
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            Trilha trilha = null;
            if (resultSet.next()) {
                trilha = new Trilha (
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getInt(5)
                );
                trilha.setId(UUID.fromString(resultSet.getString(1)));
                trilha.setDataCriacao(resultSet.getDate(6).toLocalDate());
            }
            statement.close();
            conn.close();
            return trilha;

        } catch (SQLException e) {
            throw new DatabaseException("encontrar trilha", e);
        }
    }
}
