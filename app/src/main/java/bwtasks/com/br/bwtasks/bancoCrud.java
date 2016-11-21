package bwtasks.com.br.bwtasks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.attr.id;

public class bancoCrud {
    private static SQLiteDatabase db;

    public bancoCrud(Context context) {
        BDCore auxdb = new BDCore(context);
        db = auxdb.getWritableDatabase();
    }

    public void inserirTarefa(Task tarefa) {
        ContentValues valores = new ContentValues();
        valores.put("hora", tarefa.getHora());
        valores.put("minuto", tarefa.getMinuto());
        valores.put("dias", tarefa.getDias());
        valores.put("ativado", tarefa.getAtivado());
        valores.put("desc", tarefa.getDesc());
        valores.put("day1", tarefa.getDayOne());
        valores.put("day2", tarefa.getDayTwo());
        valores.put("day3", tarefa.getDayThr());
        valores.put("day4", tarefa.getDayFou());
        valores.put("day5", tarefa.getDayFiv());
        valores.put("day6", tarefa.getDaySix());
        valores.put("day7", tarefa.getDaySev());

        db.insert("tarefas", null, valores);
    }

    public void deletarTarefa(Task tarefa) {
        db.delete("tarefas", "_id= "+tarefa.get_id(), null);
    }

    public ArrayList<Task> returnAllTasks() {
        ArrayList<Task> list = new ArrayList<Task>();
        String[] colunas = new String[]{"_id","hora", "minuto", "dias", "ativado", "desc", "day1", "day2", "day3", "day4", "day5", "day6", "day7"};
        Cursor cursor = db.query("tarefas", colunas, null, null, null, null, "hora ASC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Task a = new Task();
                a.set_id(cursor.getInt(0));
                a.setHora(cursor.getInt(1));
                a.setMinuto(cursor.getInt(2));
                a.setDias(cursor.getString(3));
                a.setAtivado(cursor.getInt(cursor.getColumnIndex("ativado")) == 1);
                a.setDesc(cursor.getString(5));
                a.setDayOne(cursor.getInt(6));
                a.setDayTwo(cursor.getInt(7));
                a.setDayThr(cursor.getInt(8));
                a.setDayFou(cursor.getInt(9));
                a.setDayFiv(cursor.getInt(10));
                a.setDaySix(cursor.getInt(11));
                a.setDaySev(cursor.getInt(12));

                list.add(a);

            } while (cursor.moveToNext());
        }
        return (list);
    }

    public void modificarTarefa(Task t) {
        ContentValues valores = new ContentValues();
        valores.put("hora", t.getHora());
        valores.put("minuto", t.getMinuto());
        valores.put("dias", t.getDias());
        valores.put("ativado", t.getAtivado());
        valores.put("desc", t.getDesc());
        Integer id = t.get_id();

        db.update("tarefas", valores, "_id = " + id, null);

    }

    public Integer returnId(Task t) {
        ContentValues valores = new ContentValues();
        valores.put("hora", t.getHora());
        valores.put("minuto", t.getMinuto());
        valores.put("dias", t.getDias());
        valores.put("ativado", t.getAtivado());

        Cursor getId = db.rawQuery("Select _id from tarefas where hora= ? and minuto = ? and dias = ? and ativado = ?",
                new String[]{t.getHora().toString(), t.getMinuto().toString(), t.getDias(), t.getAtivado().toString()});
        getId.moveToFirst();
        return getId.getInt(0);

    }
}
