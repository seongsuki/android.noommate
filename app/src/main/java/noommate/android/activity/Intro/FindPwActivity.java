package noommate.android.activity.Intro;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.florent37.shapeofview.shapes.RoundRectView;
import noommate.android.R;
import noommate.android.activity.NoommateActivity;
import noommate.android.commons.Tools;
import noommate.android.models.MemberModel;
import noommate.android.models.api.CommonRouter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindPwActivity  extends NoommateActivity {
    //--------------------------------------------------------------------------------------------
    // MARK : GET START INTENT
    //--------------------------------------------------------------------------------------------
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, FindPwActivity.class);
        return intent;
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Area
    //--------------------------------------------------------------------------------------------
    @BindView(R.id.id_default_text_view)
    AppCompatTextView mIdDefaultTextView;
    @BindView(R.id.email_default_text_view)
    AppCompatTextView mEmailDefaultTextView;
    @BindView(R.id.id_edit_text)
    AppCompatEditText mIdEditText;
    @BindView(R.id.email_edit_text)
    AppCompatEditText mEmailEditText;
    @BindView(R.id.email_round_view)
    RoundRectView mEmailRoundView;
    @BindView(R.id.id_round_view)
    RoundRectView mIdRoundView;
    @BindView(R.id.find_layout)
    AppCompatTextView mFindLayout;

    // --------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------

    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    @Override
    protected int inflateLayout() {
        return R.layout.activity_find_pw;
    }

    @Override
    protected void initLayout() {
        initToolbar("비밀번호 찾기");
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
    private void findPwAPI() {
        MemberModel memberRequest = new MemberModel();
        memberRequest.setMember_id(mIdEditText.getText().toString());
        memberRequest.setMember_email(mEmailEditText.getText().toString());
        CommonRouter.api().member_pw_reset_send_email(Tools.getInstance().getMapper(memberRequest)).enqueue(new Callback<MemberModel>() {
            @Override
            public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
                MemberModel mMemberResponse = response.body();
                if (mMemberResponse.getCode().equals("1000")) {
                    mFindLayout.setVisibility(View.VISIBLE);
                } else {
                    mEmailDefaultTextView.setVisibility(View.VISIBLE);
                    mIdDefaultTextView.setVisibility(View.VISIBLE);
                    mFindLayout.setVisibility(View.GONE);
                    mEmailRoundView.setBorderColor(mActivity.getColor(R.color.colorAccent));
                    mIdRoundView.setBorderColor(mActivity.getColor(R.color.colorAccent));
                    mEmailEditText.setTextColor(mActivity.getColor(R.color.colorAccent));
                    mIdEditText.setTextColor(mActivity.getColor(R.color.colorAccent));
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

    @OnClick(R.id.find_pw_button)
    public void findPwTouched() {
       findPwAPI();

    }


}
