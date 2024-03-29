package noommate.android;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Base64;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kakao.sdk.common.KakaoSdk;
import com.pixplicity.easyprefs.library.Prefs;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import noommate.android.commons.Constants;
import timber.log.Timber;

public class NoommateApplication extends Application {

  //--------------------------------------------------------------------------------------------
  // MARK : Override
  //--------------------------------------------------------------------------------------------
  @Override
  public void onCreate() {
    super.onCreate();

    if (BuildConfig.DEBUG) {
      Timber.plant(new NoommateDebugTree());
    }

    Timber.v("VERBOSE");
    Timber.d("DEBUG");
    Timber.i("INFO");
    Timber.w("WARNNING");
    Timber.e("ERROR");
    getKeyHash(this);

    // Shared Preference settging
    new Prefs.Builder()
        .setContext(this)
        .setMode(ContextWrapper.MODE_PRIVATE)
        .setPrefsName(getPackageName())
        .setUseDefaultSharedPreference(true)
        .build();

    // Firebase 초기화
    FirebaseApp.initializeApp(this);

    FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener(new OnCompleteListener<String>() {
              @Override
              public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                  return;
                }

                // Get new FCM registration token
                String token = task.getResult();
                Timber.i("FCM KEY ===============================");
                Prefs.putString(Constants.FCM_TOKEN, token);
                Timber.i(Prefs.getString(Constants.FCM_TOKEN, ""));
                Timber.i("FCM KEY ===============================");
              }
            });
    KakaoSdk.init(this, Constants.KAKAO_APP_KEY);

  }

  //-------------------------------------------------------------------------------------------
  // MARK : Local functions
  //--------------------------------------------------------------------------------------------*/

  /**
   * 키 해시 가져오기
   *
   * @param context
   */
  public void getKeyHash(final Context context) {
    try {
      PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
      if (packageInfo == null)
        return;

      for (android.content.pm.Signature signature : packageInfo.signatures) {
        try {
          MessageDigest md = MessageDigest.getInstance("SHA");
          md.update(signature.toByteArray());

          Timber.i(Base64.encodeToString(md.digest(), Base64.NO_WRAP));


        } catch (NoSuchAlgorithmException e) {
          Timber.e("MessageDigest를 가져올 수 없습니다. Signature : " + signature);
          Timber.e(e.getLocalizedMessage());
        }
      }
    } catch (PackageManager.NameNotFoundException e) {
      Timber.e(e.getLocalizedMessage());
    } catch (Exception e) {
      Timber.e(e.getLocalizedMessage());
    }

    return;
  }

  /**
   * Timber Debug tree
   */
  public class NoommateDebugTree extends Timber.DebugTree {
    @Override
    protected String createStackElementTag(StackTraceElement element) {
      return String.format("NOOMMATE_LOG - (%s:%s) [%s] ", element.getFileName(), element.getLineNumber(), element.getMethodName());
    }
  }

}
