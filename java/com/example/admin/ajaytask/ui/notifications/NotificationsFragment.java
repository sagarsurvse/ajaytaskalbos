package com.example.admin.ajaytask.ui.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.ajaytask.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);

                NotificationManager mNotificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("001",
                            "ajay",
                            NotificationManager.IMPORTANCE_DEFAULT);
                    channel.setDescription("this is ajay ");

                    mNotificationManager.createNotificationChannel(channel);
                }
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), "001")
                        .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                        .setContentText("check Notification ")// message for notification
                        .setSubText("notification ")
                        .setAutoCancel(true);

                mBuilder.setDefaults(Notification.FLAG_ONLY_ALERT_ONCE);
                // clear notification after click
                Intent intent = new Intent(getContext(), NotificationsFragment.class);
                PendingIntent pi = PendingIntent.getActivity(getContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                mBuilder.setContentIntent(pi);
                MediaPlayer mp= MediaPlayer.create(getContext(), R.raw.eventually);
                mp.start();
                mNotificationManager.notify(0, mBuilder.build());

            }
        });
        return root;
    }
}