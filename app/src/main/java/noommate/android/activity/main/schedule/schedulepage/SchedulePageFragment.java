package noommate.android.activity.main.schedule.schedulepage;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.michalsvec.singlerowcalendar.calendar.CalendarChangesObserver;
import com.michalsvec.singlerowcalendar.calendar.CalendarViewManager;
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendar;
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendarAdapter;
import com.michalsvec.singlerowcalendar.selection.CalendarSelectionManager;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import noommate.android.activity.NoommateActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import noommate.android.R;
import noommate.android.activity.NoommateFragment;
import noommate.android.activity.main.schedule.ScheduleFragment;
import noommate.android.commons.Constants;
import noommate.android.commons.EmptyView;
import noommate.android.commons.Tools;
import noommate.android.models.ScheduleModel;
import noommate.android.models.api.CommonRouter;
import timber.log.Timber;

public class SchedulePageFragment extends NoommateFragment {
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
    @BindView(R.id.list_recycler_view)
    RecyclerView mListRecyclerView;
    @BindView(R.id.single_row_calendar)
    SingleRowCalendar mSingleRowCalendar;
    @BindView(R.id.date_text_view)
    AppCompatTextView mDateTextView;

    @BindView(R.id.cnt_text_view)
    AppCompatTextView mCntTextView;
    //--------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------
    private ScheduleAdapter mScheduleAdapter;
    private ArrayList<ScheduleModel> mScheduleList = new ArrayList<>();

    private static String mHour = "";
    private static String mMinute = "";
    private static String mYear = "";
    private static String mMonth = "";
    private static String mDay = "";
    private Date mToday = new Date();
    private SimpleDateFormat sdf = new SimpleDateFormat("M월 d일 E요일");



    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    @Override
    protected int inflateLayout() {
        return R.layout.fragment_schedule_page;
    }

    @Override
    protected void initLayout() {
        mDateTextView.setText(sdf.format(mToday));



    }

