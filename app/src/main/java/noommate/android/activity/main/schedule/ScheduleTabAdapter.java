package noommate.android.activity.main.schedule;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import noommate.android.activity.main.schedule.history.HistoryFragment;
import noommate.android.activity.main.schedule.schedulepage.SchedulePageFragment;
import noommate.android.activity.main.schedule.todo.TodoFragment;

public class ScheduleTabAdapter extends FragmentStatePagerAdapter {

    private SchedulePageFragment schedulePageFragment;
    private HistoryFragment historyFragment;
    private TodoFragment todoFragment;

    public ScheduleTabAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            schedulePageFragment = new SchedulePageFragment();
            return schedulePageFragment;
        } else if (position == 1) {
            historyFragment = new HistoryFragment();
            return historyFragment;
        } else if (position == 2) {
            todoFragment = new TodoFragment();
            return todoFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}


