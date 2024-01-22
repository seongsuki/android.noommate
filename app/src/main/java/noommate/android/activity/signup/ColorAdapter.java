package noommate.android.activity.signup;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import io.github.florent37.shapeofview.shapes.RoundRectView;
import noommate.android.R;
import noommate.android.models.BaseModel;

public class ColorAdapter extends BaseQuickAdapter<BaseModel, BaseViewHolder> {
    public ColorAdapter(int layoutResId, @Nullable List<BaseModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseModel item) {
        RoundRectView mLayout = helper.getView(R.id.layout);
        if (item.isSelected() == true) {
            mLayout.setBorderColor(mContext.getColor(R.color.colorAccent));
            mLayout.setBorderWidth(5);
        } else {
            mLayout.setBorderWidth(0);
        }

        if (helper.getLayoutPosition() == 0) {
            helper.setBackgroundColor(R.id.back_color_view,mContext.getColor(R.color.color_ff6d6d));
        } else if (helper.getLayoutPosition() == 1) {
            helper.setBackgroundColor(R.id.back_color_view, mContext.getColor(R.color.color_ffcd4b));
        }else if (helper.getLayoutPosition() == 2) {
            helper.setBackgroundColor(R.id.back_color_view,mContext.getColor(R.color.color_63d08f));
        } else if (helper.getLayoutPosition() == 3) {
            helper.setBackgroundColor(R.id.back_color_view,mContext.getColor(R.color.color_87b7ff));
        }else if (helper.getLayoutPosition() == 4) {
            helper.setBackgroundColor(R.id.back_color_view,mContext.getColor(R.color.color_798ed6));
        }else if (helper.getLayoutPosition() == 5) {
            helper.setBackgroundColor(R.id.back_color_view,mContext.getColor(R.color.color_bfa0ff));
        }
    }
}
