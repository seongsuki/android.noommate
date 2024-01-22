package noommate.android.activity.commons.faq;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import noommate.android.R;

public class FAQListAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

  //--------------------------------------------------------------------------------------------
  // MARK : Local variables
  //--------------------------------------------------------------------------------------------
  public static final int FAQ_LIST = 0;
  public static final int FAQ_DETAIL = 1;

  //--------------------------------------------------------------------------------------------
  // MARK : Override
  //--------------------------------------------------------------------------------------------
  public FAQListAdapter(List<MultiItemEntity> data) {
    super(data);
    addItemType(FAQ_LIST, R.layout.row_faq_list);
    addItemType(FAQ_DETAIL, R.layout.row_faq_detail);
  }

  @Override
  protected void convert(final BaseViewHolder helper, MultiItemEntity item) {
    switch (helper.getItemViewType()) {
      case FAQ_LIST:
        final FAQListItem faqListItem = (FAQListItem) item;
        helper.setText(R.id.faq_title_text, faqListItem.title);

        helper.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            int pos = helper.getAdapterPosition();
            if (faqListItem.isExpanded()) {
              collapse(pos, true);
            } else {
              expand(pos, true);
            }
          }
        });
        break;
      case FAQ_DETAIL:
        final FAQDetailItem faqDetailItem = (FAQDetailItem) item;
        helper.setText(R.id.faq_detail_text, faqDetailItem.content);
        break;
    }
  }
}
