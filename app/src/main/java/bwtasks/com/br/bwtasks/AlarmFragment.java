package bwtasks.com.br.bwtasks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by gapan on 16/11/2016.
 */

public class AlarmFragment extends Fragment {
    String marcador;
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.alarm,container,false);
        final bancoCrud bancoCrud= new bancoCrud(getContext());
        ArrayList<Task> taskArrayList = bancoCrud.returnAllTasks();

        Integer position = getArguments().getInt("position");
        final Task t = taskArrayList.get(position);

        //Adaptando o TimePicker
        TimePicker timePicker = (TimePicker) myView.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(t.getHora());
        timePicker.setCurrentMinute(t.getMinuto());

        //Adaptando os Booleans
        CheckBox d = (CheckBox) myView.findViewById(R.id.domingo);
        CheckBox s = (CheckBox) myView.findViewById(R.id.segunda);
        CheckBox te = (CheckBox) myView.findViewById(R.id.terca);
        CheckBox q = (CheckBox) myView.findViewById(R.id.quarta);
        CheckBox qi = (CheckBox) myView.findViewById(R.id.quinta);
        CheckBox se = (CheckBox) myView.findViewById(R.id.sexta);
        CheckBox sa = (CheckBox) myView.findViewById(R.id.sabado);

        String dias = t.getDias();
        if(dias.contains("0"))
            d.setChecked(true);
        if(dias.contains("1"))
            s.setChecked(true);
        if(dias.contains("2"))
            te.setChecked(true);
        if(dias.contains("3"))
            q.setChecked(true);
        if(dias.contains("4"))
            qi.setChecked(true);
        if(dias.contains("5"))
            se.setChecked(true);
        if(dias.contains("6"))
            sa.setChecked(true);

        //Definindo o Marcador caso exista
        EditText mark = (EditText) myView.findViewById(R.id.editMarcador);
        mark.setText(t.getDesc());

        //Buttons
        Button voltar = (Button) myView.findViewById(R.id.Voltar);
        Button agendar = (Button) myView.findViewById(R.id.Agendar);
        Button m = (Button) myView.findViewById(R.id.Marcador);
        Button cancelar = (Button) myView.findViewById(R.id.cancelar);
        Button ok = (Button) myView.findViewById(R.id.ok);

       voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        agendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();

                TimePicker timePicker = (TimePicker) myView.findViewById(R.id.timePicker);
                Integer hora = timePicker.getCurrentHour();
                Integer minute = timePicker.getCurrentMinute();
                String diasDaSemana = null;
                CheckBox [] dias = new CheckBox[]{(CheckBox) myView.findViewById(R.id.domingo),(CheckBox) myView.findViewById(R.id.segunda),
                        (CheckBox) myView.findViewById(R.id.terca),(CheckBox) myView.findViewById(R.id.quarta),(CheckBox) myView.findViewById(R.id.quinta),
                        (CheckBox) myView.findViewById(R.id.sexta),(CheckBox) myView.findViewById(R.id.sabado)};
                for(Integer i = 0; i<7; i++) {
                    if (dias[i].isChecked() == true)
                        if (diasDaSemana == null)
                            diasDaSemana = i.toString();
                        else
                            diasDaSemana = diasDaSemana + "," + i.toString();
                }
                Task newTask = new Task(hora, minute, diasDaSemana,t.getAtivado(), marcador);

                bancoCrud.deletarTarefa(t);
                bancoCrud.inserirTarefa(newTask);


                Snackbar.make(view, "Tarefa Modificada", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                fragmentManager.popBackStack();
            }
        });

        m.setOnClickListener(new View.OnClickListener() {
            RelativeLayout editM = (RelativeLayout) myView.findViewById(R.id.marcador);
            @Override
            public void onClick(View view) {
                if (editM.getVisibility() == VISIBLE)
                    editM.setVisibility(GONE);
                else
                    editM.setVisibility(VISIBLE);
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RelativeLayout editM = (RelativeLayout) myView.findViewById(R.id.marcador);
                editM.setVisibility(GONE);
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RelativeLayout editM = (RelativeLayout) myView.findViewById(R.id.marcador);
                String x;
                EditText mark = (EditText) myView.findViewById(R.id.editMarcador);
                x = mark.getText().toString();
                if(x == "" || x == null)
                    editM.setVisibility(GONE);
                else {
                    marcador = x;
                    Toast.makeText(myView.getContext(), "Marcador Editado!", Toast.LENGTH_SHORT).show();
                    editM.setVisibility(GONE);
                }
            }
        });


        return myView;
    }
}
