package noommate.android.activity.main.home;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import butterknife.OnClick;
import noommate.android.R;
import noommate.android.activity.NoommateActivity;
import noommate.android.activity.main.MainActivity;

public class AddHouseStepThreeActivity extends NoommateActivity {
    //--------------------------------------------------------------------------------------------
    // MARK : GET START INTENT
    //--------------------------------------------------------------------------------------------
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, AddHouseStepThreeActivity.class);
        return intent;
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Area
    //--------------------------------------------------------------------------------------------

    //--------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------
    private long backpressedTime = 0;

    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    @Override
    protected int inflateLayout() {
        return R.layout.activity_add_house_step_three;
    }

    @Override
    protected void initLayout() {
        initToolbar("하우스 만들기");
        mBackButton.setVisibility(View.GONE);

    }

    @Override
    protected void initRequest() {
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backpressedTime + 2000) {
            backpressedTime = System.currentTimeMillis();
            showSnackBar("\'뒤로가기\' 버튼을 한번 더 누르시면 종료됩니다.");
        } else if (System.currentTimeMillis() <= backpressedTime + 2000) {
            removeAllActivity();
        }
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Local functions
    //--------------------------------------------------------------------------------------------

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Actions
    //--------------------------------------------------------------------------------------------

    /**
     * 홈으로
     */
    @OnClick(R.id.home_button)
    public void homeTouched() {
        Intent mainActivity = MainActivity.getStartIntent(mActivity);
        startActivity(mainActivity,TRANS.ZOOM);
        removeAllActivity();
    }



}

