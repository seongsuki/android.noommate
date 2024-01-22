package noommate.android.activity.signup;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import io.github.florent37.shapeofview.shapes.RoundRectView;
import noommate.android.R;
import noommate.android.models.BaseModel;

public class FaceAdapter extends BaseQuickAdapter<BaseModel, BaseViewHolder> {
    public FaceAdapter(int layoutResId, @Nullable List<BaseModel> data) {
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
            helper.setImageDrawable(R.id.image_view,mContext.getDrawable(R.drawable.group_191));
        }  else if (helper.getLayoutPosition() == 1) {
            helper.setImageDrawable(R.id.image_view,mContext.getDrawable(R.drawable.group_197));
        }  else if (helper.getLayoutPosition() == 2) {
            helper.setImageDrawable(R.id.image_view,mContext.getDrawable(R.drawable.group_193));
        }else if (helper.getLayoutPosition() == 3) {
            helper.setImageDrawable(R.id.image_view,mContext.getDrawable(R.drawable.group_194));
        }else if (helper.getLayoutPosition() == 4) {
            helper.setImageDrawable(R.id.image_view,mContext.getDrawable(R.drawable.group_195));
        }else if (helper.getLayoutPosition() == 5) {
            helper.setImageDrawable(R.id.image_view,mContext.getDrawable(R.drawable.group_196));
        }

    }
}
