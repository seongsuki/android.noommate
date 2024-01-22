package noommate.android.activity.main.home;

import android.graphics.drawable.Drawable;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.florent37.shapeofview.shapes.RoundRectView;
import noommate.android.R;
import noommate.android.models.MemberModel;

public class HomeNoteAdapter extends BaseQuickAdapter<MemberModel, BaseViewHolder> {
    public HomeNoteAdapter(int layoutResId, @Nullable List<MemberModel> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, MemberModel item) {
        RelativeLayout mBackLayout = helper.getView(R.id.back_layout);
        RoundRectView mProfileLayout = helper.getView(R.id.profile_layout);
        AppCompatImageView mBackImageView = helper.getView(R.id.back_image_view);
        AppCompatImageView mFaceImageView = helper.getView(R.id.face_image_view);

        ArrayList<Drawable> mBackImageList = new ArrayList<>();
        ArrayList<Drawable> mFaceImageList = new ArrayList<>();
        ArrayList<Integer> mBackColorList = new ArrayList<>();
        mBackImageList = new ArrayList<>(Arrays.asList(mContext.getDrawable(R.drawable.back5), mContext.getDrawable(R.drawable.back4), mContext.getDrawable(R.drawable.back3), mContext.getDrawable(R.drawable.back2), mContext.getDrawable(R.drawable.back1)));
        mFaceImageList = new ArrayList<>(Arrays.asList(mContext.getDrawable(R.drawable.group_191), mContext.getDrawable(R.drawable.group_197), mContext.getDrawable(R.drawable.group_193), mContext.getDrawable(R.drawable.group_194), mContext.getDrawable(R.drawable.group_195), mContext.getDrawable(R.drawable.group_196)));
        mBackColorList = new ArrayList<>(Arrays.asList(mContext.getColor(R.color.color_ff6d6d), mContext.getColor(R.color.color_ffcd4b), mContext.getColor(R.color.color_63d08f), mContext.getColor(R.color.color_87b7ff), mContext.getColor(R.color.color_798ed6), mContext.getColor(R.color.color_bfa0ff)));

        if (item.getMember_role1() != null) {
            mBackImageView.setImageDrawable(mBackImageList.get(Integer.parseInt(item.getMember_role1())));
        }

        if (item.getMember_role2() != null) {
            mFaceImageView.setImageDrawable(mFaceImageList.get(Integer.parseInt(item.getMember_role2())));
        }

        if (item.getMember_role3() != null) {
            mBackLayout.setBackgroundColor(mBackColorList.get(Integer.parseInt(item.getMember_role3())));
        }
        if (item.getMy_yn() != null) {
            if (item.getMy_yn().equals("Y")) {
                mProfileLayout.setBorderWidth(3);
                mProfileLayout.setBorderColor(mContext.getColor(R.color.colorAccent));
            } else {
                mProfileLayout.setBorderWidth(0);
            }
        }
        helper.setText(R.id.content_text_view, item.getContents());
        helper.setText(R.id.date_text_view, item.getIns_date());
    }
}
