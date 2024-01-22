package noommate.android.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import noommate.android.R;

public class MoreDialog extends BottomSheetDialogFragment {
    private Activity mActivity;
    private OnMoreListener mOnMoreListener;


    public interface OnMoreListener {
        void onReportEvent();

        void onBlockEvent();
    }

    public MoreDialog(Activity activity, OnMoreListener moreListener) {
        mActivity = activity;
        mOnMoreListener = moreListener;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_more, container, false);

        AppCompatTextView mHideInFeedTextView = view.findViewById(R.id.report_button);
        mHideInFeedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mOnMoreListener.onReportEvent();
            }
        });

        AppCompatTextView mDeclarationTextView = view.findViewById(R.id.block_button);
        mDeclarationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnMoreListener.onBlockEvent();
                dismiss();
            }
        });

        AppCompatTextView mCloseTextView = view.findViewById(R.id.cancel_button);
        mCloseTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }




}
