package noommate.android.activity.main.home;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import noommate.android.activity.NoommateActivity;
import noommate.android.models.MemberModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import noommate.android.R;
import noommate.android.commons.Constants;
import noommate.android.commons.EmptyView;
import noommate.android.commons.Tools;
import noommate.android.models.ScheduleModel;
import noommate.android.models.api.CommonRouter;

public class HomeScheduleActivity extends NoommateActivity {
    //--------------------------------------------------------------------------------------------
    // MARK : GET START INTENT
    //--------------------------------------------------------------------------------------------
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, HomeScheduleActivity.class);
        return intent;
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Area
    //--------------------------------------------------------------------------------------------
    @BindView(R.id.schedule_recycler_view)
    RecyclerView mScheduleRecyclerView;

    //--------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------
    private ArrayList<MultiItemEntity> mHomeScheduleList = new ArrayList<>();
    private HomeScheduleListAdapter mHomeScheduleListAdapter;
    private Date mNowDate = new Date();
    private SimpleDateFormat sdf = new SimpleDateFormat("MM월 dd일 (E)");

    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    @Override
    protected int inflateLayout() {
        return R.layout.activity_home_schedule;
    }

    @Override
    protected void initLayout() {
        mToolbarTitle.setText(sdf.format(mNowDate));


    }

    @Override
    protected void initRequest() {
        initHomeScheduleListAdapter();
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Local functions
    //--------------------------------------------------------------------------------------------

    /**
     * 일정 리스트
     */
    private void initHomeScheduleListAdapter() {
        mHomeScheduleListAdapter = new HomeScheduleListAdapter(mHomeScheduleList, new HomeScheduleListAdapter.OnHomeScheduleListener() {
            @Override
            public void OnRefresh() {
                mHomeScheduleList.clear();
                todayScheduleListAPI();
            }
        });
        mHomeScheduleListAdapter.setEmptyView(new EmptyView(mActivity, "오늘은 하우스 일정이 없어요."));
        mScheduleRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mScheduleRecyclerView.setAdapter(mHomeScheduleListAdapter);
        todayScheduleListAPI();
    }

    /**
     * 오늘 할일 리스트 API
     */
    private void todayScheduleListAPI() {
        ScheduleModel scheduleRequest = new ScheduleModel();
        scheduleRequest.setMember_idx(Prefs.getString(Constants.MEMBER_IDX, ""));
        scheduleRequest.setHouse_idx(Prefs.getString(Constants.HOUSE_IDX, ""));
        CommonRouter.api().today_schedule_list(Tools.getInstance().getMapper(scheduleRequest)).enqueue(new Callback<ScheduleModel>() {
            @Override
            public void onResponse(Call<ScheduleModel> call, Response<ScheduleModel> response) {
                ScheduleModel mScheduleResponse = response.body();
                if (Tools.getInstance(mActivity).isSuccessResponse(response)) {
                    if (mScheduleResponse.getData_array() != null) {
                        for (ScheduleModel value : mScheduleResponse.getData_array()) {
                            for (MemberModel member : value.getSchedule_item_member_list()) {
                                member.setCoc_cnt("3");
                            }
                            HomeScheduleListItem homeScheduleListItem = new HomeScheduleListItem(value.getPlan_name());
                            HomeScheduleDetailItem homeScheduleDetailItem = new HomeScheduleDetailItem(value);
                            homeScheduleListItem.addSubItem(homeScheduleDetailItem);
                            mHomeScheduleList.add(homeScheduleListItem);
                        }
                    }
                    mHomeScheduleListAdapter.setNewData(mHomeScheduleList);
                }
            }

            @Override
            public void onFailure(Call<ScheduleModel> call, Throwable t) {

            }
        });
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Actions
    //--------------------------------------------------------------------------------------------


}