    @Override
    protected void initRequest() {
        initCalendar();
        initScheduleAdapter();
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Local functions
    //--------------------------------------------------------------------------------------------

    /**
     * 할일 리스트
     */
    private void initScheduleAdapter() {
        mScheduleAdapter = new ScheduleAdapter(R.layout.row_schedule, mScheduleList);
        mScheduleAdapter.setEmptyView(new EmptyView(mActivity, "해당 날짜에 할 일이 없어요."));
        mScheduleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent addScheduleActivity = AddScheduleActivity.getStartIntent(mActivity, mScheduleList.get(position).getPlan_idx(), new AddScheduleActivity.OnAddScheduleListener() {
                    @Override
                    public void onRefresh() {
                        mScheduleList.clear();
                        scheduleListAPI();

                    }
                });
                startActivity(addScheduleActivity, NoommateActivity.TRANS.PUSH);
            }
        });
        mListRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mListRecyclerView.setAdapter(mScheduleAdapter);

    }

    /**
     * 달력 커스터마이징
     */
    private void initCalendar() {

        CalendarViewManager calendarViewManager = new CalendarViewManager() {
            @Override
            public int setCalendarViewResourceId(int i, @NonNull Date date, boolean b) {
                return R.layout.row_date;
            }

            @Override
            public void bindDataToCalendarView(@NonNull SingleRowCalendarAdapter.CalendarViewHolder calendarViewHolder, @NonNull Date date, int i, boolean b) {
                String dayOfTheWeek = (String) DateFormat.format("E", date); // Thursday
                String day = (String) DateFormat.format("d", date); // 20
                String monthString = (String) DateFormat.format("MMM", date); // Jun
                String monthNumber = (String) DateFormat.format("M", date); // 06
                String year = (String) DateFormat.format("yyyy", date); // 2013
                String yoil = (String) DateFormat.format("E", date); // 요일



                LinearLayout dateLayout = calendarViewHolder.itemView.findViewById(R.id.date_layout);
//                AppCompatTextView todayTextView = calendarViewHolder.itemView.findViewId(R.id.today_text_view);

                AppCompatTextView mTodayTextView = calendarViewHolder.itemView.findViewById(R.id.today_text_view);

                AppCompatTextView dateTextView = calendarViewHolder.itemView.findViewById(R.id.date_text_view);
                dateTextView.setText(day);


                AppCompatTextView dayCalendarTextView = calendarViewHolder.itemView.findViewById(R.id.yoil_text_view);
                dayCalendarTextView.setText(dayOfTheWeek);

                mDateTextView.setText(monthNumber + "월 " + day + "일 " + yoil+"요일");


                if (i == 0) {
                    mTodayTextView.setVisibility(View.VISIBLE);
                } else {
                    mTodayTextView.setVisibility(View.GONE);
                }

//                LinearLayout clCalendarItem = calendarViewHolder.itemView.findViewById(R.id.cl_calendar_item);



                if (mSingleRowCalendar.getSelectedIndexes().contains(i)) {
                    dateTextView.setTextColor(mActivity.getColor(R.color.color_ffffff));
                    dayCalendarTextView.setTextColor(mActivity.getColor(R.color.color_ffffff));
                    dateLayout.setBackgroundColor(mActivity.getColor(R.color.colorAccent));
                    mScheduleList.clear();
                    mScheduleAdapter.setNewData(mScheduleList);
                    scheduleListAPI();
                } else {
                    dateTextView.setTextColor(mActivity.getColor(R.color.color_a3a7b6));
                    dayCalendarTextView.setTextColor(mActivity.getColor(R.color.color_a3a7b6));
                    dateLayout.setBackgroundColor(mActivity.getColor(R.color.color_fafafc));
                }






            }
        };

        CalendarSelectionManager calendarSelectionManager = new CalendarSelectionManager() {
            @Override
            public boolean canBeItemSelected(int i, @NonNull Date date) {
                return true;
            }
        };

        CalendarChangesObserver calendarChangesObserver = new CalendarChangesObserver() {
            @Override
            public void whenWeekMonthYearChanged(@NonNull String s, @NonNull String s1, @NonNull String s2, @NonNull String s3, @NonNull Date date) {
                Timber.i("date = " + date);
            }

            @Override
            public void whenSelectionChanged(boolean b, int i, @NonNull Date date) {
                Timber.i("Select Date = " + date);
                String dayOfTheWeek = (String) DateFormat.format("E", date); // Thursday
                String day = (String) DateFormat.format("dd", date); // 20
                String monthString = (String) DateFormat.format("MMM", date); // Jun
                String monthNumber = (String) DateFormat.format("MM", date); // 06
                String year = (String) DateFormat.format("yyyy", date); // 2013

                mYear = year;
                mMonth = monthNumber;
                mDay = day;


            }

            @Override
            public void whenCalendarScrolled(int i, int i1) {

            }

            @Override
            public void whenSelectionRestored() {

            }

            @Override
            public void whenSelectionRefreshed() {

            }
        };

        mSingleRowCalendar.setCalendarViewManager(calendarViewManager);
        mSingleRowCalendar.setCalendarSelectionManager(calendarSelectionManager);
        mSingleRowCalendar.setCalendarChangesObserver(calendarChangesObserver);
        mSingleRowCalendar.setFutureDaysCount(6);
        mSingleRowCalendar.setIncludeCurrentDate(true);
        mSingleRowCalendar.init();

        calendarChangesObserver.whenSelectionChanged(false, 0 , new Date());
        mSingleRowCalendar.select(0);
    }

    /**
     * 할일 리스트 API
     */
    private void scheduleListAPI() {
        ScheduleModel scheduleReqeust = new ScheduleModel();
        scheduleReqeust.setMember_idx(Prefs.getString(Constants.MEMBER_IDX,""));
        scheduleReqeust.setToday(mYear + "-" + mMonth + "-" + mDay);
        scheduleReqeust.setHouse_idx(Prefs.getString(Constants.HOUSE_IDX,""));
        CommonRouter.api().schedule_list(Tools.getInstance().getMapper(scheduleReqeust)).enqueue(new Callback<ScheduleModel>() {
            @Override
            public void onResponse(Call<ScheduleModel> call, Response<ScheduleModel> response) {
                ScheduleModel mScheduleResponse = response.body();
                if (mScheduleResponse.getData_array() != null) {
                    mCntTextView.setText(String.valueOf(mScheduleResponse.getData_array().size()));
                    mScheduleList.addAll(mScheduleResponse.getData_array());
                }
                mScheduleAdapter.setNewData(mScheduleList);
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
     * 일정 추가
     */
    @OnClick(R.id.add_button)
    public void addTouched() {
        Intent addScheduleActivity = AddScheduleActivity.getStartIntent(mActivity,"", new AddScheduleActivity.OnAddScheduleListener() {
            @Override
            public void onRefresh() {
                mScheduleList.clear();
                scheduleListAPI();
            }
        });
        startActivity(addScheduleActivity, NoommateActivity.TRANS.PUSH);
    }


}

