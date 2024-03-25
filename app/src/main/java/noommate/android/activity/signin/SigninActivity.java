package noommate.android.activity.signin;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.widget.AppCompatEditText;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.util.Utility;
import com.kakao.sdk.user.UserApiClient;
import com.pixplicity.easyprefs.library.Prefs;

import butterknife.BindView;
import butterknife.OnClick;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import noommate.android.activity.Intro.AddInfoActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import noommate.android.R;
import noommate.android.activity.Intro.FindPwActivity;
import noommate.android.activity.NoommateActivity;
import noommate.android.activity.main.MainActivity;
import noommate.android.activity.signup.SignupActivity;
import noommate.android.commons.Constants;
import noommate.android.commons.Tools;
import noommate.android.models.MemberModel;
import noommate.android.models.api.CommonRouter;
import timber.log.Timber;

public class SigninActivity extends NoommateActivity {
  //--------------------------------------------------------------------------------------------
  // MARK : GET START INTENT
  //--------------------------------------------------------------------------------------------
  public static Intent getStartIntent(Context context) {
    Intent intent = new Intent(context, SigninActivity.class);
    return intent;
  }

  //--------------------------------------------------------------------------------------------
  // MARK : Bind Area
  //--------------------------------------------------------------------------------------------
  @BindView(R.id.id_edit_text)
  AppCompatEditText mIdEditText;
  @BindView(R.id.pw_edit_text)
  AppCompatEditText mPwEditText;

  //--------------------------------------------------------------------------------------------
  // MARK : Local variables
  //--------------------------------------------------------------------------------------------

  //--------------------------------------------------------------------------------------------
  // MARK : Override
  //--------------------------------------------------------------------------------------------
  @Override
  protected int inflateLayout() {
    return R.layout.activity_signin;
  }

  @Override
  protected void initLayout() {
    initToolbar("로그인");
  }

  @Override
  protected void initRequest() {

  }

  //--------------------------------------------------------------------------------------------
  // MARK : Local functions
  //--------------------------------------------------------------------------------------------

  /**
   * 회원 로그인 API
   */
  private void memberLoginAPI() {
    MemberModel memberRequest = new MemberModel();
    memberRequest.setMember_id(mIdEditText.getText().toString());
    memberRequest.setMember_pw(mPwEditText.getText().toString());
    memberRequest.setDevice_os("A");
    memberRequest.setGcm_key(Prefs.getString(Constants.FCM_TOKEN,""));
    CommonRouter.api().member_login(Tools.getInstance(mActivity).getMapper(memberRequest)).enqueue(new Callback<MemberModel>() {
      @Override
      public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
          MemberModel memberResponse = response.body();
          if (memberResponse.getCode().equals("1000")) {
            Prefs.putString(Constants.MEMBER_ID, mIdEditText.getText().toString());
            Prefs.putString(Constants.MEMBER_PW, mPwEditText.getText().toString());
            Prefs.putString(Constants.MEMBER_IDX, memberResponse.getMember_idx());
            Prefs.putString(Constants.HOUSE_IDX, memberResponse.getHouse_idx());
            Prefs.putString(Constants.HOUSE_CODE, memberResponse.getHouse_code());
            Prefs.putString(Constants.MEMBER_JOIN_TYPE, "C");
            Intent mainActivity = MainActivity.getStartIntent(mActivity);
            startActivity(mainActivity,TRANS.ZOOM);
            removeAllActivity();
          } else {
            showAlertDialog(memberResponse.getCode_msg(), "확인", new DialogEventListener() {
              @Override
              public void onReceivedEvent() {

              }
            });
          }
      }

      @Override
      public void onFailure(Call<MemberModel> call, Throwable t) {
        showSnackBar("오류");
      }
    });
  }

  /**
   * 카카오로그인 API
   */
  private void kakaoLoginAPI() {
    if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(mActivity)) { // 카카오톡 설치 유무
      UserApiClient.getInstance().loginWithKakaoTalk(mActivity, (oAuthToken, throwable) -> {


        if (oAuthToken != null) {
          kakaoMeAPI();
        }
        return null;
      });
    } else {
      UserApiClient.getInstance().loginWithKakaoAccount(mActivity, (oAuthToken, throwable) -> {
        if (oAuthToken != null) {
          kakaoMeAPI();
        }
        return null;
      });
    }
  }

  /**
   * 카카오 사용자 정보 가져오기
   */
  private void kakaoMeAPI() {
    Function2<OAuthToken, Throwable, Unit> kakaoCallback = new Function2<OAuthToken, Throwable, Unit>() {
      @Override
      public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
        if (oAuthToken != null) {

        }
        if (throwable != null) {

        }
        return null;
      }
    };
    UserApiClient.getInstance().me((user, error) -> {
      if (error != null) {
        Timber.e("카카오 로그인 실패 : " + error);
      } else if (user != null) {
        snsMemberLoginAPI(String.valueOf(user.getId()), "K");
      }
      return null;
    });
  }

  /**
   * SNS 로그인 API
   */
  private void snsMemberLoginAPI(String id, String memberJoinType) {
    MemberModel memberRequest = new MemberModel();
    memberRequest.setMember_id(id);
    memberRequest.setMember_join_type(memberJoinType);
    memberRequest.setDevice_os("A");
    memberRequest.setGcm_key(Prefs.getString(Constants.FCM_TOKEN, ""));

    CommonRouter.api().sns_member_login(Tools.getInstance().getMapper(memberRequest)).enqueue(new Callback<MemberModel>() {
      @Override
      public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
        MemberModel memberResponse = response.body();

        if (memberResponse.getCode().equals("1000")) {

          Prefs.putString(Constants.MEMBER_ID, id);
          Prefs.putString(Constants.MEMBER_IDX, memberResponse.getMember_idx());
          Prefs.putString(Constants.MEMBER_NAME, memberResponse.getMember_nickname());
          Prefs.putString(Constants.MEMBER_JOIN_TYPE, memberJoinType);
          Intent mainActivity = MainActivity.getStartIntent(mActivity);
          startActivity(mainActivity,TRANS.ZOOM);
          removeAllActivity();
        } else {
          Intent addInfoActivity = AddInfoActivity.getStartIntent(mActivity, id);
          startActivity(addInfoActivity, TRANS.ZOOM);
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
   * 로그인 버튼
   */
  @OnClick(R.id.signin_button)
  public void signinTouched() {
    memberLoginAPI();
  }

  /**
   * 비밀번호 찾기
   */
  @OnClick(R.id.find_pw_button)
  public void findPwTouched() {
    Intent findPwActivity = FindPwActivity.getStartIntent(mActivity);
    startActivity(findPwActivity,TRANS.PUSH);
  }
  /**
   * 카카오 록그인
   */
  @OnClick(R.id.kakao_button)
  public void kakaoTouched() {
    kakaoLoginAPI();
  }

  /**
   * 회원가입
   */
  @OnClick(R.id.signup_button)
  public void signupTouched() {
    Intent signupActivity = SignupActivity.getStartIntent(mActivity);
    startActivity(signupActivity,TRANS.PUSH);
  }
}
