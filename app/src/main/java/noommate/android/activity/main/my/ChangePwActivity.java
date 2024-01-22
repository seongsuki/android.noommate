package noommate.android.activity.main.my;


import android.content.Context;
import android.content.Intent;

import androidx.appcompat.widget.AppCompatEditText;

import com.pixplicity.easyprefs.library.Prefs;

import butterknife.BindView;
import butterknife.OnClick;
import noommate.android.R;
import noommate.android.activity.NoommateActivity;
import noommate.android.commons.Constants;
import noommate.android.commons.Tools;
import noommate.android.models.MemberModel;
import noommate.android.models.api.CommonRouter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePwActivity extends NoommateActivity {
    //--------------------------------------------------------------------------------------------
    // MARK : GET START INTENT
    //--------------------------------------------------------------------------------------------
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ChangePwActivity.class);
        return intent;
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Area
    //--------------------------------------------------------------------------------------------
    @BindView(R.id.before_pw_edit_text)
    AppCompatEditText mPwEditText;
    @BindView(R.id.new_pw_edit_text)
    AppCompatEditText mNewPwEditText;
    @BindView(R.id.new_pw_confirm_edit_text)
    AppCompatEditText mNewPwConfirmEditText;

    //--------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------

    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    @Override
    protected int inflateLayout() {
        return R.layout.activity_change_pw;
    }

    @Override
    protected void initLayout() {
        mToolbarTitle.setText("비밀번호 변경");

    }

    @Override
    protected void initRequest() {
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Local functions
    //--------------------------------------------------------------------------------------------
    /**
     * 비밀번호 찾기
     */
    private void pwModUpAPI() {
        MemberModel memberRequest=  new MemberModel();
        memberRequest.setMember_idx(Prefs.getString(Constants.MEMBER_IDX,""));
        memberRequest.setMember_pw(mPwEditText.getText().toString());
        memberRequest.setMember_new_pw(mNewPwEditText.getText().toString());
        memberRequest.setMember_confirm_pw(mNewPwConfirmEditText.getText().toString());
        CommonRouter.api().pw_mod_up(Tools.getInstance().getMapper(memberRequest)).enqueue(new Callback<MemberModel>() {
            @Override
            public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
                if (Tools.getInstance().isSuccessResponse(response)) {
                    finishWithRemove();
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
     * 변경
     */
    @OnClick(R.id.change_button)
    public void changeTouched() {
        pwModUpAPI();
    }





}

