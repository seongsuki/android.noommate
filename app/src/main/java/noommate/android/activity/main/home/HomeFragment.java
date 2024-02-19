package noommate.android.activity.main.home;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;

import android.view.View;
import android.widget.LinearLayout;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.faltenreich.skeletonlayout.SkeletonLayout;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import noommate.android.activity.NoommateActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import noommate.android.R;
import noommate.android.activity.NoommateFragment;
import noommate.android.activity.commons.alarm.AlarmActivity;
import noommate.android.commons.Constants;
import noommate.android.commons.DecorationHorizontal;
import noommate.android.commons.EmptyView;
import noommate.android.commons.SpacingItemDecoration;
import noommate.android.commons.Tools;
import noommate.android.dialog.HouseIntoDialog;
import noommate.android.dialog.ImagePickerDialog;
import noommate.android.models.MemberModel;
import noommate.android.models.api.BaseRouter;
import noommate.android.models.api.CommonRouter;
import timber.log.Timber;

public class HomeFragment extends NoommateFragment {
    //--------------------------------------------------------------------------------------------
    // MARK : GET START INTENT
    //--------------------------------------------------------------------------------------------
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, HomeFragment.class);
        return intent;
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Area
    //--------------------------------------------------------------------------------------------
    @BindView(R.id.default_layout)
    LinearLayout mDefaultLayout;
    @BindView(R.id.house_name_text_view)
    AppCompatTextView mHouseNameTextView;
    @BindView(R.id.house_layout)
    CoordinatorLayout mHouseLayout;
    @BindView(R.id.mate_cnt_text_view)
    AppCompatTextView mMateCntTextView;
    @BindView(R.id.cnt_text_view)
    AppCompatTextView mCntTextView;
    @BindView(R.id.note_recycler_view)
    RecyclerView mNoteRecyclerView;
    @BindView(R.id.todo_recycler_view)
    RecyclerView mTodoRecyclerView;
    @BindView(R.id.noommate_recycler_view)
    RecyclerView mNoomateRecyclerView;
    @BindView(R.id.date_text_view)
    AppCompatTextView mDateTextView;
    @BindView(R.id.house_image_view)
    AppCompatImageView mHouseImageView;
    @BindView(R.id.default_text_view)
    AppCompatTextView mDefaultTextView;
    @BindView(R.id.skeleton_layout)
    SkeletonLayout mSkeletonLayout;

    //--------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------
    private ArrayList<MemberModel> mMateList = new ArrayList<>();
    private MateAdapter mMateAdapter;
    private ArrayList<MemberModel> mTodoList = new ArrayList<>();
    private HomeScheduleAdapter mHomeScheduleAdapter;
    private ArrayList<MemberModel> mNoteList = new ArrayList<>();
    private HomeNoteAdapter mNoteAdapter;

    private Date mNowDate = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("MM월 dd일 (E)");

    private ImagePickerDialog mImagePickDialog;
    private String mImagePath;

    private BroadcastReceiver mHomeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Prefs.getString(Constants.HOUSE_CODE, "").equals("")) {
                mHouseLayout.setVisibility(View.GONE);
                mDefaultLayout.setVisibility(View.VISIBLE);
            } else if (!Prefs.getString(Constants.HOUSE_CODE, "").equals("")) {
                mHouseLayout.setVisibility(View.VISIBLE);
                mDefaultLayout.setVisibility(View.GONE);
                mMateList.clear();
                mNoteList.clear();
                mTodoList.clear();
                homeAPI();

            }

        }
    };

    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    @Override
    protected int inflateLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initLayout() {

//        mSkeletonLayout.showSkeleton();

        mActivity.registerReceiver(mHomeReceiver, new IntentFilter(Constants.HOME_REFRESH));
        if (Prefs.getString(Constants.HOUSE_CODE, "").equals("")) {
            mHouseLayout.setVisibility(View.GONE);
            mDefaultLayout.setVisibility(View.VISIBLE);
        } else if (!Prefs.getString(Constants.HOUSE_CODE, "").equals("")) {
            mHouseLayout.setVisibility(View.VISIBLE);
            mDefaultLayout.setVisibility(View.GONE);
            initMateAdapter();
            initNoteAdapter();
            initHomeScheduleAdapter();
            mMateList.clear();
            mNoteList.clear();
            mTodoList.clear();
            homeAPI();

        }
    }


    @Override
    protected void initRequest() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSkeletonLayout.showOriginal();
            }
        }, 1000);

    }

    private ActivityResultLauncher<Intent> startForProfileImageResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                int resultCode = result.getResultCode();
                Intent data = result.getData();

                Uri uri = data.getData();
                Timber.i("image_url" + data);
                File file = Tools.getInstance().compressImage(uri);
                Tools.getInstance().fileUploadAction(file, new Tools.FileUploadListener() {
                    @Override
                    public void onResult(boolean isSuccess, String filePath) {

                        RequestOptions requestOptions = new RequestOptions();
                        requestOptions.centerCrop();

                        Glide.with(mActivity)
                                .load(BaseRouter.BASE_URL + filePath)
                                .apply(requestOptions)
                                .into(mHouseImageView);
                        mImagePath = filePath;

                        houseModUpAPI();

                    }
                });
            });


    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity.unregisterReceiver(mHomeReceiver);
    }


    //--------------------------------------------------------------------------------------------
    // MARK : Local functions
    //--------------------------------------------------------------------------------------------

    /**
     * 메이트 리스트
     */
    private void initMateAdapter() {
        mMateAdapter = new MateAdapter(R.layout.row_homemate, mMateList);
        mNoomateRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mNoomateRecyclerView.addItemDecoration(new DecorationHorizontal(mContext, Tools.getInstance().dpTopx(mContext, 8), Tools.getInstance().dpTopx(mContext, 16)));
        mNoomateRecyclerView.setAdapter(mMateAdapter);
    }

    /**
     * 일정
     */
    private void initHomeScheduleAdapter() {
        mHomeScheduleAdapter = new HomeScheduleAdapter(R.layout.row_home_schedule, mTodoList);
        mHomeScheduleAdapter.setEmptyView(new EmptyView(mActivity, "하우스 일정이 없어요"));
        mHomeScheduleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mTodoList.get(position).getSchedule_yn() != null) {
                    if (mTodoList.get(position).getSchedule_yn().equals("N")) {
                        showConfirmDialog(mTodoList.get(position).getPlan_name() + " 을(를) 완료하셨나요?", "취소", "수행 완료했어요.", new NoommateActivity.DialogEventListener() {
                            @Override
                            public void onReceivedEvent() {
                                todayScheduleEndAPI(mTodoList.get(position).getSchedule_idx());
                            }
                        });

                    }
                }
            }
        });
        mTodoRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        mTodoRecyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.getInstance().dpTopx(mActivity, 15), true));
        mTodoRecyclerView.setAdapter(mHomeScheduleAdapter);
    }

    /**
     * 알림장
     */
    private void initNoteAdapter() {
        mNoteAdapter = new HomeNoteAdapter(R.layout.row_home_note, mNoteList);
        mNoteAdapter.setEmptyView(new EmptyView(mActivity, "아직 작성된 알림장이 없어요."));
        mNoteRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mNoteRecyclerView.setAdapter(mNoteAdapter);
    }

    /**
     * 홈 API
     */
    private void homeAPI() {
        MemberModel memberRequest = new MemberModel();
        memberRequest.setMember_idx(Prefs.getString(Constants.MEMBER_IDX, ""));
        memberRequest.setHouse_idx(Prefs.getString(Constants.HOUSE_IDX, ""));
        CommonRouter.api().house_list(Tools.getInstance().getMapper(memberRequest)).enqueue(new Callback<MemberModel>() {
            @Override
            public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
                MemberModel mMemberResponse = response.body();
                if (Tools.getInstance(mActivity).isSuccessResponse(response)) {
                    mHouseNameTextView.setText(mMemberResponse.getHouse_name());
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.centerCrop();
                    Glide.with(mActivity)
                            .load(BaseRouter.BASE_URL + mMemberResponse.getHouse_img())
                            .apply(requestOptions)
                            .placeholder(mActivity.getDrawable(R.drawable.default_image))
                            .into(mHouseImageView);
                    mMateCntTextView.setText("눔메이트 " + mMemberResponse.getMate_cnt() + "명");
                    // 메이트 리스트
                    if (mMemberResponse.getMate_array() != null) {
                        mMateList.addAll(mMemberResponse.getMate_array());
                    }
                    mMateAdapter.setNewData(mMateList);

                    // 일정
                    mDateTextView.setText(sdf.format(mNowDate));
                    mCntTextView.setText("나의 할 일 " + mMemberResponse.getMy_schedule_count() + "개");

                    if (mMemberResponse.getMy_schedule_array().size() > 0) {
                        for (int j = 0; j < mMemberResponse.getMy_schedule_array().size(); j++) {
                            mHomeScheduleAdapter.addData(j, mMemberResponse.getMy_schedule_array().get(j));
                        }
                        mDefaultTextView.setVisibility(View.GONE);
                        mTodoRecyclerView.setVisibility(View.VISIBLE);
                        if (mMemberResponse.getMy_schedule_array().size() == 1) {
                            mTodoList.add(new MemberModel());
                            mTodoList.add(new MemberModel());
                            mTodoList.add(new MemberModel());
                        } else if (mMemberResponse.getMy_schedule_array().size() == 2) {
                            mTodoList.add(new MemberModel());
                            mTodoList.add(new MemberModel());
                        } else if (mMemberResponse.getMy_schedule_array().size() == 3) {
                            mTodoList.add(new MemberModel());
                        }
                    } else {
                        mDefaultTextView.setVisibility(View.VISIBLE);
                        mTodoRecyclerView.setVisibility(View.GONE);
                    }
                    mHomeScheduleAdapter.setNewData(mTodoList);

                    if (mMemberResponse.getNote_array() != null) {
                        mNoteList.addAll(mMemberResponse.getNote_array());
                    }
                    mNoteAdapter.setNewData(mNoteList);

                }
            }

            @Override
            public void onFailure(Call<MemberModel> call, Throwable t) {

            }
        });
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
                mNoteList.clear();
                mTodoList.clear();
                mMateList.clear();
                homeAPI();
            }

            @Override
            public void onFailure(Call<MemberModel> call, Throwable t) {

            }
        });
    }

    /**
     * 하우스 이미지 등록
     */
    private void houseModUpAPI() {
        MemberModel memberRequest = new MemberModel();
        memberRequest.setHouse_code(Prefs.getString(Constants.HOUSE_CODE, ""));
        memberRequest.setHouse_img(mImagePath);
        CommonRouter.api().house_mod_up(Tools.getInstance().getMapper(memberRequest)).enqueue(new Callback<MemberModel>() {
            @Override
            public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
                if (Tools.getInstance(mActivity).isSuccessResponse(response)) {

                }
            }

            @Override
            public void onFailure(Call<MemberModel> call, Throwable t) {

            }
        });
    }
    //--------------------------------------------------------------------------------------------
    // MARK : Bind Actions
    //--------------------------------------------------------------------------------------------

    /**
     * 하우스 들어가기
     */
    @OnClick(R.id.join_button)
    public void joinTouched() {
        HouseIntoDialog houseIntoDialog = new HouseIntoDialog(mActivity, new HouseIntoDialog.IntoListener() {
            @Override
            public void onRefresh() {
                Intent feedRefresh = new Intent(Constants.MY_REFRESH);
                mActivity.sendBroadcast(feedRefresh);
                if (Prefs.getString(Constants.HOUSE_CODE, "").equals("")) {
                    mHouseLayout.setVisibility(View.GONE);
                    mDefaultLayout.setVisibility(View.VISIBLE);
                } else {
                    mHouseLayout.setVisibility(View.VISIBLE);
                    mDefaultLayout.setVisibility(View.GONE);
                    initMateAdapter();
                    initNoteAdapter();
                    initHomeScheduleAdapter();
                    homeAPI();

                }

            }
        });
        houseIntoDialog.show(getChildFragmentManager(), "");
    }

    /**
     * 알람
     */
    @OnClick(R.id.alarm_list_button)
    public void alarmTouched() {
        Intent alarmActivity = AlarmActivity.getStartIntent(mActivity);
        startActivity(alarmActivity, NoommateActivity.TRANS.PUSH);
    }

    /**
     * 하우스 만들기
     */
    @OnClick(R.id.add_house_button)
    public void addHouseTouched() {
        Intent addHouseActivity = AddHouseActivity.getStartIntent(mActivity);
        startActivity(addHouseActivity, NoommateActivity.TRANS.PUSH);
    }

    /**
     * 하우스 코드 입력하기
     */
    @OnClick(R.id.house_button)
    public void houseTouched() {
        HouseIntoDialog houseIntoDialog = new HouseIntoDialog(mActivity, new HouseIntoDialog.IntoListener() {
            @Override
            public void onRefresh() {
                if (Prefs.getString(Constants.HOUSE_CODE, "").equals("")) {
                    mHouseLayout.setVisibility(View.GONE);
                    mDefaultLayout.setVisibility(View.VISIBLE);
                } else if (!Prefs.getString(Constants.HOUSE_CODE, "").equals("")) {
                    mHouseLayout.setVisibility(View.VISIBLE);
                    mDefaultLayout.setVisibility(View.GONE);
                    initMateAdapter();
                    initNoteAdapter();
                    initHomeScheduleAdapter();
                    mMateList.clear();
                    mNoteList.clear();
                    mTodoList.clear();
                    homeAPI();
                }


            }
        });
        houseIntoDialog.show(getChildFragmentManager(), "");
    }

    /**
     * 일정 이동
     */
    @OnClick(R.id.more_button)
    public void scheduleTouched() {
        Intent homeScheduleActivity = HomeScheduleActivity.getStartIntent(mActivity);
        startActivity(homeScheduleActivity, NoommateActivity.TRANS.PUSH);
    }

    /**
     * 알림장
     */
    @OnClick(R.id.note_button)
    public void noteTouched() {
        Intent noteActivity = NoteActivity.getStartIntent(mActivity);
        startActivity(noteActivity, NoommateActivity.TRANS.PUSH);
    }

    /**
     * 하우스 이미지
     */
    @OnClick(R.id.add_button)
    public void addTouched() {

        ImagePicker.Companion.with(this)
                .compress(1024)         // Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)  // Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent(new Function1<Intent, Unit>() {
                    @Override
                    public Unit invoke(Intent intent) {
                        startForProfileImageResult.launch(intent);
                        return null;
                    }
                });
    }


}
