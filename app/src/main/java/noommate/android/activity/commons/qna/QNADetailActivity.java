package noommate.android.activity.commons.qna;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;

import com.pixplicity.easyprefs.library.Prefs;

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


public class QNADetailActivity extends RocateerActivity {
  public interface QaDetailListener {
    void onResult();
  }

  //--------------------------------------------------------------------------------------------
  // MARK : GET START INTENT
  //--------------------------------------------------------------------------------------------
  public static Intent getStartIntent(Context context, String qaIdx, QaDetailListener qaDetailListener) {
    Intent intent = new Intent(context, QNADetailActivity.class);
    mQaIdx = qaIdx;
    mQaDetailListener = qaDetailListener;
    return intent;
  }

  //--------------------------------------------------------------------------------------------
  // MARK : Bind Area
  //--------------------------------------------------------------------------------------------

  @BindView(R.id.title_text_view)
  AppCompatTextView mTitleTextView;
  @BindView(R.id.contents_text_view)
  AppCompatTextView mContentsTextView;
  @BindView(R.id.category_text_view)
  AppCompatTextView mCategoryTextView;
  @BindView(R.id.date_text_view)
  AppCompatTextView mDateTextView;

  @BindView(R.id.reply_layout)
  LinearLayout mReplyLinearLayout;
  @BindView(R.id.reply_date_text_view)
  AppCompatTextView mReplyDateTextView;
  @BindView(R.id.reply_contents_text_view)
  AppCompatTextView mReplyTextView;

  //--------------------------------------------------------------------------------------------
  // MARK : Local variables
  //--------------------------------------------------------------------------------------------
  private static String mQaIdx;
  private static QaDetailListener mQaDetailListener;

  //--------------------------------------------------------------------------------------------
  // MARK : Override
  //--------------------------------------------------------------------------------------------
  @Override
  protected int inflateLayout() {
    return R.layout.activity_qna_detail;
  }

  @Override
  protected void initLayout() {
    initToolbar("1:1 문의 상세");

  }

  @Override
  protected void initRequest() {
    qaDetailAPI();

  }

  //--------------------------------------------------------------------------------------------
  // MARK : Local functions
  //--------------------------------------------------------------------------------------------

  /**
   * QA 상세 API
   */
  private void qaDetailAPI() {
    QnaModel qnaModel = new QnaModel();
    qnaModel.setMember_idx(Prefs.getString(Constants.MEMBER_IDX, ""));
    qnaModel.setQa_idx(mQaIdx);
    CommonRouter.api().qa_detail(Tools.getInstance().getMapper(qnaModel)).enqueue(new Callback<QnaModel>() {
      @Override
      public void onResponse(Call<QnaModel> call, Response<QnaModel> response) {
        if (Tools.getInstance().isSuccessResponse(response)) {
          QnaModel qnaResponse = response.body();
          mTitleTextView.setText(qnaResponse.getQa_title());
          mContentsTextView.setText(qnaResponse.getQa_contents());
          mDateTextView.setText(qnaResponse.getIns_date());
          if (qnaResponse.getQa_type().equals("0")) {
            mCategoryTextView.setText("불편신고");
          } else if (qnaResponse.getQa_type().equals("1")) {
            mCategoryTextView.setText("제보");
          } else {
            mCategoryTextView.setText("기타");

          }

          if (qnaResponse.getReply_yn().equals("Y")) {
            mReplyLinearLayout.setVisibility(View.VISIBLE);
            mReplyTextView.setText(qnaResponse.getReply_contents());
            mReplyDateTextView.setText(qnaResponse.getReply_date());
          } else {
            mReplyLinearLayout.setVisibility(View.GONE);
          }
        }
      }

      @Override
      public void onFailure(Call<QnaModel> call, Throwable t) {
        showErrorDialog();
      }
    });
  }

  /**
   * QA 삭제 API
   */
  private void qaDelAPI() {
    QnaModel qnaModel = new QnaModel();
    qnaModel.setMember_idx(Prefs.getString(Constants.MEMBER_IDX, ""));
    qnaModel.setQa_idx(mQaIdx);
    CommonRouter.api().qa_del(Tools.getInstance().getMapper(qnaModel)).enqueue(new Callback<QnaModel>() {
      @Override
      public void onResponse(Call<QnaModel> call, Response<QnaModel> response) {
        if (Tools.getInstance().isSuccessResponse(response)) {
          mQaDetailListener.onResult();
          finishWithRemove();
        }
      }

      @Override
      public void onFailure(Call<QnaModel> call, Throwable t) {
        showErrorDialog();
      }
    });
  }


  //--------------------------------------------------------------------------------------------
  // MARK : Bind Actions
  //--------------------------------------------------------------------------------------------

  /**
   * 1:1 문의 삭제
   */
  @OnClick(R.id.delete_button)
  public void qnaDeleteTouched() {
    showConfirmDialog("헤딩 문의글을 삭제 하시겠습니까? 삭제하시면 해당 글은 다시 복구 할 수 없습니다.", getString(R.string.common_cancel), getString(R.string.common_ok), new RocateerActivity.DialogEventListener() {
      @Override
      public void onReceivedEvent() {
        qaDelAPI();
      }
    });
  }

}
