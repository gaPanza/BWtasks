package bwtasks.com.br.bwtasks;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by gapan on 16/11/2016.
 */

public class addAlarmFragment extends Fragment{
    View myView;
    AlarmManager alarmManager;
    Task t;
    String marcador;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.alarm_new,container,false);
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        //Buttons
        Button agendar = (Button) myView.findViewById(R.id.Agendar);
        Button voltar =(Button) myView.findViewById(R.id.Voltar);
        Button m = (Button) myView.findViewById(R.id.Marcador);
        Button cancelar = (Button) myView.findViewById(R.id.cancelar);
        Button ok = (Button) myView.findViewById(R.id.ok);

        m.setOnClickListener(onClickListener);
        agendar.setOnClickListener(onClickListener);
        voltar.setOnClickListener(onClickListener);
        cancelar.setOnClickListener(onClickListener);
        ok.setOnClickListener(onClickListener);

        //Banco

        //Calendar
        TimePicker timePicker = (TimePicker) myView.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);



        return myView;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RelativeLayout editM = (RelativeLayout) myView.findViewById(R.id.marcador);
            FragmentManager fragmentManager = getFragmentManager();
            switch (v.getId()) {
                case R.id.Voltar:
                    fragmentManager.popBackStack();
                    break;
                case R.id.Agendar:

                    //Adiciona uma nova Task
                    TimePicker timePicker = (TimePicker) myView.findViewById(R.id.timePicker);
                    Integer hora = timePicker.getCurrentHour();
                    Integer minute = timePicker.getCurrentMinute();
                    String diasDaSemana = null;
                    CheckBox [] dias = new CheckBox[]{(CheckBox) myView.findViewById(R.id.domingo),(CheckBox) myView.findViewById(R.id.segunda),
                            (CheckBox) myView.findViewById(R.id.terca),(CheckBox) myView.findViewById(R.id.quarta),(CheckBox) myView.findViewById(R.id.quinta),
                            (CheckBox) myView.findViewById(R.id.sexta),(CheckBox) myView.findViewById(R.id.sabado)};

                    for(Integer i = 0; i<7; i++){
                        if(dias[i].isChecked()==true)
                            if (diasDaSemana == null)
                                diasDaSemana = i.toString();
                            else
                                diasDaSemana = diasDaSemana + ","+i.toString();

                    }
                    //ArrayList
                    ArrayList<Integer> w = new ArrayList<Integer>();
                    //Calendariza
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hora);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);


                    //Avisando o Android sobre novo agendamento
                    for(Integer i = 0; i<7; i++){
                        if(dias[i].isChecked()==true) {
                            Intent myIntent = new Intent(getContext(), Alarm_Receiver.class);
                            myIntent.putExtra("extra","alarmOn");
                            calendar = Calendar.getInstance();
                            calendar.set(Calendar.DAY_OF_WEEK, i + 1);
                            calendar.set(Calendar.HOUR_OF_DAY, hora);
                            calendar.set(Calendar.MINUTE, minute);
                            calendar.set(Calendar.SECOND, 0);
                            Toast.makeText(getContext(), calendar.getInstance().getTime().toString(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(getContext(), calendar.getTime().toString(), Toast.LENGTH_SHORT).show();
                            if(calendar.before(Calendar.getInstance())) {
                                calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 7);
                            }
                            final int id = (int) Calendar.getInstance().getTimeInMillis();
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), id,  myIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
                            w.add(id);
                            Log.e("Id",w.get(i).toString());
                            Toast.makeText(getContext(), calendar.getTime().toString(), Toast.LENGTH_SHORT).show();
                        }else{
                            w.add(0);
                            Log.e("Id",w.get(i).toString());
                        }
                    }


                    //Persiste a nova Task
                    t = new Task(hora, minute, diasDaSemana, true, marcador, w.get(0),w.get(1),w.get(2),w.get(3),w.get(4),w.get(5),w.get(6));
                    bancoCrud bancoCrud = new bancoCrud(getContext());
                    bancoCrud.inserirTarefa(t);

                    //Volta
                    Snackbar.make(v, "Tarefa Adicionada", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    fragmentManager.popBackStack();
                    break;

                case R.id.Marcador:
                    if (editM.getVisibility() == VISIBLE)
                        editM.setVisibility(GONE);
                    else
                        editM.setVisibility(VISIBLE);
                    break;

                case R.id.ok:
                    String x;
                    EditText mark = (EditText) myView.findViewById(R.id.editMarcador);
                    x = mark.getText().toString();
                    if(x == "" || x == null)
                        editM.setVisibility(GONE);
                    else {
                        marcador = x;
                        Toast.makeText(v.getContext(), "Marcador Editado!", Toast.LENGTH_SHORT).show();
                        editM.setVisibility(GONE);
                    }
                    break;

                case R.id.cancelar:
                    editM.setVisibility(GONE);
                    break;


            }
        }
    };
}
