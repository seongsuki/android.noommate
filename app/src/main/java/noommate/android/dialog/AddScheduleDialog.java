package noommate.android.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import noommate.android.activity.NoommateActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import noommate.android.R;
import noommate.android.activity.main.home.MateAdapter;
import noommate.android.activity.main.schedule.schedulepage.YoilAdapter;
import noommate.android.commons.Constants;
import noommate.android.commons.DecorationHorizontal;
import noommate.android.commons.Tools;
import noommate.android.models.MemberModel;
import noommate.android.models.ScheduleModel;
import noommate.android.models.api.CommonRouter;

public class AddScheduleDialog extends BottomSheetDialogFragment {
    public interface AddScheduleListener {
        void onRefresh(ArrayList<String> dayList, ArrayList<MemberModel> selectList);
    }

    public AddScheduleDialog(NoommateActivity activity, ArrayList<String> dayList, AddScheduleListener addScheduleListener) {
        mActivity = activity;
        mEnableList = dayList;
        mAddScheduleListener = addScheduleListener;

    }

    private RecyclerView mMemberRecyclerView;
    private RecyclerView mYoilRecyclerView;
    private YoilAdapter mYoilAdapter;
    private ArrayList<ScheduleModel> mYoilList = new ArrayList<>();
    private MateAdapter mMateAdapter;
    private ArrayList<MemberModel> mMemberList = new ArrayList<>();
    private ArrayList<MemberModel> mSelectMemberList = new ArrayList<>();
    private ArrayList<String> mDayList = new ArrayList<>();
    private static ArrayList<String> mEnableList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

    }

    private static AddScheduleListener mAddScheduleListener;
    private NoommateActivity mActivity;
    private int mIndex;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_add_schedule, container, false);

        mYoilRecyclerView = view.findViewById(R.id.yoil_recycler_view);
        mMemberRecyclerView = view.findViewById(R.id.member_recycler_view);
        AppCompatTextView mAddButton = view.findViewById(R.id.add_button);


        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < mYoilList.size(); i++) {
                    if (mYoilList.get(i).isSelected()) {
                        mDayList.add(String.valueOf(i + 1));
                    }
                }

                for (int i = 0; i < mMemberList.size(); i++) {
                    if (mMemberList.get(i).isSelected()) {
                        mSelectMemberList.add(mMemberList.get(i));
                    }
                }
                if (mSelectMemberList.size() > 0 && mDayList.size() > 0) {
                    mAddScheduleListener.onRefresh(mDayList, mSelectMemberList);
                    dismiss();
                } else {
                    mSelectMemberList.clear();
                    mDayList.clear();
                }

            }
        });

        /**
         * 요일 리스트
         */
        mYoilAdapter = new YoilAdapter(R.layout.row_yoil, mYoilList);
        mYoilAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mYoilList.get(position).isSelected() == true) {
                    mYoilList.get(position).setSelected(false);
                } else {
                    mYoilList.get(position).setSelected(true);
                }
                mYoilAdapter.setNewData(mYoilList);
            }
        });
        mYoilRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        mYoilRecyclerView.addItemDecoration(new DecorationHorizontal(mActivity, Tools.getInstance().dpTopx(mActivity, 8), Tools.getInstance().dpTopx(mActivity, 16)));
        mYoilRecyclerView.setAdapter(mYoilAdapter);

        for (int i = 0; i < 7; i++) {
            mYoilList.add(new ScheduleModel());
        }

        /**
         * 메이트 리스트
         */
        mMateAdapter = new MateAdapter(R.layout.row_homemate, mMemberList);
        mMateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mEnableList.contains(position + 1)) {

                } else {
                    if (mMemberList.get(position).isSelected() == true) {
                        mMemberList.get(position).setSelected(false);
                    } else {
                        mMemberList.get(position).setSelected(true);
                    }
                    mMateAdapter.setNewData(mMemberList);
                }

            }
        });
        mMemberRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        mMemberRecyclerView.addItemDecoration(new DecorationHorizontal(mActivity, Tools.getInstance().dpTopx(mActivity, 8), Tools.getInstance().dpTopx(mActivity, 16)));
        mMemberRecyclerView.setAdapter(mMateAdapter);

        mateListAPI();


        return view;
    }

    /**
     * 메이트 리스트
     */
    private void mateListAPI() {
        MemberModel memberRequest = new MemberModel();
        memberRequest.setMember_idx(Prefs.getString(Constants.MEMBER_IDX, ""));
        memberRequest.setHouse_code(Prefs.getString(Constants.HOUSE_CODE, ""));
        CommonRouter.api().mate_list(Tools.getInstance().getMapper(memberRequest)).enqueue(new Callback<MemberModel>() {
            @Override
            public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
                MemberModel mMemberResponse = response.body();
                if (Tools.getInstance(mActivity).isSuccessResponse(response)) {
                    mMemberList.addAll(mMemberResponse.getData_array());
                    mMateAdapter.setNewData(mMemberList);
                }
            }

            @Override
            public void onFailure(Call<MemberModel> call, Throwable t) {

            }
        });
    }


}

