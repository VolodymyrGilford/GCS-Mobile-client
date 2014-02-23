package com.globalcontrolsystem.gcs.Utils;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.globalcontrolsystem.gcs.MainScreenActivity;
import com.globalcontrolsystem.gcs.R;


public class Notifier {
    private static int notify_id = 0;

    private final Context _context;

    public Notifier(Context context) throws Exception {
        if (context == null) throw new Exception("Notifer context is null");
        _context = context;
    }

    public void ShowToast(String text, int duration){
        Toast.makeText(_context, text, duration).show();
    }

    public void ShowToast(String text) {
        ShowToast(text, Toast.LENGTH_SHORT);
    }

    public void ShowDialog(String title, String message){
        new AlertDialog.Builder(_context)
                       .setMessage(message)
                       .setTitle(title)
                       .setNeutralButton("Close", null)
                       .show();
    }

    //TODO Other Dialogs methods

    public void Notify(String title, String message){
        NotificationManager notifManager = (NotificationManager) _context.getSystemService(_context.NOTIFICATION_SERVICE);

        PendingIntent intent = PendingIntent.getActivity(_context, 0, new Intent(_context, MainScreenActivity.class), 0);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(_context)
                .setSmallIcon(R.drawable.notify_icon)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(intent)
                .setSound(soundUri);

        notifManager.notify(notify_id++, mBuilder.build());
    }

}
