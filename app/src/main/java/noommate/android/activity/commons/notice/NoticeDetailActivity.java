package noommate.android.activity.commons.notice;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import noommate.android.R;
import noommate.android.activity.NoommateActivity;
import noommate.android.commons.Tools;
import noommate.android.models.NoticeModel;
import noommate.android.models.api.CommonRouter;


public class NoticeDetailActivity extends NoommateActivity {
  //--------------------------------------------------------------------------------------------
  // MARK : GET START INTENT
  //--------------------------------------------------------------------------------------------
  public static Intent getStartIntent(Context context, String noticeIdx) {
    Intent intent = new Intent(context, NoticeDetailActivity.class);
    mNoticeIdx = noticeIdx;
    return intent;
  }

  //--------------------------------------------------------------------------------------------
  // MARK : Bind Area
  //--------------------------------------------------------------------------------------------
  @BindView(R.id.title_text_view)
  AppCompatTextView mTitleTextView;
  @BindView(R.id.date_text_view)
  AppCompatTextView mDateTextView;
  @BindView(R.id.notice_image_view)
  AppCompatImageView mNoticeImageView;
  @BindView(R.id.content_text_view)
  AppCompatTextView mContentTextView;
  //--------------------------------------------------------------------------------------------
  // MARK : Local variables
  //--------------------------------------------------------------------------------------------
  public static String mNoticeIdx;

  //--------------------------------------------------------------------------------------------
  // MARK : Override
  //--------------------------------------------------------------------------------------------
  @Override
  protected int inflateLayout() {
    return R.layout.activity_notice_detail;
  }

  @Override
  protected void initLayout() {
    initToolbar("공지사항");
  }

  @Override
  protected void initRequest() {

    noticeDetailAPI();

  }

  //--------------------------------------------------------------------------------------------
  // MARK : Local functions
  //--------------------------------------------------------------------------------------------

  /**
   * API - 공지사항 리스트
   */
  private void noticeDetailAPI() {
    NoticeModel noticeRequest = new NoticeModel();
    noticeRequest.setNotice_idx(mNoticeIdx);

    CommonRouter.api().notice_detail(Tools.getInstance(mActivity).getMapper(noticeRequest)).enqueue(new Callback<NoticeModel>() {
      @Override
      public void onResponse(Call<NoticeModel> call, Response<NoticeModel> response) {
        if (Tools.getInstance(mActivity).isSuccessResponse(response)) {

          NoticeModel mNoticeModel = response.body();
          mTitleTextView.setText(mNoticeModel.getTitle());
          mDateTextView.setText(mNoticeModel.getIns_date());
          if (mNoticeModel.getImg_path() != null) {
            mNoticeImageView.setVisibility(View.VISIBLE);
            Glide.with(mActivity)
                    .load(mNoticeModel.getImg_path())
                    .into(mNoticeImageView);
          } else {
            mNoticeImageView.setVisibility(View.GONE);
          }

          mContentTextView.setText(mNoticeModel.getContents());
        }
      }

      @Override
      public void onFailure(Call<NoticeModel> call, Throwable t) {

      }
    });
  }


  //--------------------------------------------------------------------------------------------
  // MARK : Bind Actions
  //--------------------------------------------------------------------------------------------

}
