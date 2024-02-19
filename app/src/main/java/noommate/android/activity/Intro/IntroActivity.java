package noommate.android.activity.Intro;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import noommate.android.R;
import noommate.android.activity.NoommateActivity;
import noommate.android.activity.main.MainActivity;
import noommate.android.activity.signin.SigninActivity;
import noommate.android.commons.Constants;
import noommate.android.commons.Tools;
import noommate.android.models.AlarmModel;
import timber.log.Timber;

public class IntroActivity extends NoommateActivity {
  //--------------------------------------------------------------------------------------------
  // MARK : GET START INTENT
  //--------------------------------------------------------------------------------------------
  public static Intent getStartIntent(Context context) {
    Intent intent = new Intent(context, IntroActivity.class);
    return intent;
  }

  public static Intent getStartIntent(Context context, AlarmModel alarmModel) {
    Intent intent = new Intent(context, IntroActivity.class);
    mAlarmModel = alarmModel;
    return intent;
  }

  //--------------------------------------------------------------------------------------------
  // MARK : Bind Area
  //--------------------------------------------------------------------------------------------

  //--------------------------------------------------------------------------------------------
  // MARK : Local variables
  //--------------------------------------------------------------------------------------------
  private static AlarmModel mAlarmModel;

  //--------------------------------------------------------------------------------------------
  // MARK : Override
  //--------------------------------------------------------------------------------------------
  @Override
  protected int inflateLayout() {
    return R.layout.activity_intro;
  }

  @Override
  protected void initLayout() {
    permissionCheck();
  }

  @Override
  protected void initRequest() {

  }

  //--------------------------------------------------------------------------------------------
  // MARK : Local functions
  //--------------------------------------------------------------------------------------------

  /**
   * 메인 이동 딜레이
   */
  private void delayMain() {
    Handler delayHandler = new Handler();
    delayHandler.postDelayed(new Runnable() {
      @Override
      public void run() {
        toMain();
      }
    }, 2000);
  }

  /**
   * 권한체크
   */
  private void permissionCheck() {
    // 권한 체크
    PermissionListener permissionlistener = new PermissionListener() {
      @Override
      public void onPermissionGranted() {
        delayMain();
      }

      @Override
      public void onPermissionDenied(List<String> deniedPermissions) {
        Tools.getInstance(mActivity).showToast("원할한 서비스 이용을 위하여, 권한을 확인해주세요.");
        delayMain();
      }
    };

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      TedPermission.with(mActivity)
          .setPermissionListener(permissionlistener)
          .setRationaleMessage(R.string.common_auth)
          .setDeniedMessage(R.string.common_auth_denined)
          .setPermissions(Manifest.permission.CAMERA,
                  Manifest.permission.POST_NOTIFICATIONS,
                  Manifest.permission.READ_MEDIA_IMAGES,
                  Manifest.permission.READ_MEDIA_AUDIO,
                  Manifest.permission.READ_MEDIA_VIDEO)
          .check();
    } else {
      TedPermission.with(mActivity)
              .setPermissionListener(permissionlistener)
              .setRationaleMessage(R.string.common_auth)
              .setDeniedMessage(R.string.common_auth_denined)
              .setPermissions(Manifest.permission.CAMERA,
                      Manifest.permission.POST_NOTIFICATIONS,
                      Manifest.permission.READ_EXTERNAL_STORAGE,
                      Manifest.permission.WRITE_EXTERNAL_STORAGE)
              .check();
    }
  }

  /**
   * 로그인 체크
   */
  private void toMain() {

    Timber.i("알림 보낼때 ::::::" + mAlarmModel);

    if (mAlarmModel == null) {
      if (Prefs.getString(Constants.MEMBER_IDX,"").equals("")) {
        removeAllActivity();
        Intent signinActivity = SigninActivity.getStartIntent(mActivity);
        startActivity(signinActivity, TRANS.ZOOM);
      } else {
        removeAllActivity();
        Intent mainActivity = MainActivity.getStartIntent(mActivity);
        startActivity(mainActivity,TRANS.ZOOM);
      }

    } else {
      removeAllActivity();
      Intent mainActivity = MainActivity.getStartIntent(mActivity, mAlarmModel);
      startActivity(mainActivity, TRANS.ZOOM);
    }

  }


  //--------------------------------------------------------------------------------------------
  // MARK : Bind Actions
  //--------------------------------------------------------------------------------------------
}

