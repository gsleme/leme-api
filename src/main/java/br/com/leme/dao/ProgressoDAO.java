package br.com.leme.dao;

import br.com.leme.connections.ConnectionFactory;
import br.com.leme.entities.Progresso;
import br.com.leme.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ProgressoDAO {
    private final ConnectionFactory factory;

    public ProgressoDAO() {
        this.factory = new ConnectionFactory();
    }

    public Progresso findById (String id) {
        try {
            Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM lm_progressos WHERE id_usuario = ? ORDER BY data_conclusao DESC FETCH FIRST 1 ROW ONLY");
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            Progresso progresso = null;

            if (resultSet.next()) {
                progresso = new Progresso (
                        UUID.fromString(resultSet.getString(2)),
                        UUID.fromString(resultSet.getString(3))
                );
                progresso.setId(UUID.fromString(resultSet.getString(1)));
                progresso.setDataConclusao(resultSet.getTimestamp(4).toLocalDateTime());
            }
            statement.close();
            conn.close();
            return progresso;

        } catch (SQLException e) {
            throw new DatabaseException("encontrar sugestão", e);
        }
    }

    public void register (Progresso progresso) {
        try {
            Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO lm_progressos (id_progresso, id_usuario, id_modulo, data_conclusao) VALUES (?,?,?,?)");
            statement.setString(1, progresso.getId().toString());
            statement.setString(2, progresso.getIdUsuario().toString());
            statement.setString(3, progresso.getIdModulo().toString());
            statement.setTimestamp(4, java.sql.Timestamp.valueOf(progresso.getDataConclusao()));
            statement.execute();
            statement.close();
            conn.close();

        } catch (SQLException e) {
            throw new DatabaseException("registrar progresso", e);
        }
    }
}
