package noommate.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import noommate.android.R;
import noommate.android.commons.TouchDetectableScrollView;
import noommate.android.dialog.RocateerDialog;
import noommate.android.models.ActivityModel;
import noommate.android.commons.BackPressCloseHandler;
import noommate.android.commons.Tools;
import timber.log.Timber;

public abstract class RocateerActivity extends AppCompatActivity {

  //--------------------------------------------------------------------------------------------
  // MARK : Interface Area
  //--------------------------------------------------------------------------------------------

  protected abstract int inflateLayout();

  protected abstract void initLayout();

  protected abstract void initRequest();

  public interface DialogEventListener {
    void onReceivedEvent();
  }

  public enum TRANS {
    PRESENT,
    PUSH,
    ZOOM
  }


  //--------------------------------------------------------------------------------------------
  // MARK : Bind Area
  //--------------------------------------------------------------------------------------------
  @Nullable
  @BindView(R.id.toolbar)
  public Toolbar mActionBarToolbar;
  @Nullable
  @BindView(R.id.toolbar_title)
  public AppCompatTextView mToolbarTitle;
  @Nullable
  @BindView(R.id.finish_button)
  public AppCompatTextView mFinishButton;
  @Nullable
  @BindView(R.id.back_button)
  public AppCompatImageView mBackButton;
  //  @Nullable
//  @BindView(R.id.menu_drawer_layout)
//  DrawerLayout mMenuDrawerLayout;
  @Nullable
  @BindView(R.id.more_scroll_view)
  public TouchDetectableScrollView mMoreScrollView;
  @Nullable
  @BindView(R.id.refresh_layout)
  public SwipeRefreshLayout mRefreshLayout;

  //--------------------------------------------------------------------------------------------
  // MARK : Local variables
  //--------------------------------------------------------------------------------------------
  static ArrayList<ActivityModel> mListActivity = new ArrayList<>();
  protected RocateerActivity mActivity;
  private TRANS activityTrans = TRANS.PRESENT;

  private Snackbar mSnackbar;
  private DialogEventListener mDialogEventListener;

  private BackPressCloseHandler backPressCloseHandler;

  private ProgressBar mProgressBar;

  //--------------------------------------------------------------------------------------------
  // MARK : Override
  //--------------------------------------------------------------------------------------------
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    mActivity = this;
    setContentView(this.inflateLayout());

    mListActivity.add(new ActivityModel(getClass().getSimpleName(), this));
    Timber.i("Add : " + getClass().getSimpleName());

//    if (mScrollView != null) {
//      HideUtil.init(mActivity, mScrollView);
//    } else {
//      HideUtil.init(mActivity);
//    }

