package noommate.android.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yinglan.keyboard.HideUtil;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Response;
import noommate.android.R;
import noommate.android.dialog.NoommateDialog;
import noommate.android.models.BaseListModel;
import noommate.android.models.BaseModel;
import noommate.android.commons.Tools;
import timber.log.Timber;

public abstract class NoommateFragment extends Fragment {
  //--------------------------------------------------------------------------------------------
  // MARK : Interface Area
  //-------------------------------------------------------------------------------------------
  protected abstract int inflateLayout();

  protected abstract void initLayout();

  protected abstract void initRequest();

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
  @BindView(R.id.back_button)
  public AppCompatImageView mBackButton;

  //--------------------------------------------------------------------------------------------
  // MARK : Local variables
  //--------------------------------------------------------------------------------------------
  private Unbinder mButterknife;
  public Context mContext;
  public NoommateActivity mActivity;
  private NoommateActivity.DialogEventListener mDialogEventListener;
  private Snackbar mSnackbar;

  //--------------------------------------------------------------------------------------------
  // MARK : Override
  //--------------------------------------------------------------------------------------------
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(this.inflateLayout(), container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mButterknife = ButterKnife.bind(this, view);
    mContext = getContext();
    mActivity = ((NoommateActivity) getActivity());

//
    HideUtil.init(mActivity);

    Tools.getInstance(mActivity).setSystemBarColor(mActivity, R.color.color_000000);
//    Tools.setSystemBarLight(mActivity);

    this.initLayout();
    this.initRequest();
  }

  @Override
  public void onResume() {
    super.onResume();
  }


  //--------------------------------------------------------------------------------------------
  // MARK : Local functions
  //--------------------------------------------------------------------------------------------

  /**
   * initToolbar - 타이틀과 백버튼이 보일 때
   *
   * @param title
   * @param isShownBack
   */
  public void initToolbar(String title, Boolean isShownBack) {
    if (mActionBarToolbar != null) {
      mActionBarToolbar.setTitle(title);
      mActivity.setSupportActionBar(mActionBarToolbar);
    }
    if (mActivity != null) {
      mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(isShownBack);
      mActivity.getSupportActionBar().setDisplayShowTitleEnabled(true);
    }
  }

  /**
   * Start Activity
   *
   * @param trans
   */
  public void startActivity(Intent intent, NoommateActivity.TRANS trans) {
    intent.putExtra("ANIMATION", trans.ordinal());
    super.startActivity(intent);
    switch (trans) {
      case PRESENT:
        getActivity().overridePendingTransition(R.anim.animation_present_in, R.anim.animation_nothing);
        break;
      case PUSH:
        getActivity().overridePendingTransition(R.anim.animation_push_in, R.anim.animation_nothing);
        break;
      case ZOOM:
        getActivity().overridePendingTransition(R.anim.animation_zoom_in, R.anim.animation_nothing);
        break;
    }
  }

  /**
   * Start Activity For Result
   *
   * @param intent
   */
  public void startActivityForResult(Intent intent, int requestCode, NoommateActivity.TRANS trans) {
    intent.putExtra("ANIMATION", trans.ordinal());
    super.startActivityForResult(intent, requestCode);
    switch (trans) {
      case PRESENT:
        getActivity().overridePendingTransition(R.anim.animation_present_in, R.anim.animation_nothing);
        break;
      case PUSH:
        getActivity().overridePendingTransition(R.anim.animation_push_in, R.anim.animation_nothing);
        break;
      case ZOOM:
        getActivity().overridePendingTransition(R.anim.animation_zoom_in, R.anim.animation_nothing);
        break;
    }
  }


  /**
   * 데이터 맵퍼
   *
   * @param object
   * @return
   */
  public Map<String, Object> getMapper(Object object) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    Map<String, Object> map = mapper.convertValue(object, Map.class);


    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Timber.d("PARAMETER :" + gson.toJson(map));

    return map;
  }

  /**
   * API 성공 체크
   *
   * @param response
   * @return
   */
  public boolean isSuccessResponse(Response<?> response) {
    String code = "";
    String code_msg = "";
    if (response != null && response.isSuccessful()) {
      if (response.body() instanceof BaseModel) {
        code = ((BaseModel) response.body()).getCode();
        code_msg = ((BaseModel) response.body()).getCode_msg();
      } else if (response.body() instanceof BaseListModel) {
        code = ((BaseListModel) response.body()).getCode();
        code_msg = ((BaseModel) response.body()).getCode_msg();
      }

      if (Integer.valueOf(code) > 0) {
        return true;
      }
    }
    if (response != null) {
//      showAPIErrorDialog(code_msg, null);
    }

    return false;
  }

  /**
   * 스낵
   *
   * @param message
   */
  public void showSnackBar(String message) {
    if (mSnackbar == null) {
      mSnackbar = Snackbar.make(getActivity().getWindow().getDecorView().findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
      mSnackbar.show();
    } else {
      mSnackbar.setText(message);
      mSnackbar.show();
    }
  }

//  public void showCustomToast(String msg) {
//    LayoutInflater inflater = getLayoutInflater();
//    View layout = inflater.inflate(R.layout.toast_main,(ViewGroup) getActivity().getWindow().getDecorView().findViewById(R.id.toast_layout));
//    AppCompatTextView text = (AppCompatTextView) layout.findViewById(R.id.toast_text);
//    text.setText(msg);
//
//    Toast toast = new Toast(getActivity());
//    toast.setGravity(Gravity.BOTTOM | Gravity.LEFT | Gravity.FILL_HORIZONTAL, 0, 0);
//    toast.setDuration(Toast.LENGTH_LONG);
//    toast.setView(layout);
//    toast.show();
//  }

  /**
   * 확인 취소가 있는 경우
   *
   * @param message
   * @param cancelTitle
   * @param confirmTitle
   * @param dialogEventListener
   */
  public void showConfirmDialog(String message, String cancelTitle, String confirmTitle, NoommateActivity.DialogEventListener dialogEventListener) {
    if (dialogEventListener != null) {
      mDialogEventListener = dialogEventListener;
    }

    final NoommateDialog noommateDialog = new NoommateDialog(mActivity);
    noommateDialog.setDialog(message, confirmTitle, cancelTitle);
    noommateDialog.addOKButton(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        noommateDialog.dismiss();
        if (mDialogEventListener != null) {
          mDialogEventListener.onReceivedEvent();
          mDialogEventListener = null;
        }

      }
    });
    noommateDialog.addCancelButton(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        noommateDialog.dismiss();
      }
    });
  }

  /**
   * 확인 취소가 없는 경우
   *
   * @param message
   * @param confirmTitle
   * @param dialogEventListener
   */
  public void showAlertDialog(String message, String confirmTitle, NoommateActivity.DialogEventListener dialogEventListener) {
    if (dialogEventListener != null) {
      mDialogEventListener = dialogEventListener;
    }

    final NoommateDialog noommateDialog = new NoommateDialog(mActivity);
    noommateDialog.setDialog(message, confirmTitle);
    noommateDialog.addOKButton(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        noommateDialog.dismiss();
        if (mDialogEventListener != null) {
          mDialogEventListener.onReceivedEvent();
          mDialogEventListener = null;
        }
      }
    });


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
  //--------------------------------------------------------------------------------------------

}