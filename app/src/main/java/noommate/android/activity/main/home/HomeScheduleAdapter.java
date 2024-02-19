package noommate.android.activity.main.home;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

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
        LinearLayout mContentLayout = helper.getView(R.id.content_layout);
        helper.addOnClickListener(R.id.state_text_view);
        helper.setText(R.id.content_text_view, item.getPlan_name());
//        String date = item.getAlarm_hour();
//        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa ~");
//        String time1 = sdf.format(date);
//        helper.setText(R.id.date_text_view, time1);

        if (item.getSchedule_idx() != null) {
            mDefaultTextView.setVisibility(View.GONE);
            mContentLayout.setVisibility(View.VISIBLE);
            if (item.getSchedule_yn().equals("Y")) {
                mStateTextView.setTextColor(mContext.getColor(R.color.color_c8ccd5));
                mStateTextView.setBackgroundColor(mContext.getColor(R.color.color_e4e6eb));
                mStateTextView.setText("완료");
                mStateTextView.setEnabled(false);
            } else {
                mStateTextView.setBackgroundColor(mContext.getColor(R.color.color_ffffff));
                mStateTextView.setTextColor(mContext.getColor(R.color.colorAccent));
                mStateTextView.setText("미완료");
                mStateTextView.setEnabled(true);
            }
        } else {
            mDefaultTextView.setVisibility(View.VISIBLE);
            mContentLayout.setVisibility(View.GONE);
        }


    }
}
