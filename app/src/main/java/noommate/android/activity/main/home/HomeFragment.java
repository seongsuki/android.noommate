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
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.faltenreich.skeletonlayout.SkeletonLayout;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.islamkhsh.CardSliderIndicator;
import com.github.islamkhsh.CardSliderViewPager;
import com.github.islamkhsh.viewpager2.ViewPager2;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.florent37.shapeofview.shapes.RoundRectView;
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
    NestedScrollView mHouseLayout;
    @BindView(R.id.cnt_text_view)
    AppCompatTextView mCntTextView;
    @BindView(R.id.note_recycler_view)
    RecyclerView mNoteRecyclerView;
    @BindView(R.id.noommate_recycler_view)
    RecyclerView mNoomateRecyclerView;
    @BindView(R.id.date_text_view)
    AppCompatTextView mDateTextView;
    @BindView(R.id.house_image_view)
    AppCompatImageView mHouseImageView;
    @BindView(R.id.default_text_view)
    AppCompatTextView mDefaultTextView;
    @BindView(R.id.todo_view_pager)
    CardSliderViewPager mTodoViewPager;
    @BindView(R.id.photo_indicator)
    CardSliderIndicator mPhotoIndicator;
    @BindView(R.id.non_layout)
    RoundRectView mNonLayout;

    //--------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------
    private ArrayList<MemberModel> mMateList = new ArrayList<>();
    private MateAdapter mMateAdapter;
    private ArrayList<ArrayList<MemberModel>> mTodoList = new ArrayList<>();
    private ArrayList<MemberModel> mPageList = new ArrayList<>();
    private ArrayList<MemberModel> mNoteList = new ArrayList<>();
    private TodoPagerAdapter mTodoPagerAdapter;
    private HomeNoteAdapter mNoteAdapter;

    private Date mNowDate = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("M월 dd일 E요일");

    private ImagePickerDialog mImagePickDialog;
    private String mImagePath;
    private int cnt = 0;

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
            initTodoAdapter();
            mMateList.clear();
            mNoteList.clear();
            mTodoList.clear();
            homeAPI();

        }
    }


    @Override
    protected void initRequest() {

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
    private void initTodoAdapter() {
        mTodoPagerAdapter = new TodoPagerAdapter(mActivity, mTodoList, new TodoPagerAdapter.HomeBannerListener() {
            @Override
            public void onIntent(int position, int listPosition) {
               todayScheduleEndAPI(mTodoList.get(position).get(listPosition).getSchedule_idx());

            }
        });
        mTodoViewPager.setAdapter(mTodoPagerAdapter);
        mTodoViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });
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
                    // 메이트 리스트
                    if (mMemberResponse.getMate_array() != null) {
                        mMateList.addAll(mMemberResponse.getMate_array());
                    }
                    mMateAdapter.setNewData(mMateList);

                    // 일정
                    mDateTextView.setText(sdf.format(mNowDate));
                    for (MemberModel value : mMemberResponse.getMy_schedule_array()) {
                        if (value.getSchedule_yn().equals("Y")) {
                            cnt++;
                        }
                    }
                    mCntTextView.setText(cnt + "/" + mMemberResponse.getMy_schedule_count());

                    // 일정 배열 4개 단위로 나누기
                    for (int i = 0; i < mMemberResponse.getMy_schedule_array().size(); i += 4) {
                        for (int j = 0; j < 4 && i + j < mMemberResponse.getMy_schedule_array().size(); j++) {
                            mPageList.add(mMemberResponse.getMy_schedule_array().get(i + j));
                        }
                    }
                    mTodoList.add(mPageList);

                    if (mMemberResponse.getMy_schedule_array().size() > 0) {
                        mNonLayout.setVisibility(View.GONE);
                        mTodoViewPager.setVisibility(View.VISIBLE);
                        mPhotoIndicator.setVisibility(View.VISIBLE);
                    } else {
                        mNonLayout.setVisibility(View.VISIBLE);
                        mTodoViewPager.setVisibility(View.GONE);
                        mPhotoIndicator.setVisibility(View.GONE);
                    }

                    mTodoPagerAdapter.setNewData(mTodoList);

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
                    initTodoAdapter();
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
                    initTodoAdapter();
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
                .compress(2000)// Final image size will be less than 1 MB(Optional)
                .maxResultSize(2000, 2000)  // Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent(new Function1<Intent, Unit>() {
                    @Override
                    public Unit invoke(Intent intent) {
                        startForProfileImageResult.launch(intent);
                        return null;
                    }
                });
    }


}
