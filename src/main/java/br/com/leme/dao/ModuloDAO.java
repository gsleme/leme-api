package br.com.leme.dao;

import br.com.leme.connections.ConnectionFactory;
import br.com.leme.entities.Modulo;
import br.com.leme.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ModuloDAO {
    private final ConnectionFactory factory;

    public ModuloDAO() {
        this.factory = new ConnectionFactory();
    }

    public List<Modulo> findAll () {
        try {
            Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM lm_modulos");
            ResultSet resultSet = statement.executeQuery();
            List<Modulo> modulos = new ArrayList<>();
            while (resultSet.next()) {
                Modulo modulo = new Modulo(
                    UUID.fromString(resultSet.getString(2)),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getInt(7),
                    resultSet.getString(8)
                );
                modulo.setId(UUID.fromString(resultSet.getString(1)));
                modulos.add(modulo);
            }
            statement.close();
            conn.close();
            return modulos;

        } catch (SQLException e) {
            throw new DatabaseException("listar módulos", e);
        }
    }

    public Modulo findById (String id) {
        try {
            Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM lm_modulos WHERE id_modulo = ?");
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            Modulo modulo = null;
            if (resultSet.next()) {
                modulo = new Modulo (
                    UUID.fromString(resultSet.getString(2)),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getInt(7),
                    resultSet.getString(8)
                );
                modulo.setId(UUID.fromString(resultSet.getString(1)));
            }
            statement.close();
            conn.close();
            return modulo;

        } catch (SQLException e) {
            throw new DatabaseException("encontrar módulo", e);
        }
    }
}
