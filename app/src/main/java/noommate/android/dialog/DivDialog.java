package noommate.android.dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import noommate.android.activity.NoommateActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import noommate.android.R;
import noommate.android.activity.main.home.MateAdapter;
import noommate.android.commons.Constants;
import noommate.android.commons.DecorationHorizontal;
import noommate.android.commons.Tools;
import noommate.android.models.BookModel;
import noommate.android.models.MemberModel;
import noommate.android.models.api.CommonRouter;
import timber.log.Timber;

public class DivDialog extends BottomSheetDialogFragment {
    public interface DivListener {
        void onRefresh();
    }

    public DivDialog(NoommateActivity activity, BookModel bookModel, DivListener divListener) {
        mActivity = activity;
        mDivListener = divListener;
        mBookModel = bookModel;

    }

    private RecyclerView mMemberRecyclerView;
    private MateAdapter mMateAdapter;
    private static BookModel mBookModel;
    private ArrayList<String> mMateList = new ArrayList<>();

    private ArrayList<MemberModel> mMemberList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

    }

    private static DivListener mDivListener;
    private NoommateActivity mActivity;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_div, container, false);

        mMemberRecyclerView = view.findViewById(R.id.member_recycler_view);
        AppCompatTextView mCancelButton = view.findViewById(R.id.cancel_button);
        AppCompatTextView mDivButton = view.findViewById(R.id.div_button);
        AppCompatTextView mCategoryTextView = view.findViewById(R.id.category_text_view);
        AppCompatTextView mPriceTextView = view.findViewById(R.id.price_text_view);
        if (mBookModel.getItem_name() != null) {
            mCategoryTextView.setText(mBookModel.getItem_name());
            mPriceTextView.setText(Tools.getInstance().numberPlaceValue(mBookModel.getItem_bill()) + "원");
        }



        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mDivButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i <  mMemberList.size(); i++) {
                    if (mMemberList.get(i).isSelected()) {
                        mMateList.add(String.valueOf(mMemberList.get(i).getMember_idx()));
                    }
                }


                String members = String.join(",", mMateList);
                bookAlarmAPI(members,mBookModel.getItem_bill(), mBookModel.getItem_name());
            }
        });

        /**
         * 메이트 리스트
         */
        mMateAdapter = new MateAdapter(R.layout.row_homemate, mMemberList);
        mMateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mMemberList.get(position).isSelected() == true) {
                    mMemberList.get(position).setSelected(false);
                } else {
                    mMemberList.get(position).setSelected(true);
                }
                mMateAdapter.setNewData(mMemberList);
            }
        });
        mMemberRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL,false));
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
        memberRequest.setMember_idx(Prefs.getString(Constants.MEMBER_IDX,""));
        memberRequest.setHouse_code(Prefs.getString(Constants.HOUSE_CODE,""));
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

    /**
     * 비용 알리기 API
     */
    private void bookAlarmAPI(String memberArr,String itemBill, String itemName) {
        BookModel bookRequest = new BookModel();
        bookRequest.setMember_arr(memberArr);
        bookRequest.setMate_cnt(String.valueOf(mMemberList.size()));
        bookRequest.setItem_bill(itemBill);
        bookRequest.setItem_name(itemName);
        CommonRouter.api().book_alarm(Tools.getInstance().getMapper(bookRequest)).enqueue(new Callback<BookModel>() {
            @Override
            public void onResponse(Call<BookModel> call, Response<BookModel> response) {
                dismiss();
                mDivListener.onRefresh();
            }

            @Override
            public void onFailure(Call<BookModel> call, Throwable t) {

            }
        });
    }




}
