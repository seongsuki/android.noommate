package noommate.android.commons;

import android.app.Activity;
import android.widget.Toast;

import noommate.android.R;

public class BackPressCloseHandler {
  private long backKeyPressedTime = 0;
  private Toast toast;
  private Activity activity;

  public BackPressCloseHandler(Activity context) {
    this.activity = context;
  }

  public void onBackPressed() {
    if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
      backKeyPressedTime = System.currentTimeMillis();
      showGuide();
      return;
    }
    if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
//      activity.finish();
      activity.moveTaskToBack(true);						// 태스크를 백그라운드로 이동
      activity.finishAndRemoveTask();						// 액티비티 종료 + 태스크 리스트에서 지우기
      android.os.Process.killProcess(android.os.Process.myPid());

      toast.cancel();
    }
  }

  public void showGuide() {
    toast = Toast.makeText(activity, R.string.common_back, Toast.LENGTH_SHORT);
    toast.show();
  }
}


