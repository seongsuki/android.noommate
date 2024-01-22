package noommate.android.activity.main.schedule.schedulepage;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import noommate.android.models.BaseModel;

public class DateAdapter extends BaseQuickAdapter<BaseModel, BaseViewHolder> {
    public DateAdapter(int layoutResId, @Nullable List<BaseModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseModel item) {

    }
}
