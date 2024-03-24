package noommate.android.activity.main.home;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.pixplicity.easyprefs.library.Prefs;

import butterknife.BindView;
import butterknife.OnClick;
import noommate.android.R;
import noommate.android.activity.NoommateActivity;
import noommate.android.activity.main.MainActivity;
import noommate.android.commons.Constants;
import noommate.android.commons.Tools;

public class AddHouseStepTwoActivity extends NoommateActivity {
    //--------------------------------------------------------------------------------------------
    // MARK : GET START INTENT
    //--------------------------------------------------------------------------------------------
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, AddHouseStepTwoActivity.class);
        return intent;
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Area
    //--------------------------------------------------------------------------------------------
    @BindView(R.id.house_code_text_view)
    AppCompatTextView mHouseCodeTextView;

    //--------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------
    private long backpressedTime = 0;
    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    @Override
    protected int inflateLayout() {
        return R.layout.activity_add_house_step_two;
    }

    @Override
    protected void initLayout() {
        initToolbar("하우스 만들기");
        mBackButton.setVisibility(View.GONE);
        mHouseCodeTextView.setText(Prefs.getString(Constants.HOUSE_CODE,""));

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
     * 복사하기
     */
    @OnClick(R.id.copy_button)
    public void copyTouched() {
        Tools.getInstance().copyToClipboard(mActivity, mHouseCodeTextView.getText().toString());
    }

    /**
     * 초대하기
     */
    @OnClick(R.id.invite_button)
    public void inviteTouched() {
        Intent addHouseStepThreeActivity = AddHouseStepThreeActivity.getStartIntent(mActivity);
        startActivity(addHouseStepThreeActivity,TRANS.PUSH);

    }

    /**
     * 다음에 하기
     */
    @OnClick(R.id.next_time_button)
    public void nextTimeTouched() {
        removeAllActivity();
        Intent mainActivity = MainActivity.getStartIntent(mActivity);
        startActivity(mainActivity,TRANS.ZOOM);

    }




}
