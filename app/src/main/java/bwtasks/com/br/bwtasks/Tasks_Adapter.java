package bwtasks.com.br.bwtasks;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.List;

/**
 * Created by gapan on 15/11/2016.
 */

public class Tasks_Adapter extends BaseAdapter {
    private Context context;
    private List<Task> tarefas;

    public Tasks_Adapter(List<Task> tarefas, Context context) {
        super();
        this.tarefas = tarefas;
        this.context = context;
    }


    @Override
    public int getCount() {
        return tarefas.size();
    }

    @Override
    public Object getItem(int position) {
        return tarefas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tarefas.get(position).getHora();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tasks_adapter, parent, false);

        //Tarefa
        Task a = tarefas.get(position);

        //TextView
        TextView t = (TextView) view.findViewById(R.id.horario);
        TextView x = (TextView) view.findViewById(R.id.dias);
        TextView desc = (TextView) view.findViewById(R.id.marcadorEditText);

        //Buttons
        final SwitchCompat chkbx = (SwitchCompat) view.findViewById(R.id.ativada);
        ImageButton trashCan = (ImageButton) view.findViewById(R.id.trashcan);
        final bancoCrud bancoCrud = new bancoCrud(context);

        //Botoes Listener
        trashCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bancoCrud.deletarTarefa(tarefas.get(position));
                Toast.makeText(context, "Tarefa deletada", Toast.LENGTH_SHORT).show();
                tarefas.remove(tarefas.get(position));
                notifyDataSetChanged();
            }
        });

        chkbx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
                if(chkbx.isChecked()) {
                    Task task = tarefas.get(position);
                    tarefas.get(position).setAtivado(true);
                    bancoCrud.modificarTarefa(task);

                    //Agendar Alarme
                    ArrayList<Integer> w = new ArrayList<Integer>();
                    w.add(task.getDayOne());
                    w.add(task.getDayTwo());
                    w.add(task.getDayThr());
                    w.add(task.getDayFou());
                    w.add(task.getDayFiv());
                    w.add(task.getDaySix());
                    w.add(task.getDaySev());

                    for (Integer i = 0; i< 7;i++){
                        Integer id = w.get(i);
                        if(!(id == 0)){
                            Intent myIntent = new Intent(context, Alarm_Receiver.class);
                            myIntent.putExtra("extra","alarmOn");
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.DAY_OF_WEEK, i + 1);
                            calendar.set(Calendar.HOUR_OF_DAY, tarefas.get(position).getHora());
                            calendar.set(Calendar.MINUTE, tarefas.get(position).getMinuto());
                            calendar.set(Calendar.SECOND, 0);
                            if(calendar.before(Calendar.getInstance())) {
                                calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 7);
                            }
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id,  myIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
                            Log.e("Agendado", id.toString());
                        }
                    }


                }
                else {
                    Task task = tarefas.get(position);
                    task.setAtivado(false);
                    bancoCrud.modificarTarefa(task);
                    ArrayList<Integer> w = new ArrayList<Integer>();
                    w.add(task.getDayOne());
                    w.add(task.getDayTwo());
                    w.add(task.getDayThr());
                    w.add(task.getDayFou());
                    w.add(task.getDayFiv());
                    w.add(task.getDaySix());
                    w.add(task.getDaySev());
                    for (Integer i = 0; i< 7;i++){
                        Integer id = w.get(i);
                        if(!(id == 0)){
                            Intent myIntent = new Intent(context, Alarm_Receiver.class);
                            myIntent.putExtra("extra","alarmOff");
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,id,  myIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                            alarmManager.cancel(pendingIntent);
                            context.sendBroadcast(myIntent);

                            Log.e("Cancelado", id.toString());
                        }
                    }
                }
            }
        });

        //Layout
        FrameLayout background = (FrameLayout) view.findViewById(R.id.lay);
        background.setBackgroundColor(Color.parseColor("#FFE0B2"));

        //Formatando Valores de Horario
        String horario = String.format("%02d", a.getHora()) + ":" + String.format("%02d", a.getMinuto());

        //Settando Tela
        t.setText(horario);

        if (a.getDesc()!= null ||a.getDesc() != "") {
            desc.setText(a.getDesc());
        }

        if (a.getAtivado() == true) {
            chkbx.setChecked(true);
        } else {
            chkbx.setChecked(false);
            background.setBackgroundColor(Color.parseColor("#90FFE0B2"));
        }

        //Domingo = 1

        String dias = null;
        if (a.getDias().contains("0"))
            dias = "Dom";
        if (a.getDias().contains("1"))
            if (dias == null)
                dias = "Seg";
            else
                dias = dias + ", Seg";
        if (a.getDias().contains("2"))
            if (dias == null)
                dias = "Ter";
            else
                dias = dias + ", Ter";
        if (a.getDias().contains("3"))
            if (dias == null)
                dias = "Qua";
            else
                dias = dias + ", Qua";
        if (a.getDias().contains("4"))
            if (dias == null)
                dias = "Qui";
            else
                dias = dias + ", Qui";
        if (a.getDias().contains("5"))
            if (dias == null)
                dias = "Sex";
            else
                dias = dias + ", Sex";
        if (a.getDias().contains("6"))
            if (dias == null)
                dias = "Sab";
            else
                dias = dias + ", Sab";
        if (dias.equals("Dom, Seg, Ter, Qua, Qui, Sex, Sab"))
            dias = "Todos os Dias";
        x.setText(dias);

        return view;
    }
}
