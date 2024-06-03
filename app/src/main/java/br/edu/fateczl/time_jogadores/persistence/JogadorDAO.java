package br.edu.fateczl.time_jogadores.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.time_jogadores.model.Jogador;
import br.edu.fateczl.time_jogadores.model.Time;

public class JogadorDAO implements IJogadorDAO, ICRUD_DAO<Jogador> {
    private final Context context;
    private GenericDAO gDAO;
    private SQLiteDatabase db;

    public JogadorDAO(Context context) {
        this.context = context;
    }

    @Override
    public JogadorDAO open() throws SQLException {
        gDAO = new GenericDAO(context);
        db = gDAO.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDAO.close();
    }

    @Override
    public void insert(Jogador jogador) throws SQLException {
        ContentValues content = getContentValues(jogador);
        db.insert("Jogador", null, content);
    }


    @Override
    public int update(Jogador jogador) throws SQLException {
        ContentValues content = getContentValues(jogador);
        return db.update("Jogador", content, "idJogador = " + jogador.getIdJogador(), null);
    }

    @Override
    public void delete(Jogador jogador) throws SQLException {
        db.delete("Jogador", "idJogador = " + jogador.getIdJogador(), null);
    }

    @SuppressLint("Range")
    @Override
    public Jogador findOne(Jogador jogador) throws SQLException {
        String query =
                "SELECT j.idJogador, j.nomeJogador, j.dataNasc, j.altura, j.peso, " +
                "t.codTime, t.nomeTime, t.cidade FROM Jogador j, Time t " +
                "WHERE j.codTime = t.codTime AND j.idJogador = " + jogador.getIdJogador();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()) {
            Time t = new Time();
            t.setCodTime(cursor.getInt(cursor.getColumnIndex("codTime")));
            t.setNomeTime(cursor.getString(cursor.getColumnIndex("nomeTime")));
            t.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));

            jogador.setIdJogador(cursor.getInt(cursor.getColumnIndex("idJogador")));
            jogador.setNomeJogador(cursor.getString(cursor.getColumnIndex("nomeJogador")));
            jogador.setDataNasc(cursor.getString(cursor.getColumnIndex("dataNasc")));
            jogador.setAltura(cursor.getFloat(cursor.getColumnIndex("altura")));
            jogador.setPeso(cursor.getFloat(cursor.getColumnIndex("peso")));
            jogador.setTime(t);
        }
        cursor.close();
        return jogador;
    }

    @SuppressLint("Range")
    @Override
    public List<Jogador> findAll() throws SQLException {
        List<Jogador> jogadores = new ArrayList<>();
        String query =
                "SELECT j.idJogador AS idJogador, j.nomeJogador AS nomeJogador, j.dataNasc AS dataNasc, " +
                        "j.altura AS altura, j.peso AS peso, " +
                        "t.codTime AS codTime, t.nomeTime AS nomeTime, t.cidade AS cidade FROM Jogador j, Time t " +
                        "WHERE j.codTime = t.codTime";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()) {
            Time t = new Time();
            t.setCodTime(cursor.getInt(cursor.getColumnIndex("codTime")));
            t.setNomeTime(cursor.getString(cursor.getColumnIndex("nomeTime")));
            t.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));

            Jogador jogador = new Jogador();
            jogador.setIdJogador(cursor.getInt(cursor.getColumnIndex("idJogador")));
            jogador.setNomeJogador(cursor.getString(cursor.getColumnIndex("nomeJogador")));
            jogador.setDataNasc(cursor.getString(cursor.getColumnIndex("dataNasc")));
            jogador.setAltura(cursor.getFloat(cursor.getColumnIndex("altura")));
            jogador.setPeso(cursor.getFloat(cursor.getColumnIndex("peso")));
            jogador.setTime(t);

            jogadores.add(jogador);
            cursor.moveToNext();
        }
        cursor.close();
        return jogadores;
    }

    private static ContentValues getContentValues(Jogador jogador) {
        ContentValues content = new ContentValues(); //a HashMap
        content.put("idJogador", jogador.getIdJogador());
        content.put("nomeJogador", jogador.getNomeJogador());
        content.put("dataNasc", jogador.getDataNasc());
        content.put("altura", jogador.getAltura());
        content.put("peso", jogador.getPeso());
        content.put("codTime", jogador.getTime().getCodTime());
        return content;
    }
}
