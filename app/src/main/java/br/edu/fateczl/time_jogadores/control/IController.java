package br.edu.fateczl.time_jogadores.control;

import java.sql.SQLException;
import java.util.List;

public interface IController<T> {
    void inserir(T t) throws SQLException;

    void atualizar(T t) throws SQLException;

    void remover(T t) throws SQLException;

    T buscar(T t) throws SQLException;

    List<T> listar() throws SQLException;
}
