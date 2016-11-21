package bwtasks.com.br.bwtasks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDCore extends SQLiteOpenHelper {
    private static final String NOME_BD = "tarefas";
    private static final int VERSAO_BD = 6;

    public BDCore(Context ctx) {
        super(ctx, NOME_BD, null, VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("create table tarefas(_id integer primary key autoincrement,hora not null, minuto integer not null, dias text, ativado integer default 1, desc text, day1 integer, day2 integer, " +
                "day3 integer, day4 integer, day5 integer, day6 integer, day7 integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int oldVersion, int newVersion) {
        bd.execSQL("drop table if exists tarefas");
        onCreate(bd);
    }
}
