package noommate.android.activity.commons.faq;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

public class FAQDetailItem extends AbstractExpandableItem<FAQDetailItem> implements MultiItemEntity {
  String content;

  public FAQDetailItem(String content) {
    this.content = content;
  }

  @Override
  public int getLevel() {
    return 1;
  }

  @Override
  public int getItemType() {
    return FAQListAdapter.FAQ_DETAIL;
  }
}
