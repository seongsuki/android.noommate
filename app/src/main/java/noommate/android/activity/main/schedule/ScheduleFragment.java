package noommate.android.activity.main.schedule;

import android.content.Context;
import android.content.Intent;

import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import noommate.android.R;
import noommate.android.activity.RocateerFragment;
import noommate.android.commons.SwipeViewPager;

public class ScheduleFragment extends RocateerFragment {
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
    @BindView(R.id.schedule_view_pager)
    SwipeViewPager mScheduleViewPager;
    @BindView(R.id.schedule_tab_layout)
    TabLayout mScheduleTabLayout;

    //--------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------
    private ScheduleTabAdapter mScheduleTabAdapter;

    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    @Override
    protected int inflateLayout() {
        return R.layout.fragment_schedule;
    }

    @Override
    protected void initLayout() {

    }

    @Override
    protected void initRequest() {
        initMenu();

    }

    //--------------------------------------------------------------------------------------------
    // MARK : Local functions
    //--------------------------------------------------------------------------------------------

    /**
     * 메뉴 세팅
     */
    private void initMenu() {

        mScheduleTabAdapter = new ScheduleTabAdapter(getChildFragmentManager());
        mScheduleViewPager.setAdapter(mScheduleTabAdapter);
        mScheduleViewPager.setOffscreenPageLimit(3);
        mScheduleViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mScheduleTabLayout));
        mScheduleViewPager.setPagingEnabled(true);
        mScheduleTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;

                }
                mScheduleViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }


    //--------------------------------------------------------------------------------------------
    // MARK : Bind Actions
    //--------------------------------------------------------------------------------------------


}

