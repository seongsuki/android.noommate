package noommate.android.activity.commons.faq;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;

import butterknife.BindView;
import noommate.android.R;
import noommate.android.activity.NoommateActivity;
import noommate.android.commons.Tools;
import noommate.android.models.FaqModel;
import noommate.android.models.api.CommonRouter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FAQListActivity extends NoommateActivity {
  //--------------------------------------------------------------------------------------------
  // MARK : GET START INTENT
  //--------------------------------------------------------------------------------------------
  public static Intent getStartIntent(Context context) {
    Intent intent = new Intent(context, FAQListActivity.class);
    return intent;
  }

  //--------------------------------------------------------------------------------------------
  // MARK : Bind Area
  //--------------------------------------------------------------------------------------------
  @BindView(R.id.faq_recycler_view)
  public RecyclerView mFaqRecyclerView;
  //--------------------------------------------------------------------------------------------
  // MARK : Local variables
  //--------------------------------------------------------------------------------------------
  FAQListAdapter mFaqListAdapter;
  ArrayList<MultiItemEntity> mFaqList = new ArrayList<>();
  FaqModel faqResponse = new FaqModel();

  //--------------------------------------------------------------------------------------------
  // MARK : Override
  //--------------------------------------------------------------------------------------------
  @Override
  protected int inflateLayout() {
    return R.layout.activity_faq_list;
  }

  @Override
  protected void initLayout() {
    initToolbar("FAQ");
  }

  @Override
  protected void initRequest() {
    initFAQAdapter();

  }

  //--------------------------------------------------------------------------------------------
  // MARK : Local functions
  //--------------------------------------------------------------------------------------------

  /**
   * FAQ 리스트 API
   */
  private void faqListAPI() {
    FaqModel faqModel = new FaqModel();
    faqModel.setPage_num(faqResponse.getNextPage());
    CommonRouter.api().faq_list(Tools.getInstance(mActivity).getMapper(faqModel)).enqueue(new Callback<FaqModel>() {
      @Override
      public void onResponse(Call<FaqModel> call, Response<FaqModel> response) {
        faqResponse = response.body();
        if (Tools.getInstance(mActivity).isSuccessResponse(response)) {
          mFaqList.clear();
          for (FaqModel value : faqResponse.getData_array()) {
            FAQListItem faqListItem = new FAQListItem(value.getTitle());
            FAQDetailItem faqDetailItem = new FAQDetailItem(value.getContents());
            faqListItem.addSubItem(faqDetailItem);
            mFaqList.add(faqListItem);
          }
          mFaqListAdapter.setNewData(mFaqList);
        }
      }

      @Override
      public void onFailure(Call<FaqModel> call, Throwable t) {

      }
    });
  }

  /**
   * FAQ 어뎁터
   */
  private void initFAQAdapter() {

    mFaqListAdapter = new FAQListAdapter(mFaqList);
    mFaqRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
    mFaqRecyclerView.setAdapter(mFaqListAdapter);
    faqListAPI();
  }
  //--------------------------------------------------------------------------------------------
  // MARK : Bind Actions
  //--------------------------------------------------------------------------------------------

}