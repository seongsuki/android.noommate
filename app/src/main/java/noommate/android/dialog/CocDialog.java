package noommate.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.gson.Gson;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import noommate.android.R;
import noommate.android.activity.NoommateActivity;
import noommate.android.commons.Constants;
import noommate.android.commons.Tools;
import noommate.android.models.CockModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import timber.log.Timber;

public class CocDialog {
    private NoommateActivity mContext;
    private Dialog dialog;
    private static String mName;
    private static String mCnt;
    private static String mBack;
    private static String mFace;
    private static String mBackColor;
    private static String mTitle;
    private AppCompatTextView mContentTextView;
    private AppCompatButton mButtonTextView;
    private AppCompatTextView mTitleTextView;
    private AppCompatImageView mFaceImageView;
    private AppCompatImageView mBackImageView;
    private RelativeLayout mBackLayout;
    private AppCompatButton mCloseButton;
    private RewardedAd rewardedAd;


    public interface OnCocListener {
        void onCoc(String cocCnt);
    }

    private static OnCocListener mOnCocListener;

    public CocDialog(NoommateActivity context, String back, String face, String backColor, String title, String name, String cnt, String gcm, OnCocListener onCocListener) {

        this.mContext = context;

        loadAd();

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_coc);
        dialog.setCancelable(false);
        mName = name;
        mCnt = cnt;
        mTitle = title;
        mBack = back;
        mFace = face;
        mBackColor = backColor;
        mOnCocListener = onCocListener;

        mContentTextView = dialog.findViewById(R.id.content_text_view);
        mButtonTextView = dialog.findViewById(R.id.button_text_view);
        mTitleTextView = dialog.findViewById(R.id.title_text_view);
        mFaceImageView = dialog.findViewById(R.id.face_image_view);
        mBackImageView = dialog.findViewById(R.id.back_image_view);
        mBackLayout = dialog.findViewById(R.id.back_layout);
        mCloseButton = dialog.findViewById(R.id.close_button);
        ArrayList<Drawable> mBackImageList = new ArrayList<>();
        ArrayList<Drawable> mFaceImageList = new ArrayList<>();
        ArrayList<Integer> mBackColorList = new ArrayList<>();

        mBackImageList = new ArrayList<>(Arrays.asList(mContext.getDrawable(R.drawable.back5), mContext.getDrawable(R.drawable.back4), mContext.getDrawable(R.drawable.back3), mContext.getDrawable(R.drawable.back2), mContext.getDrawable(R.drawable.back1)));
        mFaceImageList = new ArrayList<>(Arrays.asList(mContext.getDrawable(R.drawable.group_191), mContext.getDrawable(R.drawable.group_197), mContext.getDrawable(R.drawable.group_193), mContext.getDrawable(R.drawable.group_194), mContext.getDrawable(R.drawable.group_195), mContext.getDrawable(R.drawable.group_196)));
        mBackColorList = new ArrayList<>(Arrays.asList(mContext.getColor(R.color.color_ff6d6d), mContext.getColor(R.color.color_ffcd4b), mContext.getColor(R.color.color_63d08f), mContext.getColor(R.color.color_87b7ff), mContext.getColor(R.color.color_798ed6), mContext.getColor(R.color.color_bfa0ff)));

        if (mBack != null) {
            mBackImageView.setImageDrawable(mBackImageList.get(Integer.parseInt(mBack)));
        }

        if (mFace != null) {
            mFaceImageView.setImageDrawable(mFaceImageList.get(Integer.parseInt(mFace)));
        }

        if (mBackColor != null) {
            mBackLayout.setBackgroundColor(mBackColorList.get(Integer.parseInt(mBackColor)));
        }

        mTitleTextView.setText(mName + "(이)에게");
        mButtonTextView.setText("콕찌르기 " + mCnt);
        mContentTextView.setText("아직 " + mTitle + "을(를) 완료하지 않았어요.\n메이트를 콕 찔러 알려주세요.");

        mButtonTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cnt = Integer.parseInt(mCnt);
                if (cnt > 0) {
                    sendNotificationSend(gcm, mCnt);
                    cnt--;
                    mCnt = String.valueOf(cnt);
                    mButtonTextView.setText("콕찌르기 " + cnt);
                } else {
                 showRewardedAd(gcm, mCnt);
                }

            }
        });

        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    /**
     * 알림 보내기
     */
    private void sendNotificationSend(String gcm, String cocCnt) {
        OkHttpClient client = new OkHttpClient();
        JSONObject json = new JSONObject();
        JSONObject notification = new JSONObject();
        try {
            notification.put("title", Prefs.getString(Constants.MEMBER_NAME, "") + " 메이트가 나를 콕 찔렀어요.");
            StringBuilder cockString = new StringBuilder();
            for (int i = 0; i <= Integer.parseInt(cocCnt); i++) {
                cockString.append("👉콕!");
            }
            notification.put("body", cockString);
            json.put("notification", notification);
            json.put("to", gcm);

        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/fcm/send")
                .post(body)
                .addHeader("Authorization", "key=AAAALvQuJTo:APA91bGQLA4AtAp82hz3SX3PrvebGM-ZCDymIrqXqDUNRTveMtprpNGoamwhsQLE6EDJdT2pxc9Pq1DCRY6wX3DYtE54TpiBWQfLT4TEgUaPUwOwBxLPAN_ZpR1VAYaiVvEFlqp0kPqO")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Timber.i("성공");
                    String responseBody = response.body().string();
                    System.out.println(responseBody);
                } else {
                    Timber.i("콕 찌르기 실패");
                }
                // 응답을 처리하거나 오류를 확인할 수 있습니다.
            }
        });

    }

    /**
     * 광고 보여주기
     */
    public void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(mContext, "ca-app-pub-3940256099942544/5224354917", adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                // 광고 로드 실패
                Timber.i(loadAdError.toString());
            }

            @Override
            public void onAdLoaded(RewardedAd ad) {
                rewardedAd = ad;
            }
        });

    }

    private void showRewardedAd(String gcm, String cnt) {
        if (rewardedAd != null) {
            rewardedAd.show(mContext, rewardItem -> {
                // 사용자에게 보상 제공 및 관련 작업 수행
                Timber.i("광고 노출");
                sendNotificationSend(gcm,"1");
            });
        } else {
            // 리워드 광고가 아직 로드되지 않았음을 알림
            Timber.i("로드되지 않음");
        }
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
