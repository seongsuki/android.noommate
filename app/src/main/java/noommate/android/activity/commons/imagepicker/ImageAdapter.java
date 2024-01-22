package noommate.android.activity.commons.imagepicker;

import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import noommate.android.R;

public class ImageAdapter extends BaseQuickAdapter<Uri, BaseViewHolder> {
  public ImageAdapter(int layoutResId, @Nullable List<Uri> data) {
    super(layoutResId, data);
  }

  @Override
  protected void convert(BaseViewHolder helper, Uri item) {

    AppCompatImageView mImageView = helper.getView(R.id.image_view);

    RequestOptions requestOptions = new RequestOptions();
    requestOptions.centerCrop();

    Glide.with(mContext)
        .load(item)
        .apply(requestOptions)
        .into(mImageView);
  }
}
