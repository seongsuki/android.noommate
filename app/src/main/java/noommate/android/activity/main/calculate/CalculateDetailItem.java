package noommate.android.activity.main.calculate;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;

import noommate.android.models.BookModel;

public class CalculateDetailItem extends AbstractExpandableItem<CalculateDetailItem> implements MultiItemEntity {
    public ArrayList<BookModel> item_list;
    public BookModel bookModel;

    public CalculateDetailItem(ArrayList<BookModel> ltem_list, BookModel bookModel) {
        this.item_list = ltem_list;
        this.bookModel = bookModel;

    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public int getItemType() {
        return AllCalculateAdapter.CALCULATE_DETAIL;
    }
}

