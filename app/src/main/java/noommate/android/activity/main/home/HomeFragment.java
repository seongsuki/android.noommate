package noommate.android.activity.main.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.view.View;
import android.widget.LinearLayout;


import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import noommate.android.R;
import noommate.android.activity.RocateerActivity;
import noommate.android.activity.RocateerFragment;
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

public class HomeFragment extends RocateerFragment {
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

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Timber.i("data : " + data + "reqeulstCode : " + requestCode);
        mImagePickDialog.dismiss();
        Image image = ImagePicker.getFirstImageOrNull(data);
        Uri imageUri = Uri.fromFile(new File(image.getPath()));
        File file = Tools.getInstance().compressImage(imageUri);

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
    }


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
        mHomeScheduleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.state_text_view) {
                    showConfirmDialog(mTodoList.get(position).getPlan_name() + " 을(를) 완료하셨나요?", "취소", "수행 완료했어요.", new RocateerActivity.DialogEventListener() {
                        @Override
                        public void onReceivedEvent() {
                            todayScheduleEndAPI(mTodoList.get(position).getSchedule_idx());
                        }
                    });
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
                        for (int i = 0; i < 4; i++) {
                            if (mMemberResponse.getMy_schedule_array().get(i).getSchedule_idx() != null) {
                                for (int j = 0; j < mMemberResponse.getMy_schedule_array().size(); j++) {
                                    mTodoList.add(j, mMemberResponse.getMy_schedule_array().get(j));
                                }
                            } else {
                                mTodoList.add(new MemberModel());
                            }
                        }
                        mDefaultTextView.setVisibility(View.GONE);
                        mTodoRecyclerView.setVisibility(View.VISIBLE);
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
        memberRequest.setHouse_idx(Prefs.getString(Constants.HOUSE_IDX, ""));
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
        startActivity(alarmActivity, RocateerActivity.TRANS.PUSH);
    }

    /**
     * 하우스 만들기
     */
    @OnClick(R.id.add_house_button)
    public void addHouseTouched() {
        Intent addHouseActivity = AddHouseActivity.getStartIntent(mActivity);
        startActivity(addHouseActivity, RocateerActivity.TRANS.PUSH);
    }

    /**
     * 일정 이동
     */
    @OnClick(R.id.schedule_button)
    public void scheduleTouched() {
        Intent homeScheduleActivity = HomeScheduleActivity.getStartIntent(mActivity);
        startActivity(homeScheduleActivity, RocateerActivity.TRANS.PUSH);
    }

    /**
     * 알림장
     */
    @OnClick(R.id.note_button)
    public void noteTouched() {
        Intent noteActivity = NoteActivity.getStartIntent(mActivity);
        startActivity(noteActivity, RocateerActivity.TRANS.PUSH);
    }

    /**
     * 하우스 이미지
     */
    @OnClick(R.id.add_button)
    public void addTouched() {
        mImagePickDialog = new ImagePickerDialog(mActivity);
        mImagePickDialog.mCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 카메라 선택
                ImagePicker.create(mActivity)
                        .imageDirectory(getString(R.string.app_name))
                        .cameraOnly()
                        .start(mActivity);
            }
        });
        mImagePickDialog.mAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 앨범에서 선택
                ImagePicker.create(mActivity)
                        .returnMode(ReturnMode.ALL)
                        .folderMode(true)
                        .toolbarFolderTitle(getString(R.string.app_name))
                        .toolbarImageTitle(getString(R.string.app_name))
                        .includeVideo(false)
                        .includeAnimation(false)
                        .imageDirectory(getString(R.string.app_name))
                        .single()
                        .theme(R.style.ImagePickerTheme)
                        .showCamera(false)
                        .start();
            }
        });
        mImagePickDialog.show();
    }


}
