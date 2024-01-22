package noommate.android.activity.main.calculate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.florent37.shapeofview.shapes.RoundRectView;
import noommate.android.activity.NoommateActivity;
import noommate.android.activity.NoommateFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import noommate.android.R;
import noommate.android.commons.Constants;
import noommate.android.commons.EmptyView;
import noommate.android.commons.Tools;
import noommate.android.dialog.DivDialog;
import noommate.android.models.BookModel;
import noommate.android.models.api.CommonRouter;

public class CalculateFragment extends NoommateFragment {
    //--------------------------------------------------------------------------------------------
    // MARK : GET START INTENT
    //--------------------------------------------------------------------------------------------
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, CalculateFragment.class);
        return intent;
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Area
    //--------------------------------------------------------------------------------------------
    @BindView(R.id.date_text_view)
    AppCompatTextView mDateTextView;
    @BindView(R.id.total_text_view)
    AppCompatTextView mTotalTextView;
    @BindView(R.id.calculate_recycler_view)
    RecyclerView mCalculateRecyclerView;
    @BindView(R.id.all_calculate_recycler_view)
    RecyclerView mAllCalculateRecyclerView;
    @BindView(R.id.add_round)
    RoundRectView mAddRound;
    @BindView(R.id.add_button)
    AppCompatTextView mAddButton;
    @BindView(R.id.sum_layout)
    RelativeLayout mSumLayout;

    //--------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------
    private Date mNowDate = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월");
    private CalculateAdapter mCalculateAdapter;
    private ArrayList<BookModel> mCalculateList = new ArrayList<>();

    private AllCalculateAdapter mAllCalculateAdapter;
    private ArrayList<MultiItemEntity> mAllCalculateList = new ArrayList<>();
    private BookModel mBookResponse = new BookModel();
    private BookModel mBookListResponse = new BookModel();

    private BroadcastReceiver mCalculateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mCalculateList.clear();
            mAllCalculateList.clear();
            bookListAPI();
            bookViewAPI();

        }
    };

    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    @Override
    protected int inflateLayout() {
        return R.layout.fragment_calculate;
    }

    @Override
    protected void initLayout() {
        mActivity.registerReceiver(mCalculateReceiver, new IntentFilter(Constants.CALCULATE_REFRESH));
        mDateTextView.setText(sdf.format(mNowDate));


    }

    @Override
    protected void initRequest() {
        initCalculateAdapter();
        initAllCalculateAdapter();

    }

    //--------------------------------------------------------------------------------------------
    // MARK : Local functions
    //--------------------------------------------------------------------------------------------

    /**
     * 가계부 리스트
     */
    private void initCalculateAdapter() {
        mCalculateAdapter = new CalculateAdapter(R.layout.row_calculate, mCalculateList);
        mCalculateAdapter.setEmptyView(new EmptyView(mActivity,"이번 달 가계부 내역이 없습니다.\n지금 바로 작성해보세요!"));
        mCalculateAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.div_button) {
                    DivDialog divDialog = new DivDialog(mActivity ,mCalculateList.get(position), new DivDialog.DivListener() {
                        @Override
                        public void onRefresh() {

                        }
                    });
                    divDialog.show(getChildFragmentManager(), "");
                }
            }
        });
        mCalculateRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mCalculateRecyclerView.setAdapter(mCalculateAdapter);
        bookViewAPI();
    }

    /**
     * 전체 가계부
     */
    private void initAllCalculateAdapter() {

        mAllCalculateAdapter = new AllCalculateAdapter(mAllCalculateList);
        mAllCalculateAdapter.setEmptyView(new EmptyView(mActivity,"작성 된 가계부\n없습니다"));
        mAllCalculateRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mAllCalculateRecyclerView.setAdapter(mAllCalculateAdapter);
        bookListAPI();
    }

    /**
     * 가계부 리스트 API
     */
    private void bookViewAPI() {
        BookModel bookRequest = new BookModel();
        bookRequest.setMember_idx(Prefs.getString(Constants.MEMBER_IDX, ""));
        CommonRouter.api().book_view(Tools.getInstance().getMapper(bookRequest)).enqueue(new Callback<BookModel>() {
            @Override
            public void onResponse(Call<BookModel> call, Response<BookModel> response) {
                mBookResponse = response.body();
                if (mBookResponse.getCode().equals("1000")) {
                    mCalculateList.add(new BookModel());
                    mCalculateList.add(new BookModel());
                    mCalculateList.add(new BookModel());

                    mCalculateList.get(0).setItem_name("가스");
                    mCalculateList.get(0).setItem_bill(mBookResponse.getBook_item_1());
                    mCalculateList.get(1).setItem_name("수도세");
                    mCalculateList.get(1).setItem_bill(mBookResponse.getBook_item_2());
                    mCalculateList.get(2).setItem_name("전기세");
                    mCalculateList.get(2).setItem_bill(mBookResponse.getBook_item_3());
                    mCalculateList.addAll(mBookResponse.getDetail_list());
                    mCalculateAdapter.setNewData(mCalculateList);

                    if (mBookResponse.getBook_idx().equals("")) {
                        mAddButton.setText("작성");
                    } else {
                        mAddButton.setText("수정");
                    }

                    int sum = 0;
                    for (BookModel value : mCalculateList) {
                        if (value.getItem_bill() != null) {
                            int i = Integer.valueOf(value.getItem_bill());
                            sum += i;
                        }
                    }

                    mTotalTextView.setText(Tools.getInstance().numberPlaceValue(String.valueOf(sum)) + " 원");


                } else {
                    mCalculateList.clear();
                    mSumLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<BookModel> call, Throwable t) {

            }
        });

    }

    /**
     * 전체 가계부 API
     */
    private void bookListAPI() {
        BookModel bookRequest = new BookModel();
        bookRequest.setHouse_code(Prefs.getString(Constants.HOUSE_CODE, ""));
        bookRequest.setPage_num(mBookListResponse.getNextPage());
        CommonRouter.api().book_list(Tools.getInstance().getMapper(bookRequest)).enqueue(new Callback<BookModel>() {
            @Override
            public void onResponse(Call<BookModel> call, Response<BookModel> response) {
                if (Tools.getInstance(mActivity).isSuccessResponse(response)) {
                    mBookListResponse = response.body();
                    if (mBookListResponse.getData_array() != null) {
                        mAllCalculateList.clear();
                        for (BookModel value : mBookListResponse.getData_array()) {
                            CalculateListItem calculateListItem = new CalculateListItem(value);
                            CalculateDetailItem calculateDetailItem = new CalculateDetailItem(value.getItem_list(), value);
                            calculateListItem.addSubItem(calculateDetailItem);
                            mAllCalculateList.add(calculateListItem);
                        }
                        mAllCalculateAdapter.setNewData(mAllCalculateList);

                    }
                }
            }

            @Override
            public void onFailure(Call<BookModel> call, Throwable t) {

            }
        });
    }


    //--------------------------------------------------------------------------------------------
    // MARK : Bind Actions
    //--------------------------------------------------------------------------------------------

    /**
     * 작성 버튼
     */
    @OnClick(R.id.add_button)
    public void addTouched() {
        if (mBookResponse.getBook_idx() == null) {
            Intent addCalculateActivity = AddCalculateActivity.getStartIntent(mActivity, "", new AddCalculateActivity.OnCalculateListener() {
                @Override
                public void OnResult() {
                    mCalculateList.clear();
                    mAllCalculateList.clear();
                    bookViewAPI();

                }
            });
            startActivity(addCalculateActivity, NoommateActivity.TRANS.PUSH);
        } else {
            Intent addCalculateActivity = AddCalculateActivity.getStartIntent(mActivity, mBookResponse.getBook_idx(), new AddCalculateActivity.OnCalculateListener() {
                @Override
                public void OnResult() {
                    mCalculateList.clear();
                    mAllCalculateList.clear();
                    bookViewAPI();

                }
            });
            startActivity(addCalculateActivity, NoommateActivity.TRANS.PUSH);
        }
    }


}

