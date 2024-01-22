package noommate.android.commons;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import noommate.android.R;
import timber.log.Timber;

public class RocateerFMService extends FirebaseMessagingService {
  public static int current_ID = 0;

  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    super.onMessageReceived(remoteMessage);

    if (remoteMessage == null) {
      return;
    }

    Timber.i("Remote message test");

    PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
    @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
    wakeLock.acquire(3000);

    if (remoteMessage.getData().size() > 0) {
      Timber.i(remoteMessage.getData().toString());
      Bundle bundle = new Bundle();
      bundle.putString("data", remoteMessage.getData().toString());
      String title = remoteMessage.getData().get("title");
      String message = remoteMessage.getData().get("body");
      String image = remoteMessage.getData().get("image");
      // Index 별 이동 처리

      if (image != null) {
        final Bitmap[] bitmap = {null};

        Glide.with(getApplicationContext())
            .asBitmap()
            .load(image)
            .into(new CustomTarget<Bitmap>() {
              @Override
              public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                bitmap[0] = resource;
                sendNotification(title, message, bitmap[0]);
              }
              @Override
              public void onLoadCleared(@Nullable Drawable placeholder) {
              }
            });


      } else {
        sendNotification(title, message);
      }

    }
  }

  /**
   * Notification 발송.
   *
   * @param title
   * @param message
   */
  private void sendNotification(String title, String message) {
    current_ID = (int) System.currentTimeMillis();
    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "notify_001");

    // 메세지는 Intro 를 통하도록
//    Intent introActivity = IntroActivity.getStartIntent(this);
//    introActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, introActivity, PendingIntent.FLAG_UPDATE_CURRENT);

    Bitmap rawBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
    NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
    bigText.bigText(message);
    bigText.setBigContentTitle(title);
//    mBuilder.setContentIntent(pendingIntent);
    mBuilder.setSmallIcon(R.mipmap.ic_launcher);
//    mBuilder.setLargeIcon(rawBitmap);
    mBuilder.setContentTitle(title);
    mBuilder.setContentText(message);
    mBuilder.setPriority(Notification.PRIORITY_MAX);
    mBuilder.setStyle(bigText);
    mBuilder.setAutoCancel(true);
    Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    mBuilder.setSound(uri);
    long[] v = {500, 1000};
    mBuilder.setVibrate(v);

    NotificationManager mNotificationManager =
        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel("notify_001", "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT);
      mNotificationManager.createNotificationChannel(channel);
    }

    mNotificationManager.notify(current_ID, mBuilder.build());

  }

  /**
   * 이미지 노티피케이션
   * @param title
   * @param message
   */
  private void sendNotification(String title, String message, Bitmap image)  {
    current_ID = (int) System.currentTimeMillis();
    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "notify_001");

    // 메세지는 Intro 를 통하도록
//    Intent introActivity = IntroActivity.getStartIntent(this);
//    introActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, introActivity, PendingIntent.FLAG_UPDATE_CURRENT);


    Bitmap rawBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
    NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
    bigText.bigText(message);
    bigText.setBigContentTitle(title);
//    mBuilder.setContentIntent(pendingIntent);
    mBuilder.setSmallIcon(R.mipmap.ic_launcher);
    mBuilder.setLargeIcon(image);
    mBuilder.setContentTitle(title);
    mBuilder.setContentText(message);
    mBuilder.setPriority(Notification.PRIORITY_MAX);
    mBuilder.setStyle(bigText);
    mBuilder.setAutoCancel(true);
    Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    mBuilder.setSound(uri);
    long[] v = {500, 1000};
    mBuilder.setVibrate(v);

    NotificationManager mNotificationManager =
        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel("notify_001", "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT);
      mNotificationManager.createNotificationChannel(channel);
    }

    mNotificationManager.notify(current_ID, mBuilder.build());

  }

}