    activityTrans = TRANS.values()[getIntent().getIntExtra("ANIMATION", 0)];
    backPressCloseHandler = new BackPressCloseHandler(this);
  }

  @Override
  public void setContentView(int layoutId) {
    if (layoutId == 0) {
      return;
    }

    super.setContentView(layoutId);
    ButterKnife.bind(this);

    Tools.getInstance(mActivity).setSystemBarColor(mActivity, R.color.color_000000);
//    Tools.setSystemBarLight(this);

    this.initLayout();
    this.initRequest();
  }

  @Override
  protected void onStart() {
    super.onStart();

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  @Override
  public void onBackPressed() {
    if (mListActivity.size() == 1) {
      backPressCloseHandler.onBackPressed();
    } else {
      finishWithRemove();
    }

//    super.onBackPressed();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  //--------------------------------------------------------------------------------------------
  // MARK : Local functions
  //--------------------------------------------------------------------------------------------

  /**
   * -   * initToolbar - 타이틀과 백버튼이 보일 때
   * -   *
   * -   * @param title
   * -   * @param homeImage
   * +   * 모든 액티비티를 삭제한다.
   */
  public void initToolbar(String title) {
    if (mToolbarTitle != null) {
      mToolbarTitle.setText(title);
    }


//    if (mActionBarToolbar != null) {
//      mActionBarToolbar.setTitle(title);
//      setSupportActionBar(mActionBarToolbar);
//    }
//    if (getSupportActionBar() != null) {
//      getSupportActionBar().setDisplayHomeAsUpEnabled((homeImage != null));
//      getSupportActionBar().setHomeAsUpIndicator(homeImage);
//      getSupportActionBar().setDisplayShowTitleEnabled(true);
//    }
  }

  /**
   * 종료 후에 액티비티 삭제
   */
  public void finishWithRemove() {
    finish();
    mListActivity.remove(mListActivity.size() - 1);
    Timber.i("remove: " + getClass().getSimpleName());

  }

  /**
   * 여러 엑티비티를 삭제한다.
   * @param count
   */
  public void finishMultiRemoveActivity(int count) {
    for (int i = 1; i < count + 1; i++) {
      mListActivity.get(mListActivity.size() - 1).getRocateerActivity().finish();
      mListActivity.remove(mListActivity.size() - 1);
    }
  }


  /**
   * 모든 액티비티를 삭제한다.
   */
  public boolean removeAllActivity() {
    for (ActivityModel value : mListActivity) {
      value.getRocateerActivity().finish();
    }
    mListActivity.clear();
    return true;
  }

  /**
   * Start Activity
   *
   * @param trans
   */
  public void startActivity(Intent intent, TRANS trans) {
    intent.putExtra("ANIMATION", trans.ordinal());
    super.startActivity(intent);
    switch (trans) {
      case PRESENT:
        overridePendingTransition(R.anim.animation_present_in, R.anim.animation_nothing);
        break;
      case PUSH:
        overridePendingTransition(R.anim.animation_push_in, R.anim.animation_nothing);
        break;
      case ZOOM:
        overridePendingTransition(R.anim.animation_zoom_in, R.anim.animation_nothing);
        break;
    }
  }

  /**
   * Start Activity For Result
   *
   * @param intent
   */
  public void startActivityForResult(Intent intent, int requestCode, TRANS trans) {
    intent.putExtra("ANIMATION", trans.ordinal());
    super.startActivityForResult(intent, requestCode);
    switch (trans) {
      case PRESENT:
        overridePendingTransition(R.anim.animation_present_in, R.anim.animation_nothing);
        break;
      case PUSH:
        overridePendingTransition(R.anim.animation_push_in, R.anim.animation_nothing);
        break;
      case ZOOM:
        overridePendingTransition(R.anim.animation_zoom_in, R.anim.animation_nothing);
        break;
    }
  }

  @Override
  public void finish() {
    super.finish();
    switch (activityTrans) {
      case PRESENT:
        overridePendingTransition(R.anim.animation_nothing, R.anim.animation_present_out);
        break;
      case PUSH:
        overridePendingTransition(R.anim.animation_nothing, R.anim.animation_push_out);
        break;
      case ZOOM:
        overridePendingTransition(R.anim.animation_nothing, R.anim.animation_zoom_out);
        break;
    }

  }

  /**
   * 확인 취소가 없는 경우
   *
   * @param message
   * @param confirmTitle
   * @param dialogEventListener
   */
  public void showAlertDialog(String message, String confirmTitle, final DialogEventListener dialogEventListener) {
    if (dialogEventListener != null) {
      mDialogEventListener = dialogEventListener;
    }

    final RocateerDialog rocateerDialog = new RocateerDialog(mActivity);
    rocateerDialog.setDialog(message, confirmTitle);
    rocateerDialog.addOKButton(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        rocateerDialog.dismiss();
        if (mDialogEventListener != null) {
          mDialogEventListener.onReceivedEvent();
          mDialogEventListener = null;
        }
      }
    });
  }

  /**
   * 확인 취소가 있는 경우
   *
   * @param message
   * @param cancelTitle
   * @param confirmTitle
   * @param dialogEventListener
   */
  public void showConfirmDialog(String message, String cancelTitle, String confirmTitle, DialogEventListener dialogEventListener) {
    if (dialogEventListener != null) {
      mDialogEventListener = dialogEventListener;
    }

    final RocateerDialog rocateerDialog = new RocateerDialog(mActivity);
    rocateerDialog.setDialog(message, confirmTitle, cancelTitle);
    rocateerDialog.addOKButton(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        rocateerDialog.dismiss();
        if (mDialogEventListener != null) {
          mDialogEventListener.onReceivedEvent();
          mDialogEventListener = null;
        }

      }
    });
    rocateerDialog.addCancelButton(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        rocateerDialog.dismiss();
      }
    });
  }

  /**
   * 에러만 보여주는 다이얼로그
   */
  public void showErrorDialog() {
    final RocateerDialog rocateerDialog = new RocateerDialog(mActivity);
    rocateerDialog.setDialog(getString(R.string.common_err_api_msg), getString(R.string.common_ok));
    rocateerDialog.addOKButton(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        rocateerDialog.dismiss();
      }
    });
  }

  /**
   * API 에러만 보여주는 다이얼로그
   */
  public void showAPIErrorDialog(String message, DialogEventListener dialogEventListener) {
    if (dialogEventListener != null) {
      mDialogEventListener = dialogEventListener;
    }

    final RocateerDialog rocateerDialog = new RocateerDialog(mActivity);
    rocateerDialog.setDialog(message, getString(R.string.common_ok));
    rocateerDialog.addOKButton(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        rocateerDialog.dismiss();
        if (mDialogEventListener != null) {
          mDialogEventListener.onReceivedEvent();
          mDialogEventListener = null;
        }
      }
    });
  }

  /**
   * 스낵바 표시
   *
   * @param message
   */
  public void showSnackBar(String message) {
    if (mSnackbar == null) {
      mSnackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
      mSnackbar.show();
    } else {
      mSnackbar.setText(message);
      mSnackbar.show();
    }
  }


  /**
   * 프로그레스바 보여주기
   */
  public void showProgressBar() {
    if (mProgressBar == null) {
      mProgressBar = new ProgressBar(this, null, android.R.attr.progressBarStyle);
      mProgressBar.getIndeterminateDrawable().setColorFilter(mActivity.getColor(R.color.colorAccent), android.graphics.PorterDuff.Mode.MULTIPLY);
      mProgressBar.setBackgroundResource(android.R.color.transparent);
      RelativeLayout rl = new RelativeLayout(this);
      rl.setGravity(Gravity.CENTER);
      rl.setBackgroundResource(android.R.color.transparent);
      rl.addView(mProgressBar);

      RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
      ViewGroup layout = (ViewGroup) ((Activity) this).findViewById(android.R.id.content).getRootView();
      layout.addView(rl, params);

      mProgressBar.setIndeterminate(true);
    }

    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    mProgressBar.setVisibility(View.VISIBLE);
  }

  /**
   * 프로그레스바 숨기기
   */
  public void hideProgressBar() {
    if (mProgressBar != null) {
      mProgressBar.setVisibility(View.GONE);

      getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
  }

  /**
   * 키보드 숨기기
   *
   * @param activity
   */
  public void hideKeyboard(Activity activity) {
    InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
    View view = activity.getCurrentFocus();

    if (view == null) {
      view = new View(activity);
    }
    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }



  //--------------------------------------------------------------------------------------------
  // MARK : Bind Actions
  //--------------------------------------------------------------------------------------------}

  /**
   * 뒤로 가기
   */
  @Optional
  @OnClick(R.id.back_button)
  public void backTouched() {
    onBackPressed();
  }

  /**
   * 닫기
   */
  @Optional
  @OnClick(R.id.close_button)
  public void closeTouched() {
    onBackPressed();
  }

}
