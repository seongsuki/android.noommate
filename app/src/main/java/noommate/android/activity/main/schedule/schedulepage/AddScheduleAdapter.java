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
import timber.log.Timber;

public class AddScheduleAdapter extends BaseQuickAdapter<ScheduleModel, BaseViewHolder> {
    public AddScheduleAdapter(int layoutResId, @Nullable List<ScheduleModel> data) {
        super(layoutResId, data);
    }

    private RecyclerView mCharRecyclerView;
    private MateAdapter mSelectCharAdapter;
    private ArrayList<MemberModel> mCharList = new ArrayList<>();
    private RecyclerView mYoilRecyclerView;

    private YoilAdapter mYoilAdapter;
    private ArrayList<ScheduleModel> mYoilList =new ArrayList<>();

    @Override
    protected void convert(BaseViewHolder helper, ScheduleModel item) {

        helper.addOnClickListener(R.id.delete_button);

        mCharRecyclerView = helper.getView(R.id.char_recycler_view);
        mYoilRecyclerView = helper.getView(R.id.yoil_recycler_view);

        initSelectCharAdapter();
        initYoilAdapter();

        mCharList.clear();
        mSelectCharAdapter.setNewData(mCharList);
        mCharList.addAll(item.getSchedule_member_list());


        mYoilList.clear();
        for (int i = 0; i < 7; i++) {
            mYoilList.add(new ScheduleModel());
        }
        if (item.getWeek_arr().length() > 0) {
            if (item.getWeek_arr() != null) {
                String[] yoil = item.getWeek_arr().split(",");
                for (String value : yoil) {
                    mYoilList.get(Integer.parseInt(value) - 1).setSelected(true);
                }
            }
        }

        mYoilAdapter.setNewData(mYoilList);

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
        mCharRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,false));
        if (mCharRecyclerView.getItemDecorationCount() <= 0) {
            mCharRecyclerView.addItemDecoration(new DecorationHorizontal(mContext, Tools.getInstance().dpTopx(mContext, 8), Tools.getInstance().dpTopx(mContext, 16)));
        }
        mCharRecyclerView.setAdapter(mSelectCharAdapter);
    }
}
