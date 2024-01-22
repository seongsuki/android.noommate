package noommate.android.activity.signup;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import butterknife.OnClick;
import noommate.android.R;
import noommate.android.activity.RocateerActivity;
import noommate.android.activity.main.MainActivity;
import noommate.android.activity.signin.SigninActivity;;


public class SignupCompleteActivity extends RocateerActivity {
    //--------------------------------------------------------------------------------------------
    // MARK : GET START INTENT
    //--------------------------------------------------------------------------------------------
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SignupCompleteActivity.class);
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
        return R.layout.activity_signup_complete;
    }

    @Override
    protected void initLayout() {
        initToolbar("가입완료");
        mBackButton.setVisibility(View.GONE);

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
        removeAllActivity();
        Intent mainActivity = MainActivity.getStartIntent(mActivity);
        startActivity(mainActivity,TRANS.ZOOM);
    }



}
