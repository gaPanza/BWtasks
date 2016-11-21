package bwtasks.com.br.bwtasks;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by gapan on 17/11/2016.
 */

public class RingtonePlayingService extends Service{

    MediaPlayer musica;
    boolean isRunning;
    int startId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Pegando as Strings Extra
        String estado = intent.getExtras().getString("extra");
        Log.e("Estado", estado);
        assert estado!=null;
        switch (estado) {
            case "alarmOn":
                startId = 1;
                break;
            case "alarmOff":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }

        if(!this.isRunning && startId == 1){

            musica = MediaPlayer.create(this, R.raw.numb);
            musica.start();
            Log.e("local","Chegou Aqui");
            this.isRunning=true;
            this.startId = 0;
        }

        else if(this.isRunning && startId == 0){

            Log.e("local","Chegou Aqui2");
            this.isRunning = false;
            this.startId = 0;

        }

        else if(!this.isRunning && startId == 0){
            this.isRunning = false;
            this.startId = 0;
            Log.e("local","Chegou Aqui3");
        }


        else if(this.isRunning && startId == 1){
            musica.stop();
            musica.start();
            this.isRunning = true;
            this.startId = 1;
            Log.e("local","Chegou Aqui4");
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "OnDestroy", Toast.LENGTH_SHORT).show();
    }
}
