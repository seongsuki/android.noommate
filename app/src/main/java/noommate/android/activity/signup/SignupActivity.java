package noommate.android.activity.signup;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.florent37.shapeofview.shapes.RoundRectView;
import noommate.android.activity.NoommateActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import noommate.android.R;
import noommate.android.commons.Tools;
import noommate.android.models.MemberModel;
import noommate.android.models.api.CommonRouter;

public class SignupActivity extends NoommateActivity {
  //--------------------------------------------------------------------------------------------
  // MARK : GET START INTENT
  //--------------------------------------------------------------------------------------------
  public static Intent getStartIntent(Context context) {
    Intent intent = new Intent(context, SignupActivity.class);
    return intent;
  }

  //--------------------------------------------------------------------------------------------
  // MARK : Bind Area
  //--------------------------------------------------------------------------------------------
  @BindView(R.id.default_text_view)
  AppCompatTextView mDefaultTextView;
  @BindView(R.id.id_edit_text)
  AppCompatEditText mIdEditText;
  @BindView(R.id.email_edit_text)
  AppCompatEditText mEmailEditText;
  @BindView(R.id.pw_edit_text)
  AppCompatEditText mPwEditText;
  @BindView(R.id.pw_confirm_edit_text)
  AppCompatEditText mPwConfirmEditText;
  @BindView(R.id.id_round_view)
  RoundRectView mIdRoundView;
  @BindView(R.id.circle_image_view)
  AppCompatImageView mCircleImageView;
  @BindView(R.id.info_layout)
  LinearLayout mInfoLayout;
  //--------------------------------------------------------------------------------------------
  // MARK : Local variables
  //--------------------------------------------------------------------------------------------
  private MemberModel mMemberModel = new MemberModel();
  private String mYn = "N";

  //--------------------------------------------------------------------------------------------
  // MARK : Override
  //--------------------------------------------------------------------------------------------
  @Override
  protected int inflateLayout() {
    return R.layout.activity_signup;
  }

  @Override
  protected void initLayout() {
    initToolbar("회원가입");
    mIdEditText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mYn = "N";
      }

      @Override
      public void afterTextChanged(Editable editable) {

      }
    });
  }

  @Override
  protected void initRequest() {

  }

  //--------------------------------------------------------------------------------------------
  // MARK : Local functions
  //--------------------------------------------------------------------------------------------


  /**
   * 아이디 중복검사 API
   */
  private void memberIdCheckAPI() {
    MemberModel memberRequest = new MemberModel();
    memberRequest.setMember_id(mIdEditText.getText().toString());
    CommonRouter.api().member_id_check(Tools.getInstance(mActivity).getMapper(memberRequest)).enqueue(new Callback<MemberModel>() {
      @Override
      public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
        MemberModel mMemberResponse = response.body();
        if (mMemberResponse.getCode().equals("1000")) {
          mYn = "Y";
          mIdRoundView.setBorderColor(mActivity.getColor(R.color.color_87b7ff));
          mInfoLayout.setVisibility(View.VISIBLE);
          mDefaultTextView.setText("사용가능한 아이디 입니다.");
          mCircleImageView.setImageDrawable(mActivity.getDrawable(R.drawable.blue_circle));
          mDefaultTextView.setTextColor(mActivity.getColor(R.color.color_87b7ff));
        } else {
          mYn = "N";
          mIdRoundView.setBorderColor(mActivity.getColor(R.color.colorPoint));
          mInfoLayout.setVisibility(View.VISIBLE);
          mDefaultTextView.setText("아이디를 입력해주세요.");
          mCircleImageView.setImageDrawable(mActivity.getDrawable(R.drawable.red_circle));
          mDefaultTextView.setTextColor(mActivity.getColor(R.color.colorPoint));
        }
      }

      @Override
      public void onFailure(Call<MemberModel> call, Throwable t) {

      }
    });
  }

  /**
   * 이메일 사용가능 여부
   */
  private void emailPasswordCheckInAPI() {
    MemberModel memberRequest = new MemberModel();
    memberRequest.setMember_email(mEmailEditText.getText().toString());
    memberRequest.setMember_pw(mPwEditText.getText().toString());
    memberRequest.setMember_pw_confirm(mPwConfirmEditText.getText().toString());
    CommonRouter.api().passwordemail_check_in(Tools.getInstance(mActivity).getMapper(memberRequest)).enqueue(new Callback<MemberModel>() {
      @Override
      public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
        MemberModel mMemberResponse = response.body();
        if (mMemberResponse.getCode().equals("1000")) {
          mMemberModel.setMember_id(mIdEditText.getText().toString());
          mMemberModel.setMember_pw(mPwEditText.getText().toString());
          mMemberModel.setMember_pw_confirm(mPwConfirmEditText.getText().toString());
          mMemberModel.setMember_email(mEmailEditText.getText().toString());
          Intent signUpStepTwoActivity = SignupStepTwoActivity.getStartIntent(mActivity, mMemberModel);
          startActivity(signUpStepTwoActivity,TRANS.PUSH);


        } else {
          showAlertDialog(mMemberResponse.getCode_msg(), "확인", new DialogEventListener() {
            @Override
            public void onReceivedEvent() {

            }
          });
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
   * 회원가입
   */
  @OnClick(R.id.next_button)
  public void signupTouched() {
    if (mYn.equals("Y")) {
      emailPasswordCheckInAPI();
    } else {
      showAlertDialog("아이디 중복 확인이 필요합니다.", "확인", new DialogEventListener() {
        @Override
        public void onReceivedEvent() {

        }
      });
    }
  }

  /**
   * 중복확인
   */
  @OnClick(R.id.confirm_button)
  public void comfirmTouched() {
    memberIdCheckAPI();
  }

}