package online.botika.risetwebviewtonotif.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class Restarter extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if  (intent.getAction().equals("restartservice")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Intent startIntent = new Intent(context, BackgroundService.class);
                startIntent.setAction("STARTSERVICE");
                context.startForegroundService(startIntent);
            } else {
                Intent startIntent = new Intent(context, BackgroundService.class);
                startIntent.setAction("STARTSERVICE");
                context.startService(startIntent);
            }
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Intent startIntent = new Intent(context, BackgroundService.class);
                startIntent.setAction("STOPSERVICE");
                context.startForegroundService(startIntent);
            } else {
                Intent startIntent = new Intent(context, BackgroundService.class);
                startIntent.setAction("STOPSERVICE");
                context.startService(startIntent);
            }
        }
    }
}
