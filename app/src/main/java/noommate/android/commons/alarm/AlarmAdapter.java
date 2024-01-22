package noommate.android.commons.alarm;


import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import noommate.android.R;
import noommate.android.models.AlarmModel;

public class AlarmAdapter extends BaseQuickAdapter<AlarmModel, BaseViewHolder> {
  public AlarmAdapter(int layoutResId, @Nullable List<AlarmModel> data) {
    super(layoutResId, data);
  }

  @Override
  protected void convert(BaseViewHolder helper, AlarmModel item) {
    helper.setText(R.id.title_text_view, item.getMsg());
    helper.setText(R.id.date_text_view, item.getIns_date());

//    AppCompatImageView mNewImageView = helper.getView(R.id.new_image_view);
//    if (item.getRead_yn().equals("Y")) {
//      mNewImageView.setVisibility(View.GONE);
//    } else {
//      mNewImageView.setVisibility(View.VISIBLE);
//    }

    helper.addOnClickListener(R.id.del_button);
  }
}

