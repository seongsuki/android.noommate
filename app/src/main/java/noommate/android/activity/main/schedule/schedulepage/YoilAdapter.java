package noommate.android.activity.main.schedule.schedulepage;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

import noommate.android.R;
import noommate.android.models.ScheduleModel;
import timber.log.Timber;

public class YoilAdapter extends BaseQuickAdapter<ScheduleModel, BaseViewHolder> {
    public YoilAdapter(int layoutResId, @Nullable List<ScheduleModel> data) {
        super(layoutResId, data);
    }
    private ArrayList<String> mYoilList = new ArrayList();
    private int position;
    private static String mWeekArr;

    @Override
    protected void convert(BaseViewHolder helper, ScheduleModel item) {

        mYoilList = new ArrayList<>(Arrays.asList("일","월","화", "수","목","금","토"));


        AppCompatTextView mYoilButton = helper.getView(R.id.yoil_button);
        mYoilButton.setText(mYoilList.get(helper.getLayoutPosition()));
        if (item.isSelected() == true) {
            mYoilButton.setBackgroundColor(mContext.getColor(R.color.colorAccent));
            mYoilButton.setTextColor(mContext.getColor(R.color.color_ffffff));
        } else {
            mYoilButton.setBackgroundColor(mContext.getColor(R.color.color_e4e6eb));
            mYoilButton.setTextColor(mContext.getColor(R.color.color_c8ccd5));
        }

//        if (item.getWeek_arr() != null) {
//            String[] yoil = item.getWeek_arr().split(",");
//            for (int i = 0; i < yoil.length; i++) {
//                item.setSelected(true);
//            }
//        }
    }
}
