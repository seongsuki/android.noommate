package noommate.android.activity.main.home;

import android.content.Context;
import android.content.Intent;

import butterknife.OnClick;
import noommate.android.R;
import noommate.android.activity.RocateerActivity;
import noommate.android.activity.main.MainActivity;

public class AddHouseStepThreeActivity extends RocateerActivity {
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

    }

    @Override
    protected void initRequest() {
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

