package br.edu.fateczl.time_jogadores.control;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.time_jogadores.model.Jogador;
import br.edu.fateczl.time_jogadores.persistence.JogadorDAO;

public class JogadorController implements IController<Jogador> {

    private final JogadorDAO jDAO;

    public JogadorController(JogadorDAO jDAO) {
        this.jDAO = jDAO;
    }
    @Override
    public void inserir(Jogador jogador) throws SQLException {
        if (jDAO.open() == null) {
            jDAO.open();
        }
        jDAO.insert(jogador);
        jDAO.close();
    }

    @Override
    public void atualizar(Jogador jogador) throws SQLException {
        if (jDAO.open() == null) {
            jDAO.open();
        }
        jDAO.update(jogador);
        jDAO.close();
    }

    @Override
    public void remover(Jogador jogador) throws SQLException {
        if (jDAO.open() == null) {
            jDAO.open();
        }
        jDAO.delete(jogador);
        jDAO.close();
    }

    @Override
    public Jogador buscar(Jogador jogador) throws SQLException {
        if (jDAO.open() == null) {
            jDAO.open();
        }
        jogador = jDAO.findOne(jogador);
        jDAO.close();
        return jogador;
    }

    @Override
    public List<Jogador> listar() throws SQLException {
        if (jDAO.open() == null) {
            jDAO.open();
        }
        List<Jogador> list = jDAO.findAll();
        jDAO.close();
        return list;
    }
}
