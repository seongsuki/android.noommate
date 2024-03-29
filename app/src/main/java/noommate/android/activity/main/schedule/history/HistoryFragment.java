package noommate.android.activity.main.schedule.history;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kizitonwose.calendarview.CalendarView;
import com.kizitonwose.calendarview.model.CalendarDay;
import com.kizitonwose.calendarview.model.CalendarMonth;
import com.kizitonwose.calendarview.model.DayOwner;
import com.kizitonwose.calendarview.ui.DayBinder;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.florent37.shapeofview.shapes.RoundRectView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import noommate.android.activity.NoommateActivity;
import noommate.android.activity.NoommateFragment;
import noommate.android.commons.EmptyView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import noommate.android.R;
import noommate.android.activity.main.schedule.ScheduleFragment;
import noommate.android.activity.main.schedule.schedulepage.AddScheduleActivity;
import noommate.android.activity.main.schedule.schedulepage.ScheduleAdapter;
import noommate.android.commons.Constants;
import noommate.android.commons.DayViewContainer;
import noommate.android.commons.Tools;
import noommate.android.models.ScheduleModel;
import noommate.android.models.api.CommonRouter;
import timber.log.Timber;

public class HistoryFragment extends NoommateFragment {
    //--------------------------------------------------------------------------------------------
    // MARK : GET START INTENT
    //--------------------------------------------------------------------------------------------
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ScheduleFragment.class);
        return intent;
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Area
    //--------------------------------------------------------------------------------------------
    @BindView(R.id.calendar_view)
    CalendarView mCalendarView;
    @BindView(R.id.month_text_view)
    AppCompatTextView mMonthTextView;
    @BindView(R.id.day_text_view)
    AppCompatTextView mDayTextView;
    @BindView(R.id.history_recycler_view)
    RecyclerView mHistoryRecyclerView;

    //--------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------
    private int mIndicatorWidth;

    private YearMonth mSelectedMonth;
    private LocalDate mSelectedDay;
    private LocalDate mRecordDate;

    private String mRecordMonth;
    private String mMonth;
    private String mRecordDay;
    private LocalDate mSDate;
    private LocalDate mEDate;

    private ArrayList<String> mDateList = new ArrayList();
    private ScheduleAdapter mHistoryAdapter;
    private ArrayList<ScheduleModel> mHistoryList = new ArrayList<>();
    private ArrayList<String> mYesDateList = new ArrayList();
    private ScheduleModel mCircleResponse = new ScheduleModel();
    private SimpleDateFormat sdf = new SimpleDateFormat("MM.dd(E)");
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM.dd(E)");
    private Date mNowDate = new Date();

    private List<String> mRecordDateList = new ArrayList<>();
    private BroadcastReceiver mScheduleReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mHistoryList.clear();
            scheduleDateMemberListAPI();

        }
    };


    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    @Override
    protected int inflateLayout() {
        return R.layout.fragment_history;
    }

    @Override
    protected void initLayout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mMonthTextView.setText(Year.now() + "년 " + YearMonth.now() + "월");
            mSelectedMonth = YearMonth.now();
            mSelectedDay = LocalDate.now();
            mDayTextView.setText(sdf.format(mNowDate));
            mSDate = mSelectedDay.withDayOfMonth(1);
            mEDate = mSelectedDay.withDayOfMonth(mSelectedDay.lengthOfMonth());
            initCalendar();
            scheduleDateMemberListAPI();
        }
        mActivity.registerReceiver(mScheduleReceiver, new IntentFilter(Constants.SCHEDULE_REFRESH3));



    }

    @Override
    protected void initRequest() {
        initHistoryAdapter();
        scheduleDateListAPI();

    }

    //--------------------------------------------------------------------------------------------
    // MARK : Local functions
    //--------------------------------------------------------------------------------------------

    /**
     * 캘린더 세팅
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initCalendar() {
        mSelectedMonth = YearMonth.now();
        mSelectedDay = LocalDate.now();
        mSDate = mSelectedDay.withDayOfMonth(1);
        mEDate = mSelectedDay.withDayOfMonth(mSelectedDay.lengthOfMonth());
        YearMonth currentMonth = YearMonth.now();
        YearMonth firstMonth = currentMonth.minusYears(100);
        YearMonth lastMonth = currentMonth.plusYears(100);
//    DayOfWeek dayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
        mCalendarView.setup(firstMonth, lastMonth, DayOfWeek.MONDAY);
        mCalendarView.scrollToMonth(currentMonth);
        mCalendarView.setDayBinder(new DayBinder<DayViewContainer>() {
            @NonNull
            @Override
            public DayViewContainer create(@NonNull View view) {
                return new DayViewContainer(view);
            }

            @Override
            public void bind(@NonNull DayViewContainer viewContainer, @NonNull CalendarDay calendarDay) {

                AppCompatTextView dayTextView = viewContainer.mDayTextView;
                RoundRectView recordPointLayout = viewContainer.mRecordPointLayout;
                AppCompatTextView todayTextView = viewContainer.mTodayTextView;
                RoundRectView dayLayout = viewContainer.mDayLayout;
                dayTextView.setText(String.valueOf(calendarDay.getDate().getDayOfMonth()));

                mDateList.add(String.valueOf(calendarDay.getDate()));


                if (calendarDay.getDate().isEqual(LocalDate.now())) {
                    todayTextView.setVisibility(View.GONE);
                    dayLayout.setBorderWidth(4);
                    dayLayout.setBorderColor(mActivity.getColor(R.color.colorAccent));
                } else {
                    dayLayout.setBorderWidth(0);
                    todayTextView.setVisibility(View.GONE);
                }

                if (calendarDay.getDate().isEqual(mSelectedDay)) {
                    dayTextView.setBackgroundColor(mActivity.getColor(R.color.colorAccent));
                    dayTextView.setTextColor(mActivity.getColor(R.color.color_ffffff));
                } else {
                    dayTextView.setBackgroundColor(mActivity.getColor(R.color.color_ffffff));
                    dayTextView.setTextColor(mActivity.getColor(R.color.color_222b45));
                }

                // 예약 여부 원 표시
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                Timber.i("dateList : " + mRecordDateList);
                String formattedString = calendarDay.getDate().format(formatter);
                if ((calendarDay.getOwner() == DayOwner.THIS_MONTH) && (mRecordDateList.contains(formattedString))) {
                    recordPointLayout.setVisibility(View.VISIBLE);
                } else {
                    recordPointLayout.setVisibility(View.GONE);
                }


                if (calendarDay.getOwner() == DayOwner.THIS_MONTH) {
                    dayTextView.setVisibility(View.VISIBLE);
                } else {
                    dayTextView.setVisibility(View.GONE);
                }

                if (calendarDay.getDate().isBefore(LocalDate.now()) || calendarDay.getDate().isEqual(LocalDate.now())) {
                    dayTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mCalendarView.notifyDateChanged(mSelectedDay);
                            mSelectedDay = calendarDay.getDate();
                            mCalendarView.notifyDateChanged(mSelectedDay);
                            mDayTextView.setText(calendarDay.getDate().format(dtf));
                            mHistoryList.clear();
                            scheduleDateMemberListAPI();

                        }
                    });
                }
            }
        });

        mCalendarView.setMonthScrollListener(new Function1<CalendarMonth, Unit>() {
            @Override
            public Unit invoke(CalendarMonth calendarMonth) {
                mMonthTextView.setText(calendarMonth.getYear() + "년 " + calendarMonth.getMonth() + "월");
                mSelectedMonth = calendarMonth.getYearMonth();
                SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
                mMonth = String.valueOf(calendarMonth.getMonth());
                mRecordMonth = calendarMonth.getYear() + "-" + mMonth;
                return null;
            }
        });


        mMonthTextView.setText(currentMonth.getYear() + "년 " + currentMonth.getMonth() + "월");
        scheduleDateListAPI();
    }

    /**
     * 일정 리스트
     */
    private void initHistoryAdapter() {
        mHistoryAdapter = new ScheduleAdapter(R.layout.row_schedule, mHistoryList);
        mHistoryAdapter.setEmptyView(new EmptyView(mActivity, "해당 날짜에 할 일이 없어요."));
        mHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mHistoryRecyclerView.setAdapter(mHistoryAdapter);

    }

    /**
     * 달력 일정 리스트 API
     */
    private void scheduleDateListAPI() {
        ScheduleModel scheduleRequest = new ScheduleModel();
        scheduleRequest.setS_date(String.valueOf(mSDate));
        scheduleRequest.setE_date(String.valueOf(mEDate));
        scheduleRequest.setHouse_idx(Prefs.getString(Constants.HOUSE_IDX, ""));
        CommonRouter.api().schedule_date_list(Tools.getInstance().getMapper(scheduleRequest)).enqueue(new Callback<ScheduleModel>() {
            @Override
            public void onResponse(Call<ScheduleModel> call, Response<ScheduleModel> response) {
                mCircleResponse = response.body();
                if (Tools.getInstance().isSuccessResponse(response)) {
                    for (ScheduleModel value : mCircleResponse.getData_array()) {
                        mRecordDateList.add(String.valueOf(value.getSchedule_date()));
                    }
                    mCalendarView.notifyMonthChanged(mSelectedMonth);
                }
            }

            @Override
            public void onFailure(Call<ScheduleModel> call, Throwable t) {

            }
        });
    }

    /**
     * 일자별 리스트 API
     */
    private void scheduleDateMemberListAPI() {
        ScheduleModel scheduleRequest = new ScheduleModel();
        scheduleRequest.setHouse_idx(Prefs.getString(Constants.HOUSE_IDX, ""));
        scheduleRequest.setMember_idx(Prefs.getString(Constants.MEMBER_IDX, ""));
        scheduleRequest.setToday(String.valueOf(mSelectedDay));
        CommonRouter.api().schedule_date_member_list(Tools.getInstance().getMapper(scheduleRequest)).enqueue(new Callback<ScheduleModel>() {
            @Override
            public void onResponse(Call<ScheduleModel> call, Response<ScheduleModel> response) {
                ScheduleModel mScheduleResponse = response.body();
                if (mScheduleResponse.getData_array() != null) {
                    mHistoryList.addAll(mScheduleResponse.getData_array());
                }
//                if (mScheduleResponse.getData_array() != null) {
//                    for (ScheduleModel value : mScheduleResponse.getData_array()) {
//                        mDayTextView.setText(value.getSchedule_w());
//                    }
//                }
                mHistoryAdapter.setNewData(mHistoryList);


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
     * 날짜 선택
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick({R.id.left_month_button, R.id.right_month_button})
    public void monthSelectTouched(View view) {
        if (view.getId() == R.id.left_month_button) {
            mSelectedMonth = mSelectedMonth.minusMonths(1);
            mHistoryList.clear();
            scheduleDateMemberListAPI();
        } else {
            mSelectedMonth = mSelectedMonth.plusMonths(1);
            mHistoryList.clear();
            scheduleDateMemberListAPI();
        }
        mCalendarView.scrollToMonth(mSelectedMonth);
    }


}


