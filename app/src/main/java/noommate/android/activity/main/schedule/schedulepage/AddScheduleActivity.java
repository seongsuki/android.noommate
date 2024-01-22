package noommate.android.activity.main.schedule.schedulepage;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pixplicity.easyprefs.library.Prefs;
import com.skydoves.powerspinner.OnSpinnerOutsideTouchListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.florent37.shapeofview.shapes.RoundRectView;
import noommate.android.activity.NoommateActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import noommate.android.R;
import noommate.android.commons.Constants;
import noommate.android.commons.EmptyView;
import noommate.android.commons.Tools;
import noommate.android.dialog.AddScheduleDialog;
import noommate.android.models.MemberModel;
import noommate.android.models.ScheduleModel;
import noommate.android.models.api.CommonRouter;
import timber.log.Timber;

public class AddScheduleActivity extends NoommateActivity {
    public interface OnAddScheduleListener {
        void onRefresh();
    }

    //--------------------------------------------------------------------------------------------
    // MARK : GET START INTENT
    //--------------------------------------------------------------------------------------------
    public static Intent getStartIntent(Context context, String planIdx, OnAddScheduleListener onAddScheduleListener) {
        Intent intent = new Intent(context, AddScheduleActivity.class);
        mOnAddScheduleListener = onAddScheduleListener;
        mPlanIdx = planIdx;
        return intent;
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Area
    //--------------------------------------------------------------------------------------------
    @BindView(R.id.title_edit_text)
    AppCompatEditText mTitleEditText;
    @BindView(R.id.checkbox)
    AppCompatCheckBox mCheckBox;
    @BindView(R.id.schedule_recycler_view)
    RecyclerView mScheduleRecyclerView;
    @BindView(R.id.time_spinner)
    PowerSpinnerView mTimeSpinner;
    @BindView(R.id.delete_layout)
    RoundRectView mDeleteLayout;
    @BindView(R.id.toolbar_title)
    AppCompatTextView mToolbarTitle;
    @BindView(R.id.enroll_button)
    AppCompatTextView mEnrollButton;

    //--------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------
    private ArrayList<ScheduleModel> mScheduleList = new ArrayList<>();
    private AddScheduleAdapter mAddScheduleAdapter;
    private static OnAddScheduleListener mOnAddScheduleListener;
    private ScheduleModel mDetailResponse = new ScheduleModel();
    private static String mPlanIdx;
    private ArrayList<String> memberIdxs = new ArrayList<>();


    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    @Override
    protected int inflateLayout() {
        return R.layout.activity_add_schedule;
    }

    @Override
    protected void initLayout() {
        if (!mPlanIdx.equals("")) {
            planDetailAPI();
            mToolbarTitle.setText("할 일 수정");
            mDeleteLayout.setVisibility(View.VISIBLE);
            mEnrollButton.setText("수정");
        } else {
            mToolbarTitle.setText("할 일 추가");
            mDeleteLayout.setVisibility(View.GONE);
            mEnrollButton.setText("등록");
        }

        mTimeSpinner.setSpinnerOutsideTouchListener(new OnSpinnerOutsideTouchListener() {
            @Override
            public void onSpinnerOutsideTouch(@NonNull View view, @NonNull MotionEvent motionEvent) {
                mTimeSpinner.dismiss();
            }
        });


    }

    @Override
    protected void initRequest() {
        initAddScheduleAdapter();
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Local functions
    //--------------------------------------------------------------------------------------------

    /**
     * 일정 추가 리스트
     */
    private void initAddScheduleAdapter() {
        mAddScheduleAdapter = new AddScheduleAdapter(R.layout.row_add_schedule, mScheduleList);
        mAddScheduleAdapter.setEmptyView(new EmptyView(mActivity, "일정을 추가해주세요."));
        mAddScheduleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.delete_button) {
                    mScheduleList.remove(position);
                    mAddScheduleAdapter.setNewData(mScheduleList);
                }
            }
        });
        mScheduleRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mScheduleRecyclerView.setAdapter(mAddScheduleAdapter);
    }

    /**
     * 일정 등록 API
     */
    private void planRegInAPI() {
        try {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < mScheduleList.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("week_arr", mScheduleList.get(i).getWeek_arr());
                jsonObject.put("member_arr", mScheduleList.get(i).getMember_arr());
                Timber.i("array : " + jsonObject.toString());
                jsonArray.put(jsonObject);
            }
            ScheduleModel scheduleRequest = new ScheduleModel();
            scheduleRequest.setHouse_idx(Prefs.getString(Constants.HOUSE_IDX, ""));
            scheduleRequest.setPlan_name(mTitleEditText.getText().toString());
            scheduleRequest.setAlarm_yn(mCheckBox.isChecked() ? "N" : "Y");
            scheduleRequest.setAlarm_hour(mTimeSpinner.getText().toString());
            scheduleRequest.setItem_array(jsonArray.toString());
            CommonRouter.api().plan_reg_in(Tools.getInstance().getMapper(scheduleRequest)).enqueue(new Callback<ScheduleModel>() {
                @Override
                public void onResponse(Call<ScheduleModel> call, Response<ScheduleModel> response) {
                    if (Tools.getInstance(mActivity).isSuccessResponse(response)) {
                        finishWithRemove();
                        mOnAddScheduleListener.onRefresh();
                    }
                }

                @Override
                public void onFailure(Call<ScheduleModel> call, Throwable t) {

                }
            });

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 일정 수정 API
     */
    private void planModUpAPI() {
        try {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < mScheduleList.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("week_arr", mScheduleList.get(i).getWeek_arr());
                jsonObject.put("member_arr", mScheduleList.get(i).getMember_arr());
                Timber.i("array : " + jsonObject.toString());
                jsonArray.put(jsonObject);
            }
            ScheduleModel scheduleRequest = new ScheduleModel();
            scheduleRequest.setHouse_idx(Prefs.getString(Constants.HOUSE_IDX, ""));
            scheduleRequest.setPlan_name(mTitleEditText.getText().toString());
            scheduleRequest.setAlarm_yn(mCheckBox.isChecked() ? "N" : "Y");
            scheduleRequest.setPlan_idx(mPlanIdx);
            scheduleRequest.setAlarm_hour(mTimeSpinner.getText().toString());
            scheduleRequest.setItem_array(jsonArray.toString());
            CommonRouter.api().plan_mod_up(Tools.getInstance().getMapper(scheduleRequest)).enqueue(new Callback<ScheduleModel>() {
                @Override
                public void onResponse(Call<ScheduleModel> call, Response<ScheduleModel> response) {
                    ScheduleModel scheduleResponse = response.body();
                    if (scheduleResponse.getCode().equals("1000")) {


                        finishWithRemove();
                        mOnAddScheduleListener.onRefresh();
                    } else {
                        showAlertDialog(scheduleResponse.getCode_msg(), "확인", new DialogEventListener() {
                            @Override
                            public void onReceivedEvent() {

                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<ScheduleModel> call, Throwable t) {

                }
            });

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 일정 상세 API
     */
    private void planDetailAPI() {
        ScheduleModel scheduleRequest = new ScheduleModel();
        scheduleRequest.setPlan_idx(mPlanIdx);
        scheduleRequest.setMember_idx(Prefs.getString(Constants.MEMBER_IDX, ""));
        CommonRouter.api().plan_detail(Tools.getInstance().getMapper(scheduleRequest)).enqueue(new Callback<ScheduleModel>() {
            @Override
            public void onResponse(Call<ScheduleModel> call, Response<ScheduleModel> response) {
                mDetailResponse = response.body();
                if (Tools.getInstance(mActivity).isSuccessResponse(response)) {
                    mTitleEditText.setText(mDetailResponse.getPlan_name());
                    mCheckBox.setChecked(mDetailResponse.getAlarm_yn() == "Y" ? true : false);
                    mTimeSpinner.setText(mDetailResponse.getAlarm_hour());
                    if (mDetailResponse.getPlan_item_list() != null) {
                        mScheduleList.addAll(mDetailResponse.getPlan_item_list());
                        Timber.i(mDetailResponse.getPlan_item_list().get(0).getMember_arr());
                    }
                    mAddScheduleAdapter.setNewData(mScheduleList);

                }
            }

            @Override
            public void onFailure(Call<ScheduleModel> call, Throwable t) {

            }
        });
    }

    /**
     * 일정 삭제 API
     */
    private void planDelAPI() {
        ScheduleModel scheduleRequest = new ScheduleModel();
        scheduleRequest.setPlan_idx(mPlanIdx);
        CommonRouter.api().plan_del(Tools.getInstance().getMapper(scheduleRequest)).enqueue(new Callback<ScheduleModel>() {
            @Override
            public void onResponse(Call<ScheduleModel> call, Response<ScheduleModel> response) {
                if (Tools.getInstance(mActivity).isSuccessResponse(response)) {
                    mOnAddScheduleListener.onRefresh();
                    finishWithRemove();
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

    /**
     * 추가하기
     */
    @OnClick(R.id.add_button)
    public void addTouched() {
        AddScheduleDialog addScheduleDialog = new AddScheduleDialog(mActivity, new AddScheduleDialog.AddScheduleListener() {
            @Override
            public void onRefresh(ArrayList<String> dayList, ArrayList<MemberModel> selectList) {
                ScheduleModel scheduleModel = new ScheduleModel();
                // 요일
                String strArrayToString = String.join(",", dayList);
                scheduleModel.setWeek_arr(strArrayToString);


                for (MemberModel value : selectList) {
                    value.setSelected(false);
                    memberIdxs.add(value.getMember_idx());
                    Timber.i("member_idx : " + value.getMember_idx());
                }
                String members = String.join(",", memberIdxs);

                scheduleModel.setMember_arr(members);
                scheduleModel.setSchedule_member_list(selectList);
                mScheduleList.add(scheduleModel);
                mAddScheduleAdapter.setNewData(mScheduleList);

            }
        });
        addScheduleDialog.show(getSupportFragmentManager(), "");
    }

    /**
     * 지정하지 않을래요
     */
    @OnClick(R.id.checkbox)
    public void checkBoxTouched() {
        if (mCheckBox.isChecked() == true) {
            mTimeSpinner.setText("");
            mTimeSpinner.setEnabled(false);
        } else {
            mTimeSpinner.setEnabled(true);
        }
    }


    /**
     * 일정 삭제
     */
    @OnClick(R.id.delete_button)
    public void deleteTouched() {
        planDelAPI();

    }

    /**
     * 등록
     */
    @OnClick(R.id.enroll_button)
    public void enrollTouched() {
        if (mPlanIdx.equals("")) {
            planRegInAPI();
        } else {
            planModUpAPI();
        }
    }

}

