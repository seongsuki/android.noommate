package noommate.android.activity.main.home;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.widget.AppCompatTextView;

import com.pixplicity.easyprefs.library.Prefs;

import butterknife.BindView;
import butterknife.OnClick;
import noommate.android.R;
import noommate.android.activity.NoommateActivity;
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
        mHouseCodeTextView.setText(Prefs.getString(Constants.HOUSE_CODE,""));

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

    }

    /**
     * 다음에 하기
     */
    @OnClick(R.id.next_time_button)
    public void nextTimeTouched() {
        Intent addHouseStepThreeActivity = AddHouseStepThreeActivity.getStartIntent(mActivity);
        startActivity(addHouseStepThreeActivity,TRANS.PUSH);

    }




}
