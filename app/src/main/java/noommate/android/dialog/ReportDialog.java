package noommate.android.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pixplicity.easyprefs.library.Prefs;
import com.skydoves.powerspinner.OnSpinnerOutsideTouchListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import noommate.android.R;
import noommate.android.activity.RocateerActivity;
import noommate.android.commons.Constants;
import noommate.android.commons.Tools;
import noommate.android.models.NoteModel;
import noommate.android.models.api.CommonRouter;

public class ReportDialog extends BottomSheetDialogFragment {
    public interface ReportListener {
        void onRefresh();
    }

    public ReportDialog(RocateerActivity activity,String noteIdx, ReportListener reportListener) {
        mActivity = activity;
        mNoteIdx = noteIdx;
        mReportListener = reportListener;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

    }

    private static ReportListener mReportListener;
    private static String mNoteIdx;
    private RocateerActivity mActivity;
    private PowerSpinnerView mReportSpinnerView;
    private AppCompatEditText mReportEditText;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_report, container, false);

        mReportSpinnerView = view.findViewById(R.id.reporting_spinner);
        mReportEditText = view.findViewById(R.id.report_edit_text);
        AppCompatTextView mReportButton = view.findViewById(R.id.reporting_button);
        AppCompatTextView mCancelButton = view.findViewById(R.id.cancel_button);

        mReportSpinnerView.setSpinnerOutsideTouchListener(new OnSpinnerOutsideTouchListener() {
            @Override
            public void onSpinnerOutsideTouch(@NonNull View view, @NonNull MotionEvent motionEvent) {
                mReportSpinnerView.dismiss();
            }
        });

        mReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportRegInAPI();

            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });



        return view;
    }

    /**
     * 알림장 신고
     */
    private void reportRegInAPI() {
        NoteModel noteReqeust = new NoteModel();
        noteReqeust.setNote_idx(mNoteIdx);
        noteReqeust.setMember_idx(Prefs.getString(Constants.MEMBER_IDX,""));
        noteReqeust.setReport_contents(mReportEditText.getText().toString());
        noteReqeust.setReport_type(String.valueOf(mReportSpinnerView.getSelectedIndex()));
        CommonRouter.api().report_reg_in(Tools.getInstance().getMapper(noteReqeust)).enqueue(new Callback<NoteModel>() {
            @Override
            public void onResponse(Call<NoteModel> call, Response<NoteModel> response) {
                if (Tools.getInstance(mActivity).isSuccessResponse(response)) {
                    mReportListener.onRefresh();
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<NoteModel> call, Throwable t) {

            }
        });
    }


}
