package noommate.android.activity.main.my;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.widget.SwitchCompat;

import com.pixplicity.easyprefs.library.Prefs;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import noommate.android.R;
import noommate.android.activity.NoommateActivity;
import noommate.android.commons.Constants;
import noommate.android.commons.Tools;
import noommate.android.models.AlarmModel;
import noommate.android.models.api.CommonRouter;

public class AlarmSettingActivity extends NoommateActivity {
    //--------------------------------------------------------------------------------------------
    // MARK : GET START INTENT
    //--------------------------------------------------------------------------------------------
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, AlarmSettingActivity.class);
        return intent;
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Area
    //--------------------------------------------------------------------------------------------
    @BindView(R.id.alarm_switch1_image_view)
    SwitchCompat mAlarmSwitch1;
    @BindView(R.id.alarm_switch2_image_view)
    SwitchCompat mAlarmSwitch2;
    @BindView(R.id.alarm_switch3_image_view)
    SwitchCompat mAlarmSwitch3;
    //--------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------

    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    @Override
    protected int inflateLayout() {
        return R.layout.activity_alarm_setting;
    }

    @Override
    protected void initLayout() {
        mToolbarTitle.setText("알림설정");

    }

    @Override
    protected void initRequest() {
        alarmToggleViewAPI();
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Local functions
    //--------------------------------------------------------------------------------------------

    /**
     * 알림 설정 보기
     */
    private void alarmToggleViewAPI() {
        AlarmModel alarmModel = new AlarmModel();
        alarmModel.setMember_idx(Prefs.getString(Constants.MEMBER_IDX, ""));

        CommonRouter.api().alarm_toggle_view(Tools.getInstance().getMapper(alarmModel)).enqueue(new Callback<AlarmModel>() {
            @Override
            public void onResponse(Call<AlarmModel> call, Response<AlarmModel> response) {
                if (Tools.getInstance().isSuccessResponse(response)) {
                    AlarmModel alarmResponse = response.body();
                    if (alarmResponse.getAll_alarm_yn().equals("Y")) {
                        mAlarmSwitch1.setChecked(true);
                    } else {
                        mAlarmSwitch1.setChecked(false);
                    }

                    if (alarmResponse.getAlarm_my_item_yn().equals("Y")) {
                        mAlarmSwitch2.setChecked(true);
                    } else {
                        mAlarmSwitch2.setChecked(false);
                    }

                    if (alarmResponse.getAlarm_call_out_yn().equals("Y")) {
                        mAlarmSwitch3.setChecked(true);
                    } else {
                        mAlarmSwitch3.setChecked(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<AlarmModel> call, Throwable t) {
//        Tools.getInstance(mActivity).showToast(getString(R.string.api_error));
            }
        });
    }

    /**
     * 알림 설정
     */
    private void alarmToggleAPI(String type, String YN) {
        AlarmModel alarmModel = new AlarmModel();
        alarmModel.setMember_idx(Prefs.getString(Constants.MEMBER_IDX, ""));
        alarmModel.setAlarm_yn(YN);
        alarmModel.setAlarm_type(type);
        CommonRouter.api().alarm_toggle_mod_up(Tools.getInstance().getMapper(alarmModel)).enqueue(new Callback<AlarmModel>() {
            @Override
            public void onResponse(Call<AlarmModel> call, Response<AlarmModel> response) {
                if (Tools.getInstance().isSuccessResponse(response)) {
                    alarmToggleViewAPI();
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
     * 전체 알람
     */
    @OnClick(R.id.alarm_switch1_image_view)
    public void alarmSwitch1Touched() {
        alarmToggleAPI("0", mAlarmSwitch1.isChecked() ? "Y" : "N");
    }

    /**
     * 나의 할일
     */
    @OnClick(R.id.alarm_switch2_image_view)
    public void alarmSwitch2Touched() {
        alarmToggleAPI("1", mAlarmSwitch2.isChecked() ? "Y" : "N");
    }

    /**
     * 콕찌르기
     */
    @OnClick(R.id.alarm_switch3_image_view)
    public void alarmSwitch3Touched() {
        alarmToggleAPI("2", mAlarmSwitch3.isChecked() ? "Y" : "N");
    }



}

