package br.edu.fateczl.time_jogadores.persistence;

import java.sql.SQLException;

public interface ITimeDAO {
    public TimeDAO open() throws SQLException;
    public void close();
}
