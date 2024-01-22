package noommate.android.activity.main.my;

import android.content.Context;
import android.content.Intent;
import android.widget.CheckBox;

import androidx.appcompat.widget.AppCompatEditText;

import com.pixplicity.easyprefs.library.Prefs;

import butterknife.BindView;
import butterknife.OnClick;
import noommate.android.R;
import noommate.android.activity.NoommateActivity;
import noommate.android.activity.signin.SigninActivity;
import noommate.android.commons.Constants;
import noommate.android.commons.Tools;
import noommate.android.models.MemberModel;
import noommate.android.models.api.CommonRouter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WithDrawalActivity extends NoommateActivity {
    //--------------------------------------------------------------------------------------------
    // MARK : GET START INTENT
    //--------------------------------------------------------------------------------------------
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, WithDrawalActivity.class);
        return intent;
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Area
    //--------------------------------------------------------------------------------------------
    @BindView(R.id.withdrawal_reason_edit_text)
    AppCompatEditText mEditText;
    @BindView(R.id.withdrawal_checkbox)
    CheckBox mCheckBox;

    //--------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------

    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    @Override
    protected int inflateLayout() {
        return R.layout.activity_withdrawal;
    }

    @Override
    protected void initLayout() {
        mToolbarTitle.setText("회원탈퇴");

    }

    @Override
    protected void initRequest() {
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Local functions
    //--------------------------------------------------------------------------------------------

    /**
     * 회원탈퇴 API
     */
    private void memberOutUpAPI() {
        MemberModel memberRequest = new MemberModel();
        memberRequest.setMember_idx(Prefs.getString(Constants.MEMBER_IDX,""));
        memberRequest.setMember_leave_yn(mCheckBox.isChecked() ? "Y" : "N");
        memberRequest.setMember_leave_reason(mEditText.getText().toString());
        CommonRouter.api().member_out_up(Tools.getInstance().getMapper(memberRequest)).enqueue(new Callback<MemberModel>() {
            @Override
            public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
                if (Tools.getInstance(mActivity).isSuccessResponse(response)) {
                    Prefs.remove(Constants.MEMBER_IDX);
                    Prefs.remove(Constants.MEMBER_ID);
                    Prefs.remove(Constants.MEMBER_PW);
                    Prefs.remove(Constants.MEMBER_IDX);
                    Prefs.remove(Constants.HOUSE_CODE);
                    Prefs.remove(Constants.MEMBER_JOIN_TYPE);
                    removeAllActivity();
                    Intent signinActivity = SigninActivity.getStartIntent(mActivity);
                    startActivity(signinActivity,TRANS.ZOOM);
                }
            }

            @Override
            public void onFailure(Call<MemberModel> call, Throwable t) {

            }
        });
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Actions
    //--------------------------------------------------------------------------------------------

    /**
     * 회원탈퇴
     */
    @OnClick(R.id.withdrawal_button)
    public void withDrawalTouched() {
        memberOutUpAPI();
    }





}
