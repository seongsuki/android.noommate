package noommate.android.activity.commons.qna;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.widget.AppCompatEditText;

import com.pixplicity.easyprefs.library.Prefs;
import com.skydoves.powerspinner.PowerSpinnerView;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import noommate.android.R;
import noommate.android.activity.RocateerActivity;
import noommate.android.commons.Constants;
import noommate.android.commons.Tools;
import noommate.android.models.QnaModel;
import noommate.android.models.api.CommonRouter;


public class AddQnaActivity extends RocateerActivity {
  public interface QaAddListener {
    void onResult();
  }
  //--------------------------------------------------------------------------------------------
  // MARK : GET START INTENT
  //--------------------------------------------------------------------------------------------

  public static Intent getStartIntent(Context context, QaAddListener qaAddListener) {
    Intent intent = new Intent(context, AddQnaActivity.class);
    mQaAddListener = qaAddListener;
    return intent;
  }

  //--------------------------------------------------------------------------------------------
  // MARK : Bind Area
  //--------------------------------------------------------------------------------------------
  @BindView(R.id.add_title_edit_text)
  AppCompatEditText mAddTitleEditText;
  @BindView(R.id.add_contents_edit_text)
  AppCompatEditText mAddContestsEditText;

  @BindView(R.id.category_spinner)
  PowerSpinnerView mCategorySpinner;
  //--------------------------------------------------------------------------------------------
  // MARK : Local variables
  //--------------------------------------------------------------------------------------------
  private static QaAddListener mQaAddListener;

  //--------------------------------------------------------------------------------------------
  // MARK : Override
  //--------------------------------------------------------------------------------------------
  @Override
  protected int inflateLayout() {
    return R.layout.activity_add_qna;
  }

  @Override
  protected void initLayout() {
    mToolbarTitle.setText("1:1 문의 등록");

  }

  @Override
  protected void initRequest() {

  }

  //--------------------------------------------------------------------------------------------
  // MARK : Local functions
  //--------------------------------------------------------------------------------------------

  /**
   * QNA 등록
   */
  private void qaRegInAPI() {
    QnaModel qnaModel = new QnaModel();
    qnaModel.setMember_idx(Prefs.getString(Constants.MEMBER_IDX, ""));
    qnaModel.setQa_title(mAddTitleEditText.getText().toString());
    qnaModel.setQa_contents(mAddContestsEditText.getText().toString());
    qnaModel.setQa_type(String.valueOf(mCategorySpinner.getSelectedIndex()));
    CommonRouter.api().qa_reg_in(Tools.getInstance().getMapper(qnaModel)).enqueue(new Callback<QnaModel>() {
      @Override
      public void onResponse(Call<QnaModel> call, Response<QnaModel> response) {
        if (Tools.getInstance().isSuccessResponse(response)) {
          mQaAddListener.onResult();
          finishWithRemove();
        }
      }

      @Override
      public void onFailure(Call<QnaModel> call, Throwable t) {

      }
    });
  }
  //--------------------------------------------------------------------------------------------
  // MARK : Bind Actions
  //--------------------------------------------------------------------------------------------

  /**
   * 1:1 문의 등록
   */
  @OnClick(R.id.add_qna_round_rect_view)
  public void addQNATouched() {
    qaRegInAPI();
  }
}
