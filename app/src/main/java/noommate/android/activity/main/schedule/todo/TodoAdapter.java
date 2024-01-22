package noommate.android.activity.main.schedule.todo;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import noommate.android.R;
import noommate.android.activity.main.schedule.history.TodoListAdapter;
import noommate.android.models.ScheduleModel;

public class TodoAdapter extends BaseQuickAdapter<ScheduleModel, BaseViewHolder> {
    public TodoAdapter(int layoutResId, @Nullable List<ScheduleModel> data) {
        super(layoutResId, data);
    }

    private TodoListAdapter mTodoListAdapter;
    private ArrayList<ScheduleModel> mTodoList = new ArrayList<>();
    private RecyclerView mTodoListRecyclerView;


    @Override
    protected void convert(BaseViewHolder helper, ScheduleModel item) {
        mTodoListRecyclerView = helper.getView(R.id.list_recycler_view);
        helper.setText(R.id.title_text_view, item.getPlan_name());

        mTodoList.clear();
        if (item.getPlan_item_list() != null) {
            mTodoList.addAll(item.getPlan_item_list());
        }
        mTodoListAdapter = new TodoListAdapter(R.layout.row_add_schedule, mTodoList);
        mTodoListRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mTodoListRecyclerView.setAdapter(mTodoListAdapter);
        mTodoListAdapter.setNewData(mTodoList);

    }


}
