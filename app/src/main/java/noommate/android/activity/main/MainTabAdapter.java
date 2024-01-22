package noommate.android.activity.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import noommate.android.activity.RocateerActivity;
import noommate.android.activity.main.calculate.CalculateFragment;
import noommate.android.activity.main.home.HomeFragment;
import noommate.android.activity.main.my.MyFragment;
import noommate.android.activity.main.schedule.ScheduleFragment;

public class MainTabAdapter extends FragmentStatePagerAdapter {

  private HomeFragment homeFragment;
  private CalculateFragment calculateFragment;
  private MyFragment myFragment;
  private ScheduleFragment scheduleFragment;
  private RocateerActivity mActivity;

  public MainTabAdapter(@NonNull FragmentManager fm) {
    super(fm);

  }

  @NonNull
  @Override
  public Fragment getItem(int position) {
    if (position == 0) {
      homeFragment = new HomeFragment();
      return homeFragment;
    } else if (position == 1) {
      scheduleFragment = new ScheduleFragment();
      return scheduleFragment;
    } else if (position == 2) {
      calculateFragment = new CalculateFragment();
      return calculateFragment;
    } else if (position == 3) {
      myFragment = new MyFragment();
      return myFragment;
    }
    return null;
  }

  @Override
  public int getCount() {
    return 4;
  }
}

