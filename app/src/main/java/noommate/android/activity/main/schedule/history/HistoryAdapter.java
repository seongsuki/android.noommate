package noommate.android.activity.main.schedule.history;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import noommate.android.R;
import noommate.android.activity.main.schedule.schedulepage.SelectCharAdapter;
import noommate.android.commons.DecorationHorizontal;
import noommate.android.commons.Tools;
import noommate.android.models.BaseModel;

public class HistoryAdapter extends BaseQuickAdapter<BaseModel, BaseViewHolder> {
    public HistoryAdapter(int layoutResId, @Nullable List<BaseModel> data) {
        super(layoutResId, data);
    }

    private RecyclerView mCharRecyclerView;
    private SelectCharAdapter mSelectCharAdapter;
    private ArrayList<BaseModel> mCharList = new ArrayList<>();

    @Override
    protected void convert(BaseViewHolder helper, BaseModel item) {
        helper.addOnClickListener(R.id.delete_button);
        mCharRecyclerView = helper.getView(R.id.char_recycler_view);
        initSelectCharAdapter();
        mCharList.clear();
        mSelectCharAdapter.setNewData(mCharList);
        mCharList.add(new BaseModel());
        mCharList.add(new BaseModel());
        mCharList.add(new BaseModel());
    }

    /**
     * 캐릭터 리스트
     */
    private void initSelectCharAdapter() {
        mSelectCharAdapter = new SelectCharAdapter(R.layout.row_char, mCharList);
        mCharRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,false));
        mCharRecyclerView.addItemDecoration(new DecorationHorizontal(mContext, Tools.getInstance().dpTopx(mContext, 8), Tools.getInstance().dpTopx(mContext, 16)));
        mCharRecyclerView.setAdapter(mSelectCharAdapter);
    }
}
