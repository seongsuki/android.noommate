package noommate.android.commons;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class RocateerPreference {
  private static String PREF_NAME = "_pref_tb";

  @SuppressLint("WrongConstant")
  public static SharedPreferences getPrefrence(Context context) {
    return context.getSharedPreferences(PREF_NAME, Activity.MODE_APPEND);
  }

  @SuppressLint("WrongConstant")
  public static SharedPreferences.Editor getEditor(Context context) {
    return context.getSharedPreferences(PREF_NAME, Activity.MODE_APPEND).edit();
  }

  public static void removeAllPreferences(Context context) {
    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = pref.edit();
    editor.clear();
    editor.commit();
  }



  // FCM TOKEN
  public static String getFCMToken(Context context) {
    return getPrefrence(context).getString("pFCMToken", null);
  }

  public static void setFCMToken(Context context, String fcmToken) {
    getEditor(context).putString("pFCMToken", fcmToken).commit();
  }
}
