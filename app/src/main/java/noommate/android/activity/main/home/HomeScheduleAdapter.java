package noommate.android.activity.main.home;

import android.graphics.BlurMaskFilter;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.mmin18.widget.RealtimeBlurView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import noommate.android.R;
import noommate.android.models.MemberModel;

public class HomeScheduleAdapter extends BaseQuickAdapter<MemberModel, BaseViewHolder> {
    public HomeScheduleAdapter(int layoutResId, @Nullable List<MemberModel> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, MemberModel item) {
        AppCompatTextView mStateTextView = helper.getView(R.id.state_text_view);
        AppCompatTextView mDefaultTextView = helper.getView(R.id.default_text_view);
        AppCompatImageView mStempImageView = helper.getView(R.id.stemp_image_view);
        LinearLayout mBlurView = helper.getView(R.id.blur_view);
        LinearLayout mContentLayout = helper.getView(R.id.content_layout);
        helper.addOnClickListener(R.id.state_text_view);
        helper.setText(R.id.content_text_view, item.getPlan_name());
        helper.setText(R.id.date_text_view, item.getAlarm_hour() + "시");




        if (item.getSchedule_idx() != null) {
            mDefaultTextView.setVisibility(View.GONE);
            mContentLayout.setVisibility(View.VISIBLE);
            if (item.getSchedule_yn().equals("Y")) {
                mBlurView.setVisibility(View.VISIBLE);
                mStempImageView.setVisibility(View.VISIBLE);
                mStateTextView.setEnabled(false);
            } else {
                mBlurView.setVisibility(View.GONE);
                mStempImageView.setVisibility(View.GONE);
                mStateTextView.setEnabled(true);
            }
        } else {
            mDefaultTextView.setVisibility(View.VISIBLE);
            mContentLayout.setVisibility(View.GONE);
        }


    }
}
