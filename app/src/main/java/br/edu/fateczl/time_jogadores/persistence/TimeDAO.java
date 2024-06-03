package br.edu.fateczl.time_jogadores.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.time_jogadores.model.Time;

public class TimeDAO implements ITimeDAO, ICRUD_DAO<Time> {
    private final Context context;
    private GenericDAO gDAO;
    private SQLiteDatabase db;

    public TimeDAO(Context context) {
        this.context = context;
    }

    @Override
    public TimeDAO open() throws SQLException {
        gDAO = new GenericDAO(context);
        db = gDAO.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDAO.close();
    }

    @Override
    public void insert(Time time) throws SQLException {
        ContentValues c = getValues(time);
        db.insert("Time", null, c);
    }

    @Override
    public int update(Time time) throws SQLException {
        ContentValues c = getValues(time);
        return db.update("Time",c,"codTime = " + time.getCodTime(), null);
    }

    @Override
    public void delete(Time time) throws SQLException {
        db.delete("Time","codTime = " + time.getCodTime(), null);
    }

    @SuppressLint("Range")
    @Override
    public Time findOne(Time time) throws SQLException {
        String query = "SELECT * FROM Time WHERE codTime = " + time.getCodTime();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()) {
            time.setCodTime(cursor.getInt(cursor.getColumnIndex("codTime")));
            time.setNomeTime(cursor.getString(cursor.getColumnIndex("nomeTime")));
            time.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));
        }
        cursor.close();
        return time;
    }

    @SuppressLint("Range")
    @Override
    public List<Time> findAll() throws SQLException {
        List<Time> times = new ArrayList<>();
        String query = "SELECT codTime, nomeTime, cidade FROM Time";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()) {
            Time time = new Time();
            time.setCodTime(cursor.getInt(cursor.getColumnIndex("codTime")));
            time.setNomeTime(cursor.getString(cursor.getColumnIndex("nomeTime")));
            time.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));

            times.add(time);
            cursor.moveToNext();
        }
        cursor.close();
        return times;
    }

    private ContentValues getValues(Time time) {
        ContentValues c = new ContentValues();
        c.put("codTime", time.getCodTime());
        c.put("nomeTime", time.getNomeTime());
        c.put("cidade", time.getCidade());
        return c;
    }
}
