package noommate.android.activity.commons.faq;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

public class FAQListItem extends AbstractExpandableItem<FAQDetailItem> implements MultiItemEntity {
  public String title;


  public FAQListItem(String title) {
    this.title = title;
  }

  @Override
  public int getLevel() {
    return 0;
  }

  @Override
  public int getItemType() {
    return FAQListAdapter.FAQ_LIST;
  }
}