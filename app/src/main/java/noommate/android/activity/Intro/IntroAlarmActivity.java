package noommate.android.activity.Intro;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.provider.SyncStateContract;

import com.pixplicity.easyprefs.library.Prefs;

import noommate.android.R;
import noommate.android.activity.NoommateActivity;

import timber.log.Timber;

public class IntroAlarmActivity extends NoommateActivity {
  //--------------------------------------------------------------------------------------------
  // MARK : GET START INTENT
  //--------------------------------------------------------------------------------------------
  public static Intent getStartIntent(Context context, String data) {
    Intent intent = new Intent(context, IntroAlarmActivity.class);
    mData = data;
    return intent;
  }
  //--------------------------------------------------------------------------------------------
  // MARK : Bind Area
  //--------------------------------------------------------------------------------------------

  //--------------------------------------------------------------------------------------------
  // MARK : Local variables
  //--------------------------------------------------------------------------------------------
  public static String mData;
  //--------------------------------------------------------------------------------------------
  // MARK : Override
  //--------------------------------------------------------------------------------------------
  @Override
  protected int inflateLayout() {
    return R.layout.activity_intro;
  }

  @Override
  protected void initLayout() {

    Handler delayHandler = new Handler();
    delayHandler.postDelayed(new Runnable() {
      @Override
      public void run() {
        Timber.i("DATA = " + mData);
        Intent introActivity = IntroActivity.getStartIntent(mActivity);
        Prefs.putString(SyncStateContract.Constants.DATA, mData);
        startActivity(introActivity, TRANS.PUSH);
        finishWithRemove();
      }
    }, 1000);



  }

  @Override
  protected void initRequest() {

  }


  //--------------------------------------------------------------------------------------------
  // MARK : Local functions
  //--------------------------------------------------------------------------------------------
  //--------------------------------------------------------------------------------------------
  // MARK : Bind Actions
  //--------------------------------------------------------------------------------------------

}
