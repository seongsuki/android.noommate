package noommate.android.activity.main.calculate;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.florent37.shapeofview.shapes.RoundRectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import noommate.android.R;
import noommate.android.activity.NoommateActivity;
import noommate.android.commons.Constants;
import noommate.android.commons.Tools;
import noommate.android.models.BookModel;
import noommate.android.models.api.CommonRouter;
import timber.log.Timber;

public class AddCalculateActivity extends NoommateActivity {
    public interface OnCalculateListener {
        void OnResult();
    }

    //--------------------------------------------------------------------------------------------
    // MARK : GET START INTENT
    //--------------------------------------------------------------------------------------------
    public static Intent getStartIntent(Context context, String idx, OnCalculateListener onCalculateListener) {
        Intent intent = new Intent(context, AddCalculateActivity.class);
        mOnCalculateListener = onCalculateListener;
        mBookIdx = idx;
        return intent;
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Area
    //--------------------------------------------------------------------------------------------
    @BindView(R.id.gas_edit_text)
    AppCompatEditText mGasEditText;
    @BindView(R.id.water_edit_text)
    AppCompatEditText mWaterEditText;
    @BindView(R.id.ele_edit_text)
    AppCompatEditText mEleEditText;
    @BindView(R.id.name1_round)
    RoundRectView mName1Round;
    @BindView(R.id.name1_edit_text)
    AppCompatEditText mNameEditText;
    @BindView(R.id.price1_round)
    RoundRectView mPriceRound;
    @BindView(R.id.price1_edit_text)
    AppCompatEditText mPrice1EditText;
    @BindView(R.id.name2_round)
    RoundRectView mName2Round;
    @BindView(R.id.name2_edit_text)
    AppCompatEditText mName2EditText;
    @BindView(R.id.price2_round)
    RoundRectView mPrice2Round;
    @BindView(R.id.price2_edit_text)
    AppCompatEditText mPrice2EditText;
    @BindView(R.id.name3_round)
    RoundRectView mName3Round;
    @BindView(R.id.name3_edit_text)
    AppCompatEditText mName3EditText;
    @BindView(R.id.price3_round)
    RoundRectView mPrice3Round;
    @BindView(R.id.price3_edit_text)
    AppCompatEditText mPrice3EditText;
    @BindView(R.id.add_layout)
    RoundRectView mAddLayout;
    @BindView(R.id.add_button)
    AppCompatTextView mAddButton;

    //--------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------
    private static OnCalculateListener mOnCalculateListener;
    private static String mBookIdx;
    private ArrayList<AppCompatEditText> mNameList = new ArrayList<>();
    private ArrayList<AppCompatEditText> mPriceList = new ArrayList<>();
    private Date mNowDate = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월");

    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    @Override
    protected int inflateLayout() {
        return R.layout.activity_add_calculate;
    }

    @Override
    protected void initLayout() {
        mToolbarTitle.setText(sdf.format(mNowDate));
        mNameList = new ArrayList<>(Arrays.asList(mNameEditText, mName2EditText, mName3EditText));
        mPriceList = new ArrayList<>(Arrays.asList(mPrice1EditText, mPrice2EditText, mPrice3EditText));
        if (!mBookIdx.equals("")) {
            bookDetailAPI();
            mAddLayout.setBorderColor(mActivity.getColor(R.color.colorAccent));
            mAddButton.setText("수정");
            mAddLayout.setBorderWidth(3);
            mAddButton.setBackgroundColor(mActivity.getColor(R.color.color_ffffff));
            mAddButton.setTextColor(mActivity.getColor(R.color.colorAccent));
        }


    }

    @Override
    protected void initRequest() {

    }

    //--------------------------------------------------------------------------------------------
    // MARK : Local functions
    //--------------------------------------------------------------------------------------------

    /**
     * 가계부 작성
     */
    private void bookRegInAPI() {
        try {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < mNameList.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                if (!mNameList.get(i).getText().toString().equals("")) {
                    jsonObject.put("item_no", i);
                    jsonObject.put("item_name", mNameList.get(i).getText().toString());
                    jsonObject.put("item_bill", mPriceList.get(i).getText().toString());
                    jsonArray.put(jsonObject);
                }
                Timber.i("array : " + jsonObject.toString());
            }
            BookModel bookRequest = new BookModel();
            bookRequest.setHouse_code(Prefs.getString(Constants.HOUSE_CODE, ""));
            bookRequest.setBook_item_1(mGasEditText.getText().toString());
            bookRequest.setBook_item_2(mWaterEditText.getText().toString());
            bookRequest.setBook_item_3(mEleEditText.getText().toString());
            bookRequest.setItem_array(jsonArray.toString());
            CommonRouter.api().book_reg_in(Tools.getInstance().getMapper(bookRequest)).enqueue(new Callback<BookModel>() {
                @Override
                public void onResponse(Call<BookModel> call, Response<BookModel> response) {
                    BookModel mBookResponse = response.body();
                    if (mBookResponse.getCode().equals("1000")) {
                        mOnCalculateListener.OnResult();
                        finishWithRemove();
                    } else {
                        showAlertDialog(mBookResponse.getCode_msg(), "확인", new DialogEventListener() {
                            @Override
                            public void onReceivedEvent() {

                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<BookModel> call, Throwable t) {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 가계부 상세 API
     **/
    private void bookDetailAPI() {
        BookModel bookRequest = new BookModel();
        bookRequest.setBook_idx(mBookIdx);
        CommonRouter.api().book_detail(Tools.getInstance().getMapper(bookRequest)).enqueue(new Callback<BookModel>() {
            @Override
            public void onResponse(Call<BookModel> call, Response<BookModel> response) {
                BookModel mBookResponse = response.body();
                if (Tools.getInstance(mActivity).isSuccessResponse(response)) {
                    mGasEditText.setText(mBookResponse.getBook_item_1());
                    mWaterEditText.setText(mBookResponse.getBook_item_2());
                    mEleEditText.setText(mBookResponse.getBook_item_3());
                    if (mBookResponse.getDetail_list().size() > 0) {
                        if (mBookResponse.getDetail_list().get(0).getItem_name() != null) {
                            mNameEditText.setText(mBookResponse.getDetail_list().get(0).getItem_name());
                            mPrice1EditText.setText(mBookResponse.getDetail_list().get(0).getItem_bill());
                        }
                    }
                    if (mBookResponse.getDetail_list().size() > 1) {

                        if (mBookResponse.getDetail_list().get(1).getItem_name() != null) {
                            mName2EditText.setText(mBookResponse.getDetail_list().get(1).getItem_name());
                            mPrice2EditText.setText(mBookResponse.getDetail_list().get(1).getItem_bill());
                        }
                    }
                    if (mBookResponse.getDetail_list().size() > 2) {
                        if (mBookResponse.getDetail_list().get(2).getItem_name() != null) {
                            mName3EditText.setText(mBookResponse.getDetail_list().get(2).getItem_name());
                            mPrice3EditText.setText(mBookResponse.getDetail_list().get(2).getItem_bill());
                        }
                    }
                }
            }

        @Override
        public void onFailure (Call < BookModel > call, Throwable t){

        }
    });
}

    /**
     * 가계부 수정
     */
    private void bookModUpAPI() {
        try {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < mNameList.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                if (!mNameList.get(i).getText().toString().equals("")) {
                    jsonObject.put("item_no", i);
                    jsonObject.put("item_name", mNameList.get(i).getText().toString());
                    jsonObject.put("item_bill", mPriceList.get(i).getText().toString());
                    jsonArray.put(jsonObject);
                }
                Timber.i("array : " + jsonObject.toString());
            }
            BookModel bookRequest = new BookModel();
            bookRequest.setHouse_code(Prefs.getString(Constants.HOUSE_CODE, ""));
            bookRequest.setBook_item_1(mGasEditText.getText().toString());
            bookRequest.setBook_item_2(mWaterEditText.getText().toString());
            bookRequest.setBook_item_3(mEleEditText.getText().toString());
            bookRequest.setBook_idx(mBookIdx);
            bookRequest.setItem_array(jsonArray.toString());
            CommonRouter.api().book_mod_up(Tools.getInstance().getMapper(bookRequest)).enqueue(new Callback<BookModel>() {
                @Override
                public void onResponse(Call<BookModel> call, Response<BookModel> response) {
                    BookModel mBookResponse = response.body();
                    if (mBookResponse.getCode().equals("1000")) {
                        mOnCalculateListener.OnResult();
                        finishWithRemove();
                    } else {
                        showAlertDialog(mBookResponse.getCode_msg(), "확인", new DialogEventListener() {
                            @Override
                            public void onReceivedEvent() {

                            }
                        });
                    }

                }

                @Override
                public void onFailure(Call<BookModel> call, Throwable t) {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //--------------------------------------------------------------------------------------------
    // MARK : Bind Actions
    //--------------------------------------------------------------------------------------------

    /**
     * 작성하기
     */
    @OnClick(R.id.add_button)
    public void addTouched() {
        if (mBookIdx.equals("")) {
            showConfirmDialog("가계부를 작성하시겠습니까?", "취소", "확인", new DialogEventListener() {
                @Override
                public void onReceivedEvent() {
                    bookRegInAPI();
                }
            });
        } else {
            showConfirmDialog("가계부를 수정하시겠습니까?", "취소", "확인", new DialogEventListener() {
                @Override
                public void onReceivedEvent() {
                    bookModUpAPI();
                }
            });
        }
    }

}