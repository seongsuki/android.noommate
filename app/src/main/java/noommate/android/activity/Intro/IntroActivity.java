package noommate.android.activity.Intro;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import noommate.android.R;
import noommate.android.activity.NoommateActivity;
import noommate.android.activity.main.MainActivity;
import noommate.android.activity.signin.SigninActivity;
import noommate.android.commons.Constants;
import noommate.android.commons.Tools;
import noommate.android.models.AlarmModel;
import noommate.android.models.MemberModel;
import noommate.android.models.api.CommonRouter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class IntroActivity extends NoommateActivity {
    //--------------------------------------------------------------------------------------------
    // MARK : GET START INTENT
    //--------------------------------------------------------------------------------------------
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, IntroActivity.class);
        return intent;
    }

    public static Intent getStartIntent(Context context, AlarmModel alarmModel) {
        Intent intent = new Intent(context, IntroActivity.class);
        mAlarmModel = alarmModel;
        return intent;
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Area
    //--------------------------------------------------------------------------------------------

    //--------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------
    private static AlarmModel mAlarmModel;

    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    @Override
    protected int inflateLayout() {
        return R.layout.activity_intro;
    }

    @Override
    protected void initLayout() {
        permissionCheck();
    }

    @Override
    protected void initRequest() {

    }

    //--------------------------------------------------------------------------------------------
    // MARK : Local functions
    //--------------------------------------------------------------------------------------------

    /**
     * 메인 이동 딜레이
     */
    private void delayMain() {
        Handler delayHandler = new Handler();
        delayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toMain();
            }
        }, 2000);
    }

    /**
     * 권한체크
     */
    private void permissionCheck() {
        // 권한 체크
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                delayMain();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Tools.getInstance(mActivity).showToast("원할한 서비스 이용을 위하여, 권한을 확인해주세요.");
                delayMain();
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            TedPermission.with(mActivity)
                    .setPermissionListener(permissionlistener)
                    .setRationaleMessage(R.string.common_auth)
                    .setDeniedMessage(R.string.common_auth_denined)
                    .setPermissions(Manifest.permission.CAMERA,
                            Manifest.permission.POST_NOTIFICATIONS,
                            Manifest.permission.READ_MEDIA_IMAGES,
                            Manifest.permission.READ_MEDIA_AUDIO,
                            Manifest.permission.READ_MEDIA_VIDEO)
                    .check();
        } else {
            TedPermission.with(mActivity)
                    .setPermissionListener(permissionlistener)
                    .setRationaleMessage(R.string.common_auth)
                    .setDeniedMessage(R.string.common_auth_denined)
                    .setPermissions(Manifest.permission.CAMERA,
                            Manifest.permission.POST_NOTIFICATIONS,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .check();
        }
    }

    /**
     * 로그인 체크
     */
    private void toMain() {

        Timber.i("알림 보낼때 ::::::" + mAlarmModel);

        if (mAlarmModel == null) {
            if (Prefs.getString(Constants.MEMBER_IDX, "").equals("")) {
                removeAllActivity();
                Intent signInActivity = SigninActivity.getStartIntent(mActivity);
                startActivity(signInActivity, TRANS.ZOOM);
            } else {
                if (Prefs.getString(Constants.MEMBER_JOIN_TYPE,"").equals("C")) {
                    memberLoginAPI();
                } else if (Prefs.getString(Constants.MEMBER_JOIN_TYPE,"").equals("K")) {
                    snsMemberLoginAPI();

                }
            }
        } else {
            if (Prefs.getString(Constants.MEMBER_IDX, "").equals("")) {
                removeAllActivity();
                Intent signInActivity = SigninActivity.getStartIntent(mActivity);
                startActivity(signInActivity, TRANS.ZOOM);
            } else {
                if (Prefs.getString(Constants.MEMBER_JOIN_TYPE,"").equals("C")) {
                    memberLoginAPI();
                } else if (Prefs.getString(Constants.MEMBER_JOIN_TYPE,"").equals("K")) {
                    snsMemberLoginAPI();

                }
            }

        }

    }

    /**
     * 회원 로그인 API
     */
    private void memberLoginAPI() {
        MemberModel memberRequest = new MemberModel();
        memberRequest.setMember_id(Prefs.getString(Constants.MEMBER_ID, ""));
        memberRequest.setMember_pw(Prefs.getString(Constants.MEMBER_PW, ""));
        memberRequest.setDevice_os("A");
        memberRequest.setGcm_key(Prefs.getString(Constants.FCM_TOKEN, ""));
        CommonRouter.api().member_login(Tools.getInstance(mActivity).getMapper(memberRequest)).enqueue(new Callback<MemberModel>() {
            @Override
            public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
                MemberModel memberResponse = response.body();
                if (memberResponse.getCode().equals("1000")) {
                    Prefs.putString(Constants.MEMBER_ID, Prefs.getString(Constants.MEMBER_ID, ""));
                    Prefs.putString(Constants.MEMBER_PW, Prefs.getString(Constants.MEMBER_PW, ""));
                    Prefs.putString(Constants.MEMBER_IDX, memberResponse.getMember_idx());
                    Prefs.putString(Constants.HOUSE_IDX, memberResponse.getHouse_idx());
                    Prefs.putString(Constants.HOUSE_CODE, memberResponse.getHouse_code());
                    Prefs.putString(Constants.MEMBER_JOIN_TYPE, "C");
                    Intent mainActivity = MainActivity.getStartIntent(mActivity);
                    startActivity(mainActivity, TRANS.ZOOM);
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
     * SNS 로그인 API
     */
    private void snsMemberLoginAPI() {
        MemberModel memberRequest = new MemberModel();
        memberRequest.setMember_id(Prefs.getString(Constants.MEMBER_ID,""));
        memberRequest.setMember_join_type("K");
        memberRequest.setDevice_os("A");
        memberRequest.setGcm_key(Prefs.getString(Constants.FCM_TOKEN, ""));

        CommonRouter.api().sns_member_login(Tools.getInstance().getMapper(memberRequest)).enqueue(new Callback<MemberModel>() {
            @Override
            public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
                MemberModel memberResponse = response.body();

                if (memberResponse.getCode().equals("1000")) {

                    Prefs.putString(Constants.MEMBER_ID, memberResponse.getMember_id());
                    Prefs.putString(Constants.MEMBER_IDX, memberResponse.getMember_idx());
                    Prefs.putString(Constants.MEMBER_NAME, memberResponse.getMember_name());
                    Prefs.putString(Constants.HOUSE_IDX, memberResponse.getHouse_idx());
                    Prefs.putString(Constants.HOUSE_CODE, memberResponse.getHouse_code());
                    Prefs.putString(Constants.MEMBER_JOIN_TYPE, "K");
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

            }
        });

    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Actions
    //--------------------------------------------------------------------------------------------
}

