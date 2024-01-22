package noommate.android.activity.commons.notice;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import noommate.android.R;
import noommate.android.activity.RocateerActivity;
import noommate.android.commons.EmptyView;
import noommate.android.commons.Tools;
import noommate.android.commons.TouchDetectableScrollView;
import noommate.android.models.NoticeModel;
import noommate.android.models.api.CommonRouter;

public class NoticeListActivity extends RocateerActivity {
  //--------------------------------------------------------------------------------------------
  // MARK : GET START INTENT
  //--------------------------------------------------------------------------------------------
  public static Intent getStartIntent(Context context) {
    Intent intent = new Intent(context, NoticeListActivity.class);
    return intent;
  }

  //--------------------------------------------------------------------------------------------
  // MARK : Bind Area
  //--------------------------------------------------------------------------------------------
  @BindView(R.id.notice_list_recycler_view)
  public RecyclerView mNoticeListRecyclerView;
  //--------------------------------------------------------------------------------------------
  // MARK : Local variables
  //--------------------------------------------------------------------------------------------
  private NoticeListAdapter mNoticeListAdapter;
  private ArrayList<NoticeModel> mNoticeList = new ArrayList<>();
  private NoticeModel mNoticeResponse = new NoticeModel();

  //--------------------------------------------------------------------------------------------
  // MARK : Override
  //--------------------------------------------------------------------------------------------
  @Override
  protected int inflateLayout() {
    return R.layout.activity_notice_list;
  }

  @Override
  protected void initLayout() {
    initToolbar("공지사항");


  }

  @Override
  protected void initRequest() {
    initNoticeListAdapter();
  }

  //--------------------------------------------------------------------------------------------
  // MARK : Local functions
  //--------------------------------------------------------------------------------------------

  /**
   * 공지사항 리스트
   */
  private void initNoticeListAdapter() {
    mNoticeListAdapter = new NoticeListAdapter(R.layout.row_notice_list, mNoticeList);
    mNoticeListAdapter.setEmptyView(new EmptyView(mActivity, "새로운 공지사항이 없습니다."));
    mNoticeListRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    mNoticeListRecyclerView.setAdapter(mNoticeListAdapter);

    mNoticeListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent noticeDetailActivity = NoticeDetailActivity.getStartIntent(mActivity, mNoticeList.get(position).getNotice_idx());
        startActivity(noticeDetailActivity, TRANS.PUSH);
      }
    });

    mMoreScrollView.setMyScrollChangeListener(new TouchDetectableScrollView.OnMyScrollChangeListener() {
      @Override
      public void onBottomReached() {
        if (mNoticeResponse.isMore()) {
          noticeListAPI();
        }
      }
    });


    noticeListAPI();
  }

  /**
   * API - 공지사항 리스트
   */
  private void noticeListAPI() {
    NoticeModel noticeRequest = new NoticeModel();
    noticeRequest.setPage_num(mNoticeResponse.getNextPage());

    CommonRouter.api().notice_list(Tools.getInstance(mActivity).getMapper(noticeRequest)).enqueue(new Callback<NoticeModel>() {
      @Override
      public void onResponse(Call<NoticeModel> call, Response<NoticeModel> response) {
        if (Tools.getInstance(mActivity).isSuccessResponse(response)) {
          mNoticeResponse = response.body();
          mNoticeList.addAll(mNoticeResponse.getData_array());
          mNoticeListAdapter.setNewData(mNoticeList);
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