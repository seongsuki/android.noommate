package noommate.android.activity.main.schedule.schedulepage;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import noommate.android.R;
import noommate.android.activity.main.home.MateAdapter;
import noommate.android.commons.DecorationHorizontal;
import noommate.android.commons.Tools;
import noommate.android.models.MemberModel;
import noommate.android.models.ScheduleModel;

public class ScheduleAdapter extends BaseQuickAdapter<ScheduleModel, BaseViewHolder> {
    public ScheduleAdapter(int layoutResId, @Nullable List<ScheduleModel> data) {
        super(layoutResId, data);
    }

    private MateAdapter mMateAdapter;
    private ArrayList<MemberModel> mMateList = new ArrayList<>();
    private RecyclerView mMateRecyclerView;

    @Override
    protected void convert(BaseViewHolder helper, ScheduleModel item) {

        mMateRecyclerView = helper.getView(R.id.mate_recycler_view);
        helper.setText(R.id.title_text_view, item.getPlan_name());


        mMateList.clear();
        if (item.getMember_list() != null) {
            mMateList.addAll(item.getMember_list());
        } else {
            mMateList.addAll(item.getSchedule_item_member_list());
        }

        mMateAdapter = new MateAdapter(R.layout.row_homemate, mMateList);
        mMateRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        if (mMateRecyclerView.getItemDecorationCount() <= 0) {
            mMateRecyclerView.addItemDecoration(new DecorationHorizontal(mContext, Tools.getInstance().dpTopx(mContext, 8), Tools.getInstance().dpTopx(mContext, 16)));
        }
        mMateRecyclerView.setAdapter(mMateAdapter);
        mMateAdapter.notifyDataSetChanged();

}

}
