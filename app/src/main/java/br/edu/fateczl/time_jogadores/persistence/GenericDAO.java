package br.edu.fateczl.time_jogadores.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GenericDAO extends SQLiteOpenHelper {

    private static final String DATA_BASE = "JOGO.DB";
    private static final int DATA_BASE_VER = 1;
    private static final String CREATE_TABLE_TIME =
            "CREATE TABLE Time (" +
                    "codTime INT UNIQUE NOT NULL PRIMARY KEY, " +
                    "nomeTime VARCHAR(50), " +
                    "cidade VARCHAR(50));";

    private static final String CREATE_TABLE_JOGADOR =
            "CREATE TABLE Jogador (" +
                    "idJogador INT UNIQUE NOT NULL PRIMARY KEY, " +
                    "nomeJogador VARCHAR(150), " +
                    "dataNasc VARCHAR(10), " +
                    "altura DECIMAL(5,2), " +
                    "peso DECIMAL(5, 2), " +
                    "codTime INT NOT NULL, " +
                    "FOREIGN KEY (codTime) REFERENCES Time(codTime));";

    public GenericDAO(Context context) {
        super(context, DATA_BASE, null, DATA_BASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TIME);
        db.execSQL(CREATE_TABLE_JOGADOR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS Jogador");
            db.execSQL("DROP TABLE IF EXISTS Time");
            onCreate(db);
        }
    }
}
