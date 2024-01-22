package noommate.android.activity.Intro;


import android.content.Context;
import android.content.Intent;

import androidx.appcompat.widget.AppCompatEditText;

import butterknife.BindView;
import butterknife.OnClick;
import noommate.android.R;
import noommate.android.activity.RocateerActivity;

public class ChangePwActivity extends RocateerActivity {
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
  AppCompatEditText mBeforePwEditText;
  @BindView(R.id.new_pw_edit_text)
  AppCompatEditText mNewPwEditText;
  @BindView(R.id.new_pw_confirm_edit_text)
  AppCompatEditText mNewPwConfirmEditText;
  // --------------------------------------------------------------------------------------------
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
    initToolbar("비밀번호 변경");
  }

  @Override
  protected void initRequest() {

  }

  //--------------------------------------------------------------------------------------------
  // MARK : Local functions
  //--------------------------------------------------------------------------------------------

  /**
   * 비밀번호 변경 API
   */
  private void memberPwModUpAPI() {
//    MemberModel memberModel = new MemberModel();
//    memberModel.setMember_idx(Prefs.getString(Constants.MEMBER_IDX, ""));
//    memberModel.setMember_pw(mBeforePwEditText.getText().toString());
//    memberModel.setNew_member_pw(mNewPwEditText.getText().toString());
//    memberModel.setNew_member_pw_check(mNewPwConfirmEditText.getText().toString());
//
//    CommonRouter.api().member_pw_mod_up(Tools.getInstance().getMapper(memberModel)).enqueue(new Callback<MemberModel>() {
//      @Override
//      public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
//        if (Tools.getInstance().isSuccessResponse(response)) {
//          Prefs.putString(Constants.MEMBER_PW, mNewPwEditText.getText().toString());
//          finishWithRemove();
//        }
//      }
//
//      @Override
//      public void onFailure(Call<MemberModel> call, Throwable t) {
////        Tools.getInstance(mActivity).showToast(mActivity.getString(R.string.api_error));
//      }
//    });
  }
  //--------------------------------------------------------------------------------------------
  // MARK : Bind Actions
  //--------------------------------------------------------------------------------------------

  /**
   * change pw
   *
   * @author khh
   * @since 6/23/21
   **/
  @OnClick(R.id.change_pw_round_rect_view)
  public void changePwTouched() {
//    memberPwModUpAPI();
  }
}
