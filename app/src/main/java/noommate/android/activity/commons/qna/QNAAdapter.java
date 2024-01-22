package noommate.android.activity.commons.qna;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import noommate.android.R;
import noommate.android.models.QnaModel;


public class QNAAdapter extends BaseQuickAdapter<QnaModel, BaseViewHolder> {
    public QNAAdapter(int layoutResId, @Nullable List<QnaModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QnaModel item) {
        AppCompatTextView mStateTextView = helper.getView(R.id.state_text_view);
        helper.setText(R.id.title_text_view, item.getQa_title());
        helper.setText(R.id.date_text_view, item.getIns_date());
        if (item.getQa_type().equals("0")) {
            helper.setText(R.id.category_text_view, "불편신고");
        } else if (item.getQa_type().equals("1")) {
            helper.setText(R.id.category_text_view, "제보");
        } else {
            helper.setText(R.id.category_text_view, "기타");

        }
        if (item.getReply_yn().equals("Y")) {
            mStateTextView.setText("답변완료");
            mStateTextView.setTextColor(mContext.getColor(R.color.colorAccent));
        } else if (item.getReply_yn().equals("N")) {
            mStateTextView.setText("미답변");
            mStateTextView.setTextColor(mContext.getColor(R.color.color_a3a7b6));
        }
        //        helper.setText(R.id.state_text_view)
    }
}
