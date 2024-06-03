package br.edu.fateczl.time_jogadores.control;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.time_jogadores.model.Time;
import br.edu.fateczl.time_jogadores.persistence.TimeDAO;

public class TimeController implements IController<Time> {

    private final TimeDAO tDAO;

    public TimeController(TimeDAO tDAO){
        this.tDAO = tDAO;
    }
    @Override
    public void inserir(Time time) throws SQLException {
        if (tDAO.open() == null) {
            tDAO.open();
        }
        tDAO.insert(time);
        tDAO.close();
    }

    @Override
    public void atualizar(Time time) throws SQLException {
        if (tDAO.open() == null) {
            tDAO.open();
        }
        tDAO.update(time);
        tDAO.close();
    }

    @Override
    public void remover(Time time) throws SQLException {
        if (tDAO.open() == null) {
            tDAO.open();
        }
        tDAO.delete(time);
        tDAO.close();
    }

    @Override
    public Time buscar(Time time) throws SQLException {
        if (tDAO.open() == null) {
            tDAO.open();
        }
        time = tDAO.findOne(time);
        tDAO.close();
        return time;
    }

    @Override
    public List<Time> listar() throws SQLException {
        if (tDAO.open() == null) {
            tDAO.open();
        }
        List<Time> list = tDAO.findAll();
        tDAO.close();
        return list;
    }
}
