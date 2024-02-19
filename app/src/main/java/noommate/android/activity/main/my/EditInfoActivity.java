package noommate.android.activity.main.my;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.pixplicity.easyprefs.library.Prefs;

import butterknife.BindView;
import butterknife.OnClick;
import noommate.android.activity.NoommateActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import noommate.android.R;
import noommate.android.activity.signup.AddCharActivity;
import noommate.android.commons.Constants;
import noommate.android.commons.Tools;
import noommate.android.models.MemberModel;
import noommate.android.models.api.CommonRouter;
import timber.log.Timber;

public class EditInfoActivity extends NoommateActivity {
    public interface OnEditListener {
        void onRefresh();
    }
    //--------------------------------------------------------------------------------------------
    // MARK : GET START INTENT
    //--------------------------------------------------------------------------------------------
    public static Intent getStartIntent(Context context, OnEditListener onEditListener) {
        Intent intent = new Intent(context, EditInfoActivity.class);
        mOnEditListener = onEditListener;
        return intent;
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Area
    //--------------------------------------------------------------------------------------------
    @BindView(R.id.nickname_edit_text)
    AppCompatEditText mNicknameEditText;
    @BindView(R.id.back_layout)
    RelativeLayout mBackLayout;
    @BindView(R.id.face_image_view)
    AppCompatImageView mFaceImageView;
    @BindView(R.id.back_image_view)
    AppCompatImageView mBackImageView;
    @BindView(R.id.add_text_view)
    AppCompatTextView mAddTextView;
    //--------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------
    private MemberModel memberRequest = new MemberModel();
    private String mFace;
    private String mBack;
    private String mColor;
    private MemberModel mMemberResponse;
    private static OnEditListener mOnEditListener;

    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    @Override
    protected int inflateLayout() {
        return R.layout.activity_edit_info;
    }

    @Override
    protected void initLayout() {
        mToolbarTitle.setText("내 정보 수정");


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
        memberRequest.setMember_idx(Prefs.getString(Constants.MEMBER_IDX,""));
        CommonRouter.api().member_info_detail(Tools.getInstance().getMapper(memberRequest)).enqueue(new Callback<MemberModel>() {
            @Override
            public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
                mMemberResponse = response.body();
                if (Tools.getInstance().isSuccessResponse(response)) {
                    mNicknameEditText.setText(mMemberResponse.getMember_name());
                    if (mMemberResponse.getMember_role1().equals("")) {
                        mBackImageView.setVisibility(View.GONE);
                        mFaceImageView.setVisibility(View.GONE);
                        mAddTextView.setVisibility(View.VISIBLE);

                    } else {
                        mBackImageView.setVisibility(View.VISIBLE);
                        mFaceImageView.setVisibility(View.VISIBLE);
                        mAddTextView.setVisibility(View.GONE);
                        mBack = mMemberResponse.getMember_role1();
                        mFace = mMemberResponse.getMember_role2();
                        mColor = mMemberResponse.getMember_role3();

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
                }
            }

            @Override
            public void onFailure(Call<MemberModel> call, Throwable t) {

            }
        });
    }

    /**
     * 내 정보 수정 API
     */
    private void memberInfoModUpAPI() {
        memberRequest.setMember_idx(Prefs.getString(Constants.MEMBER_IDX,""));
        memberRequest.setMember_role1(mBack);
        memberRequest.setMember_role2(mFace);
        memberRequest.setMember_role3(mColor);
        memberRequest.setMember_name(mNicknameEditText.getText().toString());
        CommonRouter.api().member_info_mod_up(Tools.getInstance().getMapper(memberRequest)).enqueue(new Callback<MemberModel>() {
            @Override
            public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
                MemberModel mMemberResponse = response.body();
                if (mMemberResponse.getCode().equals("1000")) {

                    finishWithRemove();
                    mOnEditListener.onRefresh();
                    Intent scheduleRefresh = new Intent(Constants.SCHEDULE_REFRESH1);
                    mActivity.sendBroadcast(scheduleRefresh);
                    Intent historyRefresh = new Intent(Constants.SCHEDULE_REFRESH2);
                    mActivity.sendBroadcast(historyRefresh);
                    Intent todoRefresh = new Intent(Constants.SCHEDULE_REFRESH3);
                    mActivity.sendBroadcast(todoRefresh);
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
     * 캐릭터 생성
     */
    @OnClick(R.id.back_layout)
    public void backLayoutTouched() {
        Intent addCharActivity = AddCharActivity.getStartIntent(mActivity, mMemberResponse.getMember_role1(), mMemberResponse.getMember_role2(), mMemberResponse.getMember_role3(), new AddCharActivity.OnAddCharListener() {
            @Override
            public void onRefresh(String back, String face, String color) {
                mBack = back;
                mFace = face;
                mColor = color;
                mAddTextView.setVisibility(View.GONE);
                mBackImageView.setVisibility(View.VISIBLE);
                mFaceImageView.setVisibility(View.VISIBLE);
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
     * 수정
     */
    @OnClick(R.id.edit_button)
    public void editTouched() {
        memberInfoModUpAPI();
    }


    /**
     * 비밀번호 변경
     */
    @OnClick(R.id.pw_edit_button)
    public void pwEditTouched() {
        Intent pwChangeActivity = ChangePwActivity.getStartIntent(mActivity);
        startActivity(pwChangeActivity,TRANS.PUSH);
    }
}