package br.com.leme.dao;

import br.com.leme.connections.ConnectionFactory;
import br.com.leme.entities.Previsao;
import br.com.leme.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PrevisaoDAO {
    private final ConnectionFactory factory;

    public PrevisaoDAO() {
        this.factory = new ConnectionFactory();
    }

    public void register (Previsao previsao) {
        try {
            Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO lm_previsoes (id_previsao, id_usuario, taxa_sucesso, categoria, data_previsao) VALUES (?,?,?,?,?)");
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
}
