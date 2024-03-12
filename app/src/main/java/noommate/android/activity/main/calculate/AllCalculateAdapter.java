package noommate.android.activity.main.calculate;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

import noommate.android.R;
import noommate.android.activity.NoommateActivity;
import noommate.android.commons.Tools;
import noommate.android.models.BookModel;
import timber.log.Timber;

public class AllCalculateAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    //--------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------
    public static final int CALCULATE_LIST = 0;
    public static final int CALCULATE_DETAIL = 1;

    private ArrayList<BookModel> mDetailList = new ArrayList<>();
    private DetailListAdapter mDetailListAdapter;
    private RecyclerView mDetailRecyclerView;

    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    public AllCalculateAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(CALCULATE_LIST, R.layout.row_calculate_list);
        addItemType(CALCULATE_DETAIL, R.layout.row_calculate_detail);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case CALCULATE_LIST:
                final CalculateListItem calculateListItem = (CalculateListItem) item;

                int sum = 0;
                int total = 0;
                for (BookModel value : calculateListItem.bookModel.getItem_list()) {
                    int i = Integer.parseInt(value.getItem_bill());
                    sum += i;
                }

                total = Integer.parseInt(String.valueOf(sum + Integer.parseInt(calculateListItem.bookModel.getBook_item_1()) + Integer.parseInt(calculateListItem.bookModel.getBook_item_2()) + Integer.parseInt(calculateListItem.bookModel.getBook_item_3())));
                helper.setText(R.id.price_text_view, Tools.getInstance().numberPlaceValue(String.valueOf(total)) + " 원");
                helper.setText(R.id.date_text_view, calculateListItem.bookModel.getMonth());


                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (calculateListItem.isExpanded()) {
                            collapse(pos, true);
                        } else {
                            expand(pos, true);
                        }
                    }
                });


                break;
            case CALCULATE_DETAIL:
                final CalculateDetailItem calculateDetailItem = (CalculateDetailItem) item;
                mDetailRecyclerView = helper.getView(R.id.detail_list_recycler_view);
                mDetailList.clear();
                mDetailList.add(new BookModel());
                mDetailList.add(new BookModel());
                mDetailList.add(new BookModel());

                mDetailList.get(0).setItem_name("가스");
                mDetailList.get(0).setItem_bill(calculateDetailItem.bookModel.getBook_item_1());
                mDetailList.get(1).setItem_name("수도세");
                mDetailList.get(1).setItem_bill(calculateDetailItem.bookModel.getBook_item_2());
                mDetailList.get(2).setItem_name("전기세");
                mDetailList.get(2).setItem_bill(calculateDetailItem.bookModel.getBook_item_3());
                mDetailList.addAll(calculateDetailItem.item_list);

                mDetailListAdapter = new DetailListAdapter(R.layout.row_detail_list, calculateDetailItem.item_list);
                mDetailRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                mDetailRecyclerView.setAdapter(mDetailListAdapter);
                mDetailListAdapter.setNewData(mDetailList);
                break;
        }
    }

}

