package noommate.android.activity.main;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.tabs.TabLayout;
import com.pixplicity.easyprefs.library.Prefs;

import butterknife.BindView;
import noommate.android.R;
import noommate.android.activity.RocateerActivity;
import noommate.android.commons.Constants;
import noommate.android.commons.SwipeViewPager;
import noommate.android.models.AlarmModel;


public class MainActivity extends RocateerActivity {
  //--------------------------------------------------------------------------------------------
  // MARK : GET START INTENT
  //--------------------------------------------------------------------------------------------
  public static Intent getStartIntent(Context context) {
    Intent intent = new Intent(context, MainActivity.class);
    return intent;
  }

  public static Intent getStartIntent(Context context, AlarmModel alarmModel) {
    Intent intent = new Intent(context, MainActivity.class);
    mAlarmModel = alarmModel;
    return intent;
  }

  //--------------------------------------------------------------------------------------------
  // MARK : Bind Area
  //--------------------------------------------------------------------------------------------
  @BindView(R.id.main_view_pager)
  SwipeViewPager mMainViewPager;
  @BindView(R.id.main_tab_layout)
  TabLayout mMainTabLayout;

  //--------------------------------------------------------------------------------------------
  // MARK : Local variables
  //--------------------------------------------------------------------------------------------
  private MainTabAdapter mMainTabAdapter;
  private static AlarmModel mAlarmModel;

  private int mRecentPosition = 0;

  //--------------------------------------------------------------------------------------------
  // MARK : Override
  //--------------------------------------------------------------------------------------------
  @Override
  protected int inflateLayout() {
    return R.layout.activity_main;
  }

  @Override
  protected void initLayout() {
    setTabItem();



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

    mMainTabAdapter = new MainTabAdapter(getSupportFragmentManager());
    mMainViewPager.setAdapter(mMainTabAdapter);
    mMainViewPager.setOffscreenPageLimit(4);
    mMainViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mMainTabLayout));
    mMainViewPager.setPagingEnabled(true);

    mMainTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() == 1 || tab.getPosition() == 2) {
          if (Prefs.getString(Constants.HOUSE_CODE, "").equals("")) {
            showAlertDialog("아직 소속된 하우스가 없어요.\n메이트가 되어 더 많은 눔메이트의\n서비스를 이용해 보세요!", "확인", new DialogEventListener() {
              @Override
              public void onReceivedEvent() {

              }
            });
            mMainTabLayout.selectTab(mMainTabLayout.getTabAt(mRecentPosition));
            return;
          }
        }

        switch (tab.getPosition()) {
          case 0:
            Intent homeRefresh = new Intent(Constants.HOME_REFRESH);
            mActivity.sendBroadcast(homeRefresh);
            break;
          case 1:
            break;
          case 2:
            Intent calculateRefresh = new Intent(Constants.CALCULATE_REFRESH);
            mActivity.sendBroadcast(calculateRefresh);
            break;
          case 3:
            Intent myRefresh = new Intent(Constants.MY_REFRESH);
            mActivity.sendBroadcast(myRefresh);
            break;
        }
        mMainViewPager.setCurrentItem(tab.getPosition());
      }
      @Override
      public void onTabUnselected(TabLayout.Tab tab) {
      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {
      }
    });

  }

  /**
   * 커스텀 탭 아이템 설정
   */
  private void setTabItem() {
    String[] menuText = {"홈", "일정", "가계부", "MY"};
    int[] menuIcon = {R.drawable.selector_menu_1, R.drawable.selector_menu_2, R.drawable.selector_menu_3, R.drawable.selector_menu_4};

    for (int i = 0; i < 4; i++) {
      View menuItem = getLayoutInflater().inflate(R.layout.main_menu_tab, null);
      AppCompatImageView menuImageView = menuItem.findViewById(R.id.menu_icon_image_view);
      AppCompatTextView menu1TextView = menuItem.findViewById(R.id.menu_text_view);
      menuImageView.setImageResource(menuIcon[i]);
      menu1TextView.setText(menuText[i]);
      mMainTabLayout.getTabAt(i).setCustomView(menuItem);
    }
    mMainTabLayout.getTabAt(0).select();
    mMainViewPager.setCurrentItem(0);
  }



  /**
   * Move Tab Position
   *
   * @author khh
   * @since 3/5/21
   **/
  public void moveTab(int tabIndex) {
    mMainTabLayout.getTabAt(tabIndex).select();
    mMainViewPager.setCurrentItem(tabIndex);


  }

  //--------------------------------------------------------------------------------------------
  // MARK : Bind Actions
  //--------------------------------------------------------------------------------------------

}