package noommate.android.activity.main.home;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

import noommate.android.R;
import noommate.android.models.BaseModel;

public class HomeScheduleListAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    //--------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------
    public static final int SCHEDULE_LIST = 0;
    public static final int SCHEDULE_DETAIL = 1;

    private ArrayList<BaseModel> mDetailList = new ArrayList<>();
    private ScheduleMateAdapter mScheduleMateAdapter;

    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    public HomeScheduleListAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(SCHEDULE_LIST, R.layout.row_schedule_list);
        addItemType(SCHEDULE_DETAIL, R.layout.row_schedule_detail);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case SCHEDULE_LIST:
                final HomeScheduleListItem homeScheduleListItem = (HomeScheduleListItem) item;
                helper.setText(R.id.category_text_view, homeScheduleListItem.title);

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
                mDetailListRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                mDetailListRecyclerView.setAdapter(mScheduleMateAdapter);

                mDetailList.clear();

                break;
        }
    }
}


