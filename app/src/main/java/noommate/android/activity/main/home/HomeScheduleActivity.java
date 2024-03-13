package noommate.android.activity.main.home;



import static com.google.firebase.messaging.Constants.MessagePayloadKeys.SENDER_ID;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.service.notification.StatusBarNotification;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.FileInputStream;
import java.lang.reflect.Member;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import noommate.android.activity.NoommateActivity;
import noommate.android.models.AlarmModel;
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
import timber.log.Timber;

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
        mHomeScheduleListAdapter = new HomeScheduleListAdapter(mHomeScheduleList,mActivity, new HomeScheduleListAdapter.OnHomeScheduleListener() {
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

