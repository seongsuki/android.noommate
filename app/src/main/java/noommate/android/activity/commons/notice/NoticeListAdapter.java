package noommate.android.activity.commons.notice;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import noommate.android.R;
import noommate.android.models.NoticeModel;


public class NoticeListAdapter extends BaseQuickAdapter<NoticeModel, BaseViewHolder> {
  public NoticeListAdapter(int layoutResId, @Nullable List<NoticeModel> data) {
    super(layoutResId, data);
  }

  @Override
  protected void convert(BaseViewHolder helper, NoticeModel item) {
    helper.setText(R.id.title_text_view, item.getTitle());
    helper.setText(R.id.date_text_view, item.getIns_date());
  }
}
