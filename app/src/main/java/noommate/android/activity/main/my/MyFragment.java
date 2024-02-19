package noommate.android.activity.main.my;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.pixplicity.easyprefs.library.Prefs;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.florent37.shapeofview.shapes.RoundRectView;
import noommate.android.activity.NoommateActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import noommate.android.R;
import noommate.android.activity.NoommateFragment;
import noommate.android.activity.commons.notice.NoticeListActivity;
import noommate.android.activity.commons.qna.QNAActivity;
import noommate.android.activity.signin.SigninActivity;
import noommate.android.commons.Constants;
import noommate.android.commons.Tools;
import noommate.android.dialog.HouseIntoDialog;
import noommate.android.models.MemberModel;
import noommate.android.models.api.CommonRouter;

public class MyFragment extends NoommateFragment {
    //--------------------------------------------------------------------------------------------
    // MARK : GET START INTENT
    //--------------------------------------------------------------------------------------------
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MyFragment.class);
        return intent;
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Area
    //--------------------------------------------------------------------------------------------
    @BindView(R.id.house_name_text_view)
    AppCompatTextView mHouseNameTextView;
    @BindView(R.id.default_house_layout)
    LinearLayout mDefaultHouseLayout;
    @BindView(R.id.house_out_button)
    RelativeLayout mHouseOutButton;
    @BindView(R.id.name_text_view)
    AppCompatTextView mNameTextView;
    @BindView(R.id.invite_layout)
    LinearLayout mInviteLayout;
    @BindView(R.id.join_type)
    AppCompatTextView mJoinType;
    @BindView(R.id.back_image_view)
    AppCompatImageView mBackImageView;
    @BindView(R.id.face_image_view)
    AppCompatImageView mFaceImageView;
    @BindView(R.id.back_layout)
    RelativeLayout mBackLayout;
    @BindView(R.id.profile_layout)
    RoundRectView mProfileLayout;

    //--------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------
    private MemberModel mMemberResponse = new MemberModel();

    private BroadcastReceiver mMyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Prefs.getString(Constants.HOUSE_CODE, "").equals("")) {
                mDefaultHouseLayout.setVisibility(View.VISIBLE);
                mInviteLayout.setVisibility(View.GONE);
                mHouseOutButton.setVisibility(View.GONE);
                mHouseNameTextView.setVisibility(View.GONE);
            } else {
                mDefaultHouseLayout.setVisibility(View.GONE);
                mInviteLayout.setVisibility(View.VISIBLE);
                mHouseOutButton.setVisibility(View.VISIBLE);
                mHouseNameTextView.setVisibility(View.VISIBLE);

            }

        }
    };

    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    @Override
    protected int inflateLayout() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initLayout() {
        mActivity.registerReceiver(mMyReceiver, new IntentFilter(Constants.MY_REFRESH));
        mToolbarTitle.setText("마이페이지");
        mBackButton.setVisibility(View.GONE);
    }

    @Override
    protected void initRequest() {
        memberInfoDetailAPI();

    }

    //--------------------------------------------------------------------------------------------
    // MARK : Local functions
    //--------------------------------------------------------------------------------------------

    /**
     * 내 정보
     */
    private void memberInfoDetailAPI() {
        MemberModel memberRequest = new MemberModel();
        memberRequest.setMember_idx(Prefs.getString(Constants.MEMBER_IDX, ""));
        CommonRouter.api().member_info_detail(Tools.getInstance().getMapper(memberRequest)).enqueue(new Callback<MemberModel>() {
            @Override
            public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
                mMemberResponse = response.body();
                if (Tools.getInstance().isSuccessResponse(response)) {
                    Prefs.putString(Constants.HOUSE_CODE, mMemberResponse.getHouse_code());
                    mNameTextView.setText(mMemberResponse.getMember_name());
                    if (mMemberResponse.getMember_role1().equals("")) {
                        mProfileLayout.setVisibility(View.GONE);

                    } else {
                        mProfileLayout.setVisibility(View.VISIBLE);

                        // 얼굴
                        if (mMemberResponse.getMember_role1().equals("0")) {
                            mBackImageView.setImageDrawable(mActivity.getDrawable(R.drawable.back5));
                        } else if (mMemberResponse.getMember_role1().equals("1")) {
                            mBackImageView.setImageDrawable(mActivity.getDrawable(R.drawable.back4));
                        } else if (mMemberResponse.getMember_role1().equals("2")) {
                            mBackImageView.setImageDrawable(mActivity.getDrawable(R.drawable.back3));
                        } else if (mMemberResponse.getMember_role1().equals("3")) {
                            mBackImageView.setImageDrawable(mActivity.getDrawable(R.drawable.back2));
                        } else if (mMemberResponse.getMember_role1().equals("4")) {
                            mBackImageView.setImageDrawable(mActivity.getDrawable(R.drawable.back1));
                        }
                        // 표정
                        if (mMemberResponse.getMember_role2().equals("0")) {
                            mFaceImageView.setImageDrawable(mActivity.getDrawable(R.drawable.group_191));
                        } else if (mMemberResponse.getMember_role2().equals("1")) {
                            mFaceImageView.setImageDrawable(mActivity.getDrawable(R.drawable.group_197));
                        } else if (mMemberResponse.getMember_role2().equals("2")) {
                            mFaceImageView.setImageDrawable(mActivity.getDrawable(R.drawable.group_193));
                        } else if (mMemberResponse.getMember_role2().equals("3")) {
                            mFaceImageView.setImageDrawable(mActivity.getDrawable(R.drawable.group_194));
                        } else if (mMemberResponse.getMember_role2().equals("4")) {
                            mFaceImageView.setImageDrawable(mActivity.getDrawable(R.drawable.group_195));
                        } else if (mMemberResponse.getMember_role2().equals("5")) {
                            mFaceImageView.setImageDrawable(mActivity.getDrawable(R.drawable.group_196));
                        }
                        // 색상
                        if (mMemberResponse.getMember_role3().equals("0")) {
                            mBackLayout.setBackgroundColor(mActivity.getColor(R.color.color_ff6d6d));
                        } else if (mMemberResponse.getMember_role3().equals("1")) {
                            mBackLayout.setBackgroundColor(mActivity.getColor(R.color.color_ffcd4b));
                        } else if (mMemberResponse.getMember_role3().equals("2")) {
                            mBackLayout.setBackgroundColor(mActivity.getColor(R.color.color_63d08f));
                        } else if (mMemberResponse.getMember_role3().equals("3")) {
                            mBackLayout.setBackgroundColor(mActivity.getColor(R.color.color_87b7ff));
                        } else if (mMemberResponse.getMember_role3().equals("4")) {
                            mBackLayout.setBackgroundColor(mActivity.getColor(R.color.color_798ed6));
                        } else if (mMemberResponse.getMember_role3().equals("5")) {
                            mBackLayout.setBackgroundColor(mActivity.getColor(R.color.color_bfa0ff));
                        }
                    }
                    Prefs.putString(Constants.HOUSE_CODE, mMemberResponse.getHouse_code());
                    if (mMemberResponse.getHouse_code() != null) {
                        mHouseOutButton.setVisibility(View.VISIBLE);
                        mDefaultHouseLayout.setVisibility(View.GONE);
                        mHouseNameTextView.setVisibility(View.VISIBLE);
                        mInviteLayout.setVisibility(View.VISIBLE);
                        mHouseNameTextView.setText(mMemberResponse.getHouse_name());
                    } else {
                        mHouseOutButton.setVisibility(View.GONE);
                        mHouseNameTextView.setVisibility(View.GONE);
                        mInviteLayout.setVisibility(View.GONE);
                        mDefaultHouseLayout.setVisibility(View.VISIBLE);
                    }

                    if (mMemberResponse.getMember_join_type().equals("C")) {
                        mJoinType.setText(mMemberResponse.getMember_id());
                        mJoinType.setBackgroundColor(mActivity.getColor(R.color.color_c8ccd5));
                    }
                }
            }

            @Override
            public void onFailure(Call<MemberModel> call, Throwable t) {

            }
        });
    }

    /**
     * 회원 로그아웃
     */
    private void memberLogoutAPI() {
        MemberModel memberModel = new MemberModel();
        memberModel.setMember_idx(Prefs.getString(Constants.MEMBER_IDX, ""));
        CommonRouter.api().member_logout(Tools.getInstance().getMapper(memberModel)).enqueue(new Callback<MemberModel>() {
            @Override
            public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
                if (Tools.getInstance().isSuccessResponse(response)) {
                    Prefs.remove(Constants.HOUSE_CODE);
                    Prefs.remove(Constants.MEMBER_IDX);
                    Prefs.remove(Constants.MEMBER_PW);
                    Prefs.remove(Constants.HOUSE_IDX);
                    mActivity.removeAllActivity();
                    Intent signInActivity = SigninActivity.getStartIntent(mActivity);
                    startActivity(signInActivity, NoommateActivity.TRANS.ZOOM);
                }
            }

            @Override
            public void onFailure(Call<MemberModel> call, Throwable t) {
            }
        });
    }

    /**
     * 하우스 나가기
     */
    private void houseOutUpAPI() {
        MemberModel memberRequest = new MemberModel();
        memberRequest.setMember_idx(Prefs.getString(Constants.MEMBER_IDX, ""));
        CommonRouter.api().house_out_up(Tools.getInstance().getMapper(memberRequest)).enqueue(new Callback<MemberModel>() {
            @Override
            public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
                if (Tools.getInstance(mActivity).isSuccessResponse(response)) {
                    Prefs.remove(Constants.HOUSE_IDX);
                    Prefs.remove(Constants.HOUSE_CODE);
                    if (Prefs.getString(Constants.HOUSE_CODE, "").equals("")) {
                        mDefaultHouseLayout.setVisibility(View.VISIBLE);
                        mInviteLayout.setVisibility(View.GONE);
                        mHouseNameTextView.setVisibility(View.GONE);
                        mHouseOutButton.setVisibility(View.GONE);
                    } else {
                        mDefaultHouseLayout.setVisibility(View.GONE);
                        mInviteLayout.setVisibility(View.VISIBLE);
                        mHouseOutButton.setVisibility(View.VISIBLE);
                        mHouseNameTextView.setVisibility(View.VISIBLE);

                    }
                    Intent feedRefresh = new Intent(Constants.HOME_REFRESH);
                    mActivity.sendBroadcast(feedRefresh);

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
     * 내 정보 수정
     */
    @OnClick(R.id.edit_info_button)
    public void editInfoTouched() {
        Intent editInfoActivity = EditInfoActivity.getStartIntent(mActivity, new EditInfoActivity.OnEditListener() {
            @Override
            public void onRefresh() {
                memberInfoDetailAPI();
            }
        });
        startActivity(editInfoActivity, NoommateActivity.TRANS.PUSH);
    }

    /**
     * 눔메이트 초대하기
     */
    @OnClick(R.id.invite_layout)
    public void inviteLayout() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        // String으로 받아서 넣기
        String sendMessage = "'" + mMemberResponse.getHouse_name() + "'" + " 초대장이 도착했어요.\n\n" +"http://noom.api.hollysome.com/share/house_share?member_idx=2&house_code="+ mMemberResponse.getHouse_code();
        intent.putExtra(Intent.EXTRA_TEXT,sendMessage);

        Intent shareIntent = Intent.createChooser(intent, "share");

        startActivity(shareIntent);
    }


    /**
     * 하우스 코드 입력하기
     */
    @OnClick(R.id.house_code_button)
    public void houseCodeTouched() {
        HouseIntoDialog houseIntoDialog = new HouseIntoDialog(mActivity, new HouseIntoDialog.IntoListener() {
            @Override
            public void onRefresh() {
                if (Prefs.getString(Constants.HOUSE_CODE, "").equals("")) {
                    mDefaultHouseLayout.setVisibility(View.VISIBLE);
                    mInviteLayout.setVisibility(View.GONE);
                } else {
                    mDefaultHouseLayout.setVisibility(View.GONE);
                    mInviteLayout.setVisibility(View.VISIBLE);

                }

            }
        });
        houseIntoDialog.show(getChildFragmentManager(), "");
    }

    /**
     * 공지사항
     */
    @OnClick(R.id.notice_button)
    public void noticeTouched() {
        Intent noticeActivity = NoticeListActivity.getStartIntent(mActivity);
        startActivity(noticeActivity, NoommateActivity.TRANS.PUSH);
    }

    /**
     * 1:1 문의
     */
    @OnClick(R.id.qna_button)
    public void qnaTouched() {
        Intent qnaActivity = QNAActivity.getStartIntent(mActivity);
        startActivity(qnaActivity, NoommateActivity.TRANS.PUSH);
    }

    /**
     * 하우스 나가기
     */
    @OnClick(R.id.house_out_button)
    public void houseOutTouched() {
        showConfirmDialog("하우스의 일정, 가계부 등은\n삭제되어 더 이상 볼 수 없게 됩니다.\n" + mMemberResponse.getHouse_name() + "을(를) 나가시겠어요?", "취소", "하우스 나가기", new NoommateActivity.DialogEventListener() {
            @Override
            public void onReceivedEvent() {
                houseOutUpAPI();
                showAlertDialog(mMemberResponse.getHouse_name() + "\n하우스를 나왔습니다.", "확인", new NoommateActivity.DialogEventListener() {
                    @Override
                    public void onReceivedEvent() {
                    }
                });
            }
        });
    }

    /**
     * 로그아웃
     */
    @OnClick(R.id.signout_button)
    public void signoutTouched() {
        showConfirmDialog("로그아웃 하시겠어요?", "취소", "확인", new NoommateActivity.DialogEventListener() {
            @Override
            public void onReceivedEvent() {
                memberLogoutAPI();
            }
        });
    }

    /**
     * 알림 세팅
     */
    @OnClick(R.id.alarm_button)
    public void alarmTouched() {
        Intent alarmSettingActivity = AlarmSettingActivity.getStartIntent(mActivity);
        startActivity(alarmSettingActivity, NoommateActivity.TRANS.PUSH);
    }

    /**
     * 이용약관
     */
    @OnClick(R.id.terms_button)
    public void termsTouched() {
        Intent termsListActivity = TermsListActivity.getStartIntent(mActivity);
        startActivity(termsListActivity, NoommateActivity.TRANS.PUSH);
    }

    /**
     * 회원탈퇴
     */
    @OnClick(R.id.withdrawal_button)
    public void withdrawalTouched() {
        Intent withdrawalActivity = WithDrawalActivity.getStartIntent(mActivity);
        startActivity(withdrawalActivity, NoommateActivity.TRANS.PUSH);
    }


}


