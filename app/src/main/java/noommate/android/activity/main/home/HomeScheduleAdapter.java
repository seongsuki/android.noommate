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

import java.text.ParseException;
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



        if (item.getPlan_name() != null) {
                helper.setText(R.id.content_text_view, item.getPlan_name());
            }
            if (item.getAlarm_hour() != null) {
                if (!item.getAlarm_hour().equals("")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                    SimpleDateFormat dateFormat=new SimpleDateFormat("HH");
                    String strDate= item.getAlarm_hour();
                    try {
                        Date date = dateFormat.parse(strDate);
                        helper.setText(R.id.date_text_view, sdf.format(date));

                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    helper.setText(R.id.date_text_view, "미정");
                }
            }





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
            mStempImageView.setVisibility(View.GONE);
            mBlurView.setVisibility(View.GONE);
            mDefaultTextView.setVisibility(View.VISIBLE);
            mContentLayout.setVisibility(View.GONE);
        }


    }
}
