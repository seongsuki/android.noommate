package noommate.android.activity.main.calculate;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import noommate.android.models.BookModel;


public class CalculateListItem extends AbstractExpandableItem<CalculateDetailItem> implements MultiItemEntity {
    public BookModel bookModel;


    public CalculateListItem(BookModel bookModel) {
        this.bookModel = bookModel;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return AllCalculateAdapter.CALCULATE_LIST;
    }
}
