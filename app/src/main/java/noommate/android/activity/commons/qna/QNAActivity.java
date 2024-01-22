package noommate.android.activity.commons.qna;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import noommate.android.R;
import noommate.android.activity.RocateerActivity;
import noommate.android.commons.Constants;
import noommate.android.commons.EmptyView;
import noommate.android.commons.Tools;
import noommate.android.models.QnaModel;
import noommate.android.models.api.CommonRouter;


public class QNAActivity extends RocateerActivity {
  //--------------------------------------------------------------------------------------------
  // MARK : GET START INTENT
  //--------------------------------------------------------------------------------------------
  public static Intent getStartIntent(Context context) {
    Intent intent = new Intent(context, QNAActivity.class);
    return intent;
  }

  //--------------------------------------------------------------------------------------------
  // MARK : Bind Area
  //--------------------------------------------------------------------------------------------
  @BindView(R.id.qna_list_recycler_view)
  public RecyclerView mQnaListRecyclerView;

  //--------------------------------------------------------------------------------------------
  // MARK : Local variables
  //--------------------------------------------------------------------------------------------
  private QNAAdapter mQnaAdapter;
  private ArrayList<QnaModel> mQnaList = new ArrayList<>();
  private QnaModel mQnaResponse = new QnaModel();

  //--------------------------------------------------------------------------------------------
  // MARK : Override
  //--------------------------------------------------------------------------------------------
  @Override
  protected int inflateLayout() {
    return R.layout.activity_qna_list;
  }

  @Override
  protected void initLayout() {

    mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        mRefreshLayout.setRefreshing(false);
      }
    });



  }

  @Override
  protected void initRequest() {
    initQnaListAdapter();
  }

  //--------------------------------------------------------------------------------------------
  // MARK : Local functions
  //--------------------------------------------------------------------------------------------

  /**
   * 1;1문의 리스트
   */
  private void initQnaListAdapter() {
    mQnaAdapter = new QNAAdapter(R.layout.row_qna_list, mQnaList);
    mQnaAdapter.setEmptyView(new EmptyView(mActivity, "궁금하신 내용이 자주묻는질문(FAQ)으로는\n해결이 어려우신가요?\n\n문의 글을 작성하시면 확인 후에 답변을 드립니다."));
    mQnaAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent qnaDetailActivity = QNADetailActivity.getStartIntent(mActivity, mQnaList.get(position).getQa_idx(), new QNADetailActivity.QaDetailListener() {
          @Override
          public void onResult() {
            mQnaList.clear();
            mQnaResponse.resetPage();
            qaListAPI();
          }
        });
        startActivity(qnaDetailActivity, TRANS.PUSH);
      }
    });

    mQnaListRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    mQnaListRecyclerView.setAdapter(mQnaAdapter);

    mQnaAdapter.setNewData(mQnaList);
    qaListAPI();
  }

  /**
   * QA 리스트 API
   */
  private void qaListAPI() {
    QnaModel qnaRequest = new QnaModel();
    qnaRequest.setMember_idx(Prefs.getString(Constants.MEMBER_IDX, ""));
    qnaRequest.setPage_num(mQnaResponse.getNextPage());
    CommonRouter.api().qa_list(Tools.getInstance().getMapper(qnaRequest)).enqueue(new Callback<QnaModel>() {
      @Override
      public void onResponse(Call<QnaModel> call, Response<QnaModel> response) {
        if (Tools.getInstance().isSuccessResponse(response)) {
          mQnaResponse = response.body();
          if (mQnaResponse.getData_array() != null) {
            mQnaList.addAll(mQnaResponse.getData_array());
          }
          mQnaAdapter.setNewData(mQnaList);
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
   * 등록
   */
  @OnClick(R.id.add_button)
  public void enrollTouched() {
    Intent addQnaActivity = AddQnaActivity.getStartIntent(mActivity, new AddQnaActivity.QaAddListener() {
      @Override
      public void onResult() {
        mQnaList.clear();
        mQnaResponse.resetPage();
        qaListAPI();

        showAlertDialog("관리자가 문의 하신 내용 확인 후 답변 드리도록 하겠습니다.", "확인", null);
      }
    });
    startActivity(addQnaActivity, TRANS.PUSH);
  }

}
