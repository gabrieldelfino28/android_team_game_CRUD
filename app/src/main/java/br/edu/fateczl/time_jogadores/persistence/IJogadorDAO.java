package br.edu.fateczl.time_jogadores.persistence;

import java.sql.SQLException;

public interface IJogadorDAO {
    public JogadorDAO open() throws SQLException;
    public void close();
}
