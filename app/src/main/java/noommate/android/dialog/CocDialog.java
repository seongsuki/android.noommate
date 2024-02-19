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

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;
import java.util.Arrays;

import noommate.android.R;

public class CocDialog {
        private Context mContext;
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

        public interface OnCocListener {
            void onCoc(String cocCnt);
        }

        private static OnCocListener mOnCocListener;
        public CocDialog(Context context,String back,String face, String backColor,String title, String name, String cnt, OnCocListener onCocListener) {

            this.mContext = context;

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
            mButtonTextView.setText("콕찌르기 "+ mCnt);
            mContentTextView.setText("아직 " + mTitle + "을(를) 완료하지 않았어요.\n메이트를 콕 찔러 알려주세요.");

            mButtonTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int cnt = Integer.parseInt(mCnt);
                    cnt--;
                    mCnt = String.valueOf(cnt);
                    mButtonTextView.setText("콕찌르기 "+ cnt);
                }
            });

            mCloseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

        }

        public void show() {
            dialog.show();
        }

        public void dismiss() {
            dialog.dismiss();
        }
}
