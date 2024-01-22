package noommate.android.activity.main.calculate;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import noommate.android.models.BookModel;

public class BookAdapter extends BaseQuickAdapter<BookModel, BaseViewHolder> {
    public BookAdapter(int layoutResId, @Nullable List<BookModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BookModel item) {

    }
}
