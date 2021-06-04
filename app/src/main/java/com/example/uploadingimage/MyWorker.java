package com.example.uploadingimage;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.opengl.Visibility;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;


import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {

    public MyWorker(@NonNull  Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

    }

    @NonNull
    @Override
    public Result doWork() {
        displayNotifaction();
        return Result.success();
    }
    public void displayNotifaction() {
        int progressMax = 100;
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("wappgo", "wappgo", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "wappgo")
                .setContentTitle("Uploading")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOnlyAlertOnce(true)
                .setOngoing(true)
                .setProgress(progressMax, 0, false);
        notificationManager.notify(1, builder.build());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                    Log.d("Uploding", "started uploading ");
                    for (int progress = 10; progress <= progressMax; progress += 20) {
                        builder.setProgress(progressMax, progress, false);
                        SystemClock.sleep(1000);
                        notificationManager.notify(1, builder.build());

                    }
                    builder.setContentText("Upload complete")
                            .setProgress(0, 0, false)
                            .setOngoing(false);
                    notificationManager.notify(1, builder.build());
                }
        });
        thread.start();

    }


}
