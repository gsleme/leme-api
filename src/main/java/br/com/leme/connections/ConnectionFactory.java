package br.com.leme.connections;

import br.com.leme.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public Connection getConnection () {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            return DriverManager.getConnection(
                "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL",
                "RM562999",
                "081105"
            );

        } catch (ClassNotFoundException e) {
            throw new DatabaseException("não foi possível encontrar o driver JDBC");

        } catch (SQLException e) {
            throw new DatabaseException("não foi possível estabelecer conexão com o banco");
        }
    }
}