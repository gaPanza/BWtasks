package bwtasks.com.br.bwtasks;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PrimeiraFragmento extends Fragment implements AdapterView.OnItemClickListener {
    View myView;
    Tasks_Adapter tasks_adapter;
    ListView tasks_list;
    ArrayList<Task> taskArrayList;
    bancoCrud bancoCrud;
    AlarmManager alarmManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.primeiro_layout,container,false);
        bancoCrud = new bancoCrud(getContext());
        taskArrayList = bancoCrud.returnAllTasks();
        tasks_adapter = new Tasks_Adapter(taskArrayList, getContext());

        tasks_list = (ListView) myView.findViewById(R.id.tasks_list);
        tasks_list.setAdapter(tasks_adapter);
        tasks_list.setOnItemClickListener(this);

        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        final FloatingActionButton fab = (FloatingActionButton) myView.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new addAlarmFragment()).addToBackStack(null).commit();
            }
        });

        TextView desd = (TextView) myView.findViewById(R.id.marcadorEditText);

        return myView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        Task t = taskArrayList.get(position);

        AlarmFragment fr = new AlarmFragment();
        FragmentManager fragmentManager = getFragmentManager();

        Bundle args = new Bundle();
        args.putInt("position", position);
        fr.setArguments(args);

        fragmentManager.beginTransaction().replace(R.id.content_frame, fr).addToBackStack(null).commit();
    }
}
