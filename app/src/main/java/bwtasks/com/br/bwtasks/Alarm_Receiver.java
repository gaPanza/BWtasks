package bwtasks.com.br.bwtasks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by gapan on 17/11/2016.
 */

public class Alarm_Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //Pegando as Strings Extras
        String getString = intent.getExtras().getString("extra");

        //Criando o Intent
        Intent serviceIntent = new Intent(context, RingtonePlayingService.class);

        //Passando a String extra para o RingtoneService
        serviceIntent.putExtra("extra",getString);

        //Iniciando o Serviço
        context.startService(serviceIntent);

    }
}
