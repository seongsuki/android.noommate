package noommate.android.activity.main.home;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

import noommate.android.R;
import noommate.android.activity.NoommateActivity;
import noommate.android.commons.Constants;
import noommate.android.commons.Tools;
import noommate.android.dialog.CocDialog;
import noommate.android.models.BaseModel;
import noommate.android.models.MemberModel;
import noommate.android.models.api.CommonRouter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class HomeScheduleListAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public interface OnHomeScheduleListener {
        void OnRefresh();
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------
    public static final int SCHEDULE_LIST = 0;
    public static final int SCHEDULE_DETAIL = 1;
    private static OnHomeScheduleListener mOnHomeScheduleListener;

    private ArrayList<BaseModel> mDetailList = new ArrayList<>();
    private ScheduleMateAdapter mScheduleMateAdapter;
    private String mTitle;

    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    public HomeScheduleListAdapter(List<MultiItemEntity> data, OnHomeScheduleListener onHomeScheduleListener) {
        super(data);
        mOnHomeScheduleListener = onHomeScheduleListener;
        addItemType(SCHEDULE_LIST, R.layout.row_schedule_list);
        addItemType(SCHEDULE_DETAIL, R.layout.row_schedule_detail);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case SCHEDULE_LIST:
                final HomeScheduleListItem homeScheduleListItem = (HomeScheduleListItem) item;
                helper.setText(R.id.category_text_view, homeScheduleListItem.title);
                mTitle = homeScheduleListItem.title;
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (homeScheduleListItem.isExpanded()) {
                            collapse(pos, true);
                        } else {
                            expand(pos, true);
                        }
                    }
                });
                break;
            case SCHEDULE_DETAIL:
                final HomeScheduleDetailItem homeScheduleDetailItem = (HomeScheduleDetailItem) item;
                RecyclerView mDetailListRecyclerView = helper.getView(R.id.mate_recycler_view);
                mScheduleMateAdapter = new ScheduleMateAdapter(R.layout.row_schedule_mate, ((HomeScheduleDetailItem) item).scheduleModel.getSchedule_item_member_list());
                mScheduleMateAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        if (view.getId() == R.id.coc_button) {
                            if (((HomeScheduleDetailItem) item).scheduleModel.getSchedule_item_member_list().get(position).getMy_yn().equals("Y")) {
                                todayScheduleEndAPI(((HomeScheduleDetailItem) item).scheduleModel.getSchedule_item_member_list().get(position).getSchedule_idx());
                            } else {
                                CocDialog cocDialog = new CocDialog(mContext,((HomeScheduleDetailItem) item).scheduleModel.getSchedule_item_member_list().get(position).getMember_role1(),((HomeScheduleDetailItem) item).scheduleModel.getSchedule_item_member_list().get(position).getMember_role2(),((HomeScheduleDetailItem) item).scheduleModel.getSchedule_item_member_list().get(position).getMember_role3(),
                                        mTitle,((HomeScheduleDetailItem) item).scheduleModel.getSchedule_item_member_list().get(position).getMember_nickname(), ((HomeScheduleDetailItem) item).scheduleModel.getSchedule_item_member_list().get(position).getCoc_cnt(), new CocDialog.OnCocListener() {
                                    @Override
                                    public void onCoc(String cocCnt) {
                                        ((HomeScheduleDetailItem) item).scheduleModel.getSchedule_item_member_list().get(position).setCoc_cnt(cocCnt);
                                        mOnHomeScheduleListener.OnRefresh();
                                        Intent feedRefresh = new Intent(Constants.HOME_REFRESH);
                                        mContext.sendBroadcast(feedRefresh);
                                    }
                                });
                                cocDialog.show();
                            }
                        }
                    }
                });
                mDetailListRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                mDetailListRecyclerView.setAdapter(mScheduleMateAdapter);

                mDetailList.clear();

                break;
        }
    }

    /**
     * 오늘 할일 완료하기 API
     */
    private void todayScheduleEndAPI(String scheduleIdx) {
        MemberModel memberRequest = new MemberModel();
        memberRequest.setSchedule_idx(scheduleIdx);
        CommonRouter.api().today_schedule_end(Tools.getInstance().getMapper(memberRequest)).enqueue(new Callback<MemberModel>() {
            @Override
            public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
                mOnHomeScheduleListener.OnRefresh();
                Intent feedRefresh = new Intent(Constants.HOME_REFRESH);
                mContext.sendBroadcast(feedRefresh);

            }

            @Override
            public void onFailure(Call<MemberModel> call, Throwable t) {

            }
        });
    }
}


