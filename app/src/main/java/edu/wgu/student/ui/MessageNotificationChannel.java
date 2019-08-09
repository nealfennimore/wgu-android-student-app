//package edu.wgu.student.ui;
//
//import android.annotation.TargetApi;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.Service;
//import android.content.Intent;
//import android.os.Build;
//import android.os.IBinder;
//
//import edu.wgu.student.R;
//
//import static android.provider.Settings.System.getString;
//
//@TargetApi(26)
//public class MessageNotificationChannel {
//    private String CHANNEL_ID = "message";
//    private String channel_name = "message";
//    private String description = "message";
//
//
//    private void createChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channel_name, importance);
//            channel.setDescription(description);
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//
//    }
//}
