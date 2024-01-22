package noommate.android.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pixplicity.easyprefs.library.Prefs;

import io.github.florent37.shapeofview.shapes.RoundRectView;
import noommate.android.activity.NoommateActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import noommate.android.R;
import noommate.android.commons.Constants;
import noommate.android.commons.Tools;
import noommate.android.models.MemberModel;
import noommate.android.models.api.CommonRouter;

public class HouseIntoDialog extends BottomSheetDialogFragment {
    public interface IntoListener {
        void onRefresh();
    }

    public HouseIntoDialog(NoommateActivity activity, IntoListener intoListener) {
        mActivity = activity;
        mIntoListener = intoListener;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

    }

    private static IntoListener mIntoListener;
    private NoommateActivity mActivity;

    private RoundRectView mHouseCodeLayout;
    private AppCompatEditText mHouseCodeEditText;
    private AppCompatTextView mDefaultTextView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_house_into, container, false);

        AppCompatTextView mCancelButton = view.findViewById(R.id.cancel_button);
        AppCompatTextView mCompleteButton = view.findViewById(R.id.complete_button);
        mHouseCodeEditText = view.findViewById(R.id.house_code_edit_text);
        mDefaultTextView = view.findViewById(R.id.default_text_view);
        mHouseCodeLayout = view.findViewById(R.id.house_layout);

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                houseJoinInAPI();

            }
        });





        return view;
    }

    /**
     * 초대받은 하우스 들어가기 API
     */
    private void houseJoinInAPI() {
        MemberModel memberRequest = new MemberModel();
        memberRequest.setMember_idx(Prefs.getString(Constants.MEMBER_IDX,""));
        memberRequest.setHouse_code(mHouseCodeEditText.getText().toString());
        CommonRouter.api().house_join_in(Tools.getInstance().getMapper(memberRequest)).enqueue(new Callback<MemberModel>() {
            @Override
            public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
                MemberModel mMemberResponse = response.body();
                if (mMemberResponse.getCode().equals("1000")) {
                    Prefs.putString(Constants.HOUSE_CODE, mMemberResponse.getHouse_code());
                    Prefs.putString(Constants.HOUSE_IDX, mMemberResponse.getHouse_idx());
                    mIntoListener.onRefresh();
                    dismiss();
                } else {
                    mDefaultTextView.setVisibility(View.VISIBLE);
                    mHouseCodeEditText.setHintTextColor(mActivity.getColor(R.color.color_ff2525));
                    mHouseCodeLayout.setBorderWidth(3);
                    mHouseCodeLayout.setBorderColor(mActivity.getColor(R.color.color_ff2525));
                }
            }

            @Override
            public void onFailure(Call<MemberModel> call, Throwable t) {

            }
        });
    }




}

