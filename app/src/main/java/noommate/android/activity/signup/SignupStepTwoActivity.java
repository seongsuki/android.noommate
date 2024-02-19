package noommate.android.activity.signup;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.pixplicity.easyprefs.library.Prefs;

import butterknife.BindView;
import butterknife.OnClick;
import noommate.android.activity.NoommateActivity;
import noommate.android.activity.commons.terms.TermsActivity;
import noommate.android.commons.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import noommate.android.R;
import noommate.android.commons.Tools;
import noommate.android.models.MemberModel;
import noommate.android.models.api.CommonRouter;
import timber.log.Timber;

public class SignupStepTwoActivity extends NoommateActivity {
    //--------------------------------------------------------------------------------------------
    // MARK : GET START INTENT
    //--------------------------------------------------------------------------------------------
    public static Intent getStartIntent(Context context, MemberModel memberModel) {
        Intent intent = new Intent(context, SignupStepTwoActivity.class);
        mMemberModel = memberModel;
        return intent;
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Area
    //--------------------------------------------------------------------------------------------
    @BindView(R.id.nickname_edit_text)
    AppCompatEditText mNicknameEditText;
    @BindView(R.id.housecode_edit_text)
    AppCompatEditText mHouseCodeEditText;
    @BindView(R.id.all_term_check_box)
    CheckBox mAllTermCheckBox;
    @BindView(R.id.term01_check_box)
    CheckBox mTerm01CheckBox;
    @BindView(R.id.term02_check_box)
    CheckBox mTerm02CheckBox;
    @BindView(R.id.add_text_view)
    AppCompatTextView mAddTextView;
    @BindView(R.id.back_layout)
    RelativeLayout mBackLayout;
    @BindView(R.id.face_image_view)
    AppCompatImageView mFaceImageView;
    @BindView(R.id.back_image_view)
    AppCompatImageView mBackImageView;
    //--------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------
    private static MemberModel mMemberModel;
    private MemberModel memberRequest = new MemberModel();


    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    @Override
    protected int inflateLayout() {
        return R.layout.activity_signup_step_two;
    }

    @Override
    protected void initLayout() {
        mToolbarTitle.setText("회원가입");
        mTerm01CheckBox.setOnCheckedChangeListener(mCheckboxChangeListener);
        mTerm02CheckBox.setOnCheckedChangeListener(mCheckboxChangeListener);

    }

    @Override
    protected void initRequest() {
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Local functions
    //--------------------------------------------------------------------------------------------
    /**
     * 회원가입 API
     */
  private void memberRegInAPI() {
    memberRequest.setMember_id(mMemberModel.getMember_id().toString());
    memberRequest.setMember_pw(mMemberModel.getMember_pw().toString());
    memberRequest.setMember_pw_confirm(mMemberModel.getMember_pw_confirm());
    memberRequest.setMember_name(mNicknameEditText.getText().toString());
    memberRequest.setMember_email(mMemberModel.getMember_email());
    memberRequest.setId_check("Y");
    memberRequest.setHouse_code(mHouseCodeEditText.getText().toString());

    CommonRouter.api().member_reg_in(Tools.getInstance(mActivity).getMapper(memberRequest)).enqueue(new Callback<MemberModel>() {
      @Override
      public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
          MemberModel mMemberResposne = response.body();
        if (Tools.getInstance(mActivity).isSuccessResponse(response)) {
            Prefs.putString(Constants.MEMBER_ID, mMemberResposne.getMember_id());
            Prefs.putString(Constants.MEMBER_PW, mMemberResposne.getMember_pw());
            Prefs.putString(Constants.MEMBER_IDX, mMemberResposne.getMember_idx());
            Prefs.putString(Constants.MEMBER_JOIN_TYPE, mMemberResposne.getMember_join_type());
            Intent signUpCompleteActivity = SignupCompleteActivity.getStartIntent(mActivity);
            startActivity(signUpCompleteActivity,TRANS.PRESENT);
        }
      }

      @Override
      public void onFailure(Call<MemberModel> call, Throwable t) {
        showSnackBar("오류 발생");
      }
    });
  }

    /**
     * 약관 check box event
     *
     * @author khh
     * @since 5/27/21
     **/
    CompoundButton.OnCheckedChangeListener mCheckboxChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.term01_check_box:
                case R.id.term02_check_box:
                    if (mTerm01CheckBox.isChecked() && mTerm02CheckBox.isChecked()) { // && mTerm04CheckBox.isChecked()
                        mAllTermCheckBox.setChecked(true);
                    } else {
                        mAllTermCheckBox.setChecked(false);
                    }
                    break;
            }
        }
    };


    //--------------------------------------------------------------------------------------------
    // MARK : Bind Actions
    //--------------------------------------------------------------------------------------------

    /**
     * 캐릭터 생성
     */
    @OnClick(R.id.back_layout)
    public void backLayoutTouched() {
        Intent addCharActivity = AddCharActivity.getStartIntent(mActivity,"0","0","0", new AddCharActivity.OnAddCharListener() {
            @Override
            public void onRefresh(String back, String face, String color) {
                mAddTextView.setVisibility(View.GONE);
                mBackImageView.setVisibility(View.VISIBLE);
                mFaceImageView.setVisibility(View.VISIBLE);
                memberRequest.setMember_role1(back);
                memberRequest.setMember_role2(face);
                memberRequest.setMember_role3(color);
                Timber.i("back : " + back);
                Timber.i("face : " + face);
                Timber.i("color : " + color);

                // 얼굴
                if (back.equals("0")) {
                    mBackImageView.setImageDrawable(mActivity.getDrawable(R.drawable.back5));
                } else if (back.equals("1")) {
                    mBackImageView.setImageDrawable(mActivity.getDrawable(R.drawable.back4));
                } else if (back.equals("2")) {
                    mBackImageView.setImageDrawable(mActivity.getDrawable(R.drawable.back3));
                } else if (back.equals("3")) {
                    mBackImageView.setImageDrawable(mActivity.getDrawable(R.drawable.back2));
                } else if (back.equals("4")) {
                    mBackImageView.setImageDrawable(mActivity.getDrawable(R.drawable.back1));
                }
                // 표정
                if (face.equals("0")) {
                    mFaceImageView.setImageDrawable(mActivity.getDrawable(R.drawable.group_191));
                } else if (face.equals("1")) {
                    mFaceImageView.setImageDrawable(mActivity.getDrawable(R.drawable.group_197));
                } else if (face.equals("2")) {
                    mFaceImageView.setImageDrawable(mActivity.getDrawable(R.drawable.group_193));
                } else if (face.equals("3")) {
                    mFaceImageView.setImageDrawable(mActivity.getDrawable(R.drawable.group_194));
                } else if (face.equals("4")) {
                    mFaceImageView.setImageDrawable(mActivity.getDrawable(R.drawable.group_195));
                } else if (face.equals("5")) {
                    mFaceImageView.setImageDrawable(mActivity.getDrawable(R.drawable.group_196));
                }
                // 색상
                if (color.equals("0")) {
                    mBackLayout.setBackgroundColor(mActivity.getColor(R.color.color_ff6d6d));
                } else if (color.equals("1")) {
                    mBackLayout.setBackgroundColor(mActivity.getColor(R.color.color_ffcd4b));
                } else if (color.equals("2")) {
                    mBackLayout.setBackgroundColor(mActivity.getColor(R.color.color_63d08f));
                } else if (color.equals("3")) {
                    mBackLayout.setBackgroundColor(mActivity.getColor(R.color.color_87b7ff));
                } else if (color.equals("4")) {
                    mBackLayout.setBackgroundColor(mActivity.getColor(R.color.color_798ed6));
                } else if (color.equals("5")) {
                    mBackLayout.setBackgroundColor(mActivity.getColor(R.color.color_bfa0ff));
                }
            }
        });
        startActivity(addCharActivity,TRANS.PUSH);
    }

    /**
     * 전체 약관 체크
     */
    @OnClick(R.id.all_term_check_box)
    public void allTermTouched() {
        if (mAllTermCheckBox.isChecked() == true) {
            mTerm01CheckBox.setChecked(true);
            mTerm02CheckBox.setChecked(true);
        } else {
            mTerm01CheckBox.setChecked(false);
            mTerm02CheckBox.setChecked(false);
        }
    }

    /**
     * 서비스
     */
    @OnClick(R.id.terms1_button)
    public void terms1Touched() {
        Intent termsActivity = TermsActivity.getStartIntent(mActivity, TermsActivity.TermsType.SERVICE);
        startActivity(termsActivity, TRANS.PUSH);
    }

    /**
     * 개인정보
     */
    @OnClick(R.id.terms2_button)
    public void terms2Touched() {
        Intent termsActivity = TermsActivity.getStartIntent(mActivity, TermsActivity.TermsType.PRI);
        startActivity(termsActivity, TRANS.PUSH);
    }

    /**
     * 다음
     */
    @OnClick(R.id.next_button)
    public void nextTouched() {
      memberRegInAPI();
    }



}

