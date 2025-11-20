package br.com.leme.dao;

import br.com.leme.connections.ConnectionFactory;
import br.com.leme.entities.Progresso;
import br.com.leme.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProgressoDAO {
    private final ConnectionFactory factory;

    public ProgressoDAO() {
        this.factory = new ConnectionFactory();
    }

    public void register (Progresso progresso) {
        try {
            Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO lm_progressos (id_progresso, id_usuario, id_modulo, data_conclusao) VALUES (?,?,?,?)");
            statement.setString(1, progresso.getId().toString());
            statement.setString(2, progresso.getIdUsuario().toString());
            statement.setString(3, progresso.getIdModulo().toString());
            statement.setDate(4, java.sql.Date.valueOf(progresso.getDataConclusao()));
            statement.execute();
            statement.close();
            conn.close();

        } catch (SQLException e) {
            throw new DatabaseException("registrar progresso", e);
        }
    }
}
