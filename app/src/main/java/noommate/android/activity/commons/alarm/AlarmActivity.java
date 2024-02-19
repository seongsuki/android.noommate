package noommate.android.activity.commons.alarm;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.gms.ads.MobileAds;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import butterknife.OnClick;
import noommate.android.activity.main.MainActivity;
import noommate.android.activity.main.home.HomeScheduleActivity;
import noommate.android.activity.main.home.NoteActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import butterknife.BindView;
import noommate.android.R;
import noommate.android.activity.NoommateActivity;
import noommate.android.commons.Constants;
import noommate.android.commons.EmptyView;
import noommate.android.commons.Tools;
import noommate.android.commons.TouchDetectableScrollView;
import noommate.android.models.AlarmModel;
import noommate.android.models.api.CommonRouter;

public class AlarmActivity extends NoommateActivity {
  //--------------------------------------------------------------------------------------------
  // MARK : GET START INTENT
  //--------------------------------------------------------------------------------------------
  public static Intent getStartIntent(Context context) {
    Intent intent = new Intent(context, AlarmActivity.class);
    return intent;
  }

  //--------------------------------------------------------------------------------------------
  // MARK : Bind Area
  //--------------------------------------------------------------------------------------------
  @BindView(R.id.alarm_recycler_view)
  RecyclerView mAlarmRecyclerView;

  //--------------------------------------------------------------------------------------------
  // MARK : Local variables
  //--------------------------------------------------------------------------------------------
  private AlarmAdapter mAlarmAdapter;
  private ArrayList<AlarmModel> mAlarmList = new ArrayList<>();
  private AlarmModel mAlarmResponse = new AlarmModel();

  //--------------------------------------------------------------------------------------------
  // MARK : Override
  //--------------------------------------------------------------------------------------------
  @Override
  protected int inflateLayout() {
    return R.layout.activity_alarm;
  }

  @Override
  protected void initLayout() {
    initToolbar("알림");

  }

  @Override
  protected void initRequest() {
    initAlarmAdapter();

  }

//  @Override
//  protected void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    // ...
//    MobileAds.initialize(mActivity);
//  }


  //--------------------------------------------------------------------------------------------
  // MARK : Local functions
  //--------------------------------------------------------------------------------------------

  /**
   * 알림 리스트
   */
  private void initAlarmAdapter() {
    mAlarmAdapter = new AlarmAdapter(R.layout.row_alarm, mAlarmList);
    mAlarmAdapter.setEmptyView(new EmptyView(mActivity, "새로운 알림이 없습니다."));
    mAlarmAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        AlarmModel alarmData = mAlarmList.get(position).getData();
        switch (alarmData.getIndex()) {
          case "101":
          case "105":
          case "201":
            Intent mainActivity = MainActivity.getStartIntent(mActivity);
            startActivity(mainActivity, TRANS.ZOOM);
            break;
          case "102":
            Intent noteActivity = NoteActivity.getStartIntent(mActivity);
            startActivity(noteActivity, TRANS.PUSH);

            break;
          case "103":
          case "104":
            Intent homeScheduleActivity = HomeScheduleActivity.getStartIntent(mActivity);
            startActivity(homeScheduleActivity, TRANS.PUSH);
            break;
        }

      }
    });
    mAlarmAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
      @Override
      public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.del_button) {
          alarmDelAPI(mAlarmList.get(position).getAlarm_idx());
        }
      }
    });
    mAlarmRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
    mAlarmRecyclerView.setAdapter(mAlarmAdapter);
    mMoreScrollView.setMyScrollChangeListener(new TouchDetectableScrollView.OnMyScrollChangeListener() {
      @Override
      public void onBottomReached() {
        if (mAlarmResponse.isMore()) {
          alarmListAPI();
        }
      }
    });
    alarmListAPI();
  }

  /**
   * 알림 리스트
   */
  private void alarmListAPI() {
    AlarmModel alarmModel = new AlarmModel();
    alarmModel.setMember_idx(Prefs.getString(Constants.MEMBER_IDX, ""));
    alarmModel.setPage_num(mAlarmResponse.getPage_num());

    CommonRouter.api().alarm_list(Tools.getInstance().getMapper(alarmModel)).enqueue(new Callback<AlarmModel>() {
      @Override
      public void onResponse(Call<AlarmModel> call, Response<AlarmModel> response) {
        if (Tools.getInstance().isSuccessResponse(response)) {
          mAlarmResponse = response.body();
          mAlarmList.addAll(mAlarmResponse.getData_array());
          mAlarmAdapter.setNewData(mAlarmList);

        }
      }

      @Override
      public void onFailure(Call<AlarmModel> call, Throwable t) {

      }
    });
  }

  /**
   * 알림 삭제
   *
   * @param alarmIdx
   */
  private void alarmDelAPI(String alarmIdx) {
    AlarmModel alarmModel = new AlarmModel();
    alarmModel.setAlarm_idx(alarmIdx);

    CommonRouter.api().alarm_del(Tools.getInstance().getMapper(alarmModel)).enqueue(new Callback<AlarmModel>() {
      @Override
      public void onResponse(Call<AlarmModel> call, Response<AlarmModel> response) {
        if (Tools.getInstance().isSuccessResponse(response)) {
          mAlarmList.clear();
          mAlarmResponse.resetPage();
          alarmListAPI();
        }
      }

      @Override
      public void onFailure(Call<AlarmModel> call, Throwable t) {

      }
    });
  }

  /**
   * 알림 전체 삭제 API
   */
  private void alarmAllDelAPI() {
    AlarmModel alarmRequest = new AlarmModel();
    alarmRequest.setMember_idx(Prefs.getString(Constants.MEMBER_IDX,""));
    CommonRouter.api().alarm_all_del(Tools.getInstance().getMapper(alarmRequest)).enqueue(new Callback<AlarmModel>() {
      @Override
      public void onResponse(Call<AlarmModel> call, Response<AlarmModel> response) {
        if (Tools.getInstance(mActivity).isSuccessResponse(response)) {
          mAlarmList.clear();
          mAlarmResponse.resetPage();
          alarmListAPI();
        }
      }

      @Override
      public void onFailure(Call<AlarmModel> call, Throwable t) {

      }
    });
  }
  //--------------------------------------------------------------------------------------------
  // MARK : Bind Actions
  //--------------------------------------------------------------------------------------------

  /**
   * 알림 전체 삭제
   */
  @OnClick(R.id.all_delete_button)
  public void allDeleteTouched() {
    alarmAllDelAPI();
  }
}

