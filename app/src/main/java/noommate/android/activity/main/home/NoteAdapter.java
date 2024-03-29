package noommate.android.activity.main.home;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
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
import noommate.android.models.NoteModel;

public class NoteAdapter extends BaseQuickAdapter<NoteModel, BaseViewHolder> {
    public NoteAdapter(int layoutResId, @Nullable List<NoteModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoteModel item) {
        helper.addOnClickListener(R.id.more_button);
        helper.addOnClickListener(R.id.delete_button);
        helper.addOnClickListener(R.id.edit_button);
        helper.addOnClickListener(R.id.unblock_button);


        RelativeLayout mBackLayout = helper.getView(R.id.back_layout);
        AppCompatImageView mBackImageView = helper.getView(R.id.back_image_view);
        AppCompatImageView mFaceImageView = helper.getView(R.id.face_image_view);
        LinearLayout mMyLayout = helper.getView(R.id.my_layout);
        RoundRectView mNoteLayout = helper.getView(R.id.note_layout);
        RoundRectView mBlockLayout = helper.getView(R.id.block_layout);
        RoundRectView mReportLayout = helper.getView(R.id.report_layout);
        AppCompatImageView mMoreImageView = helper.getView(R.id.more_button);

        helper.setText(R.id.block_date_text_view, item.getIns_date());
        helper.setText(R.id.report_date_text_view, item.getIns_date());

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
        if (item.getMember_nickname() != null) {
            helper.setText(R.id.name_text_view, item.getMember_nickname());
        }


        if (item.getMy_note_yn() != null) {
            if (item.getMy_note_yn().equals("Y")) {
                mMyLayout.setVisibility(View.VISIBLE);
                mMoreImageView.setVisibility(View.GONE);
            } else {
                mMyLayout.setVisibility(View.GONE);
                mMoreImageView.setVisibility(View.VISIBLE);
            }
        }

        if (item.getReport_yn().equals("Y")) {
            mReportLayout.setVisibility(View.VISIBLE);
            mBlockLayout.setVisibility(View.GONE);
            mNoteLayout.setVisibility(View.GONE);
        }

        if (item.getBlock_yn().equals("Y")) {
            mReportLayout.setVisibility(View.GONE);
            mBlockLayout.setVisibility(View.VISIBLE);
            mNoteLayout.setVisibility(View.GONE);
        }

        if (item.getBlock_yn().equals("N") && item.getReport_yn().equals("N")) {
            mReportLayout.setVisibility(View.GONE);
            mBlockLayout.setVisibility(View.GONE);
            mNoteLayout.setVisibility(View.VISIBLE);
        }

        helper.setText(R.id.content_text_view, item.getContents());
        helper.setText(R.id.date_text_view, item.getIns_date());




    }
}
