package noommate.android.activity.main.schedule.history;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import noommate.android.R;
import noommate.android.activity.main.home.MateAdapter;
import noommate.android.activity.main.schedule.schedulepage.YoilAdapter;
import noommate.android.commons.DecorationHorizontal;
import noommate.android.commons.Tools;
import noommate.android.models.MemberModel;
import noommate.android.models.ScheduleModel;

public class TodoListAdapter extends BaseQuickAdapter<ScheduleModel, BaseViewHolder> {
    public TodoListAdapter(int layoutResId, @Nullable List<ScheduleModel> data) {
        super(layoutResId, data);
    }

    private RecyclerView mCharRecyclerView;
    private MateAdapter mSelectCharAdapter;
    private RecyclerView mYoilRecyclerView;
    private YoilAdapter mYoilAdapter;
    private ArrayList<ScheduleModel> mYoilList = new ArrayList<>();
    private ArrayList<MemberModel> mCharList = new ArrayList<>();

    @Override
    protected void convert(BaseViewHolder helper, ScheduleModel item) {
        helper.addOnClickListener(R.id.delete_button);
        mCharRecyclerView = helper.getView(R.id.char_recycler_view);
        mYoilRecyclerView = helper.getView(R.id.yoil_recycler_view);
        AppCompatImageView mDeleteImageView = helper.getView(R.id.delete_button);
        mDeleteImageView.setVisibility(View.GONE);


        initYoilAdapter();
        initSelectCharAdapter();

        mYoilList.clear();
        for (int i = 0; i < 7; i++) {
            mYoilList.add(new ScheduleModel());
        }

        if (item.getWeek_arr() != null) {
            String[] yoil = item.getWeek_arr().split(",");
            for (String value : yoil) {
                mYoilList.get(Integer.parseInt(value)).setSelected(true);
            }
        }
        mYoilAdapter.setNewData(mYoilList);


        if (item.getSchedule_member_list() != null) {
            mCharList.clear();
            mCharList.addAll(item.getSchedule_member_list());
        }
        mSelectCharAdapter.setNewData(mCharList);


    }

    /**
     * 요일 리스트
     */
    private void initYoilAdapter() {
        mYoilAdapter = new YoilAdapter(R.layout.row_yoil, mYoilList);
        mYoilRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        if (mYoilRecyclerView.getItemDecorationCount() <= 0) {
            mYoilRecyclerView.addItemDecoration(new DecorationHorizontal(mContext, Tools.getInstance().dpTopx(mContext, 8), Tools.getInstance().dpTopx(mContext, 16)));
        }
        mYoilRecyclerView.setAdapter(mYoilAdapter);
    }

    /**
     * 캐릭터 리스트
     */
    private void initSelectCharAdapter() {
        mSelectCharAdapter = new MateAdapter(R.layout.row_homemate, mCharList);
        mCharRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        if (mCharRecyclerView.getItemDecorationCount() <= 0) {
            mCharRecyclerView.addItemDecoration(new DecorationHorizontal(mContext, Tools.getInstance().dpTopx(mContext, 8), Tools.getInstance().dpTopx(mContext, 16)));
        }
        mCharRecyclerView.setAdapter(mSelectCharAdapter);
    }
}

