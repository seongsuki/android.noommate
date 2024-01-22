package noommate.android.activity.main.calculate;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import noommate.android.R;
import noommate.android.commons.Tools;
import noommate.android.models.BookModel;

public class CalculateAdapter extends BaseQuickAdapter<BookModel, BaseViewHolder> {
    public CalculateAdapter(int layoutResId, @Nullable List<BookModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BookModel item) {
        helper.addOnClickListener(R.id.div_button);
        helper.setText(R.id.category_text_view, item.getItem_name());
        helper.setText(R.id.price_text_view, Tools.getInstance().numberPlaceValue(item.getItem_bill()) + " 원");

    }
}
