package noommate.android.activity.main.schedule.todo;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import noommate.android.R;
import noommate.android.activity.RocateerActivity;
import noommate.android.activity.RocateerFragment;
import noommate.android.activity.main.schedule.ScheduleFragment;
import noommate.android.activity.main.schedule.schedulepage.AddScheduleActivity;
import noommate.android.commons.Constants;
import noommate.android.commons.Tools;
import noommate.android.models.ScheduleModel;
import noommate.android.models.api.CommonRouter;
import timber.log.Timber;

public class TodoFragment extends RocateerFragment {
    //--------------------------------------------------------------------------------------------
    // MARK : GET START INTENT
    //--------------------------------------------------------------------------------------------
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ScheduleFragment.class);
        return intent;
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Area
    //--------------------------------------------------------------------------------------------
    @BindView(R.id.todo_recycler_view)
    RecyclerView mTodoRecyclerView;

    //--------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------
    private TodoAdapter mTodoAdapter;
    private ArrayList<ScheduleModel> mTodoList = new ArrayList<>();
    private ScheduleModel mScheduleResponse = new ScheduleModel();

    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    @Override
    protected int inflateLayout() {
        return R.layout.fragment_todo;
    }

    @Override
    protected void initLayout() {

    }

    @Override
    protected void initRequest() {
        initTodoAdapter();

    }

    //--------------------------------------------------------------------------------------------
    // MARK : Local functions
    //--------------------------------------------------------------------------------------------

    /**
     * 할일 리스트
     */
    private void initTodoAdapter() {
        mTodoAdapter = new TodoAdapter(R.layout.row_todo, mTodoList);
        mTodoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent addScheduleActivity = AddScheduleActivity.getStartIntent(mActivity, mTodoList.get(position).getPlan_idx(), new AddScheduleActivity.OnAddScheduleListener() {
                    @Override
                    public void onRefresh() {
                        mTodoList.clear();
                        mScheduleResponse.resetPage();
                        planListAPI();

                    }
                });
                startActivity(addScheduleActivity, RocateerActivity.TRANS.PUSH);

            }
        });
        mTodoRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mTodoRecyclerView.setAdapter(mTodoAdapter);
        planListAPI();
    }

    /**
     * 할일 리스트 API
     */
    private void planListAPI() {
        ScheduleModel scheduleRequest = new ScheduleModel();
        scheduleRequest.setHouse_idx(Prefs.getString(Constants.HOUSE_IDX,""));
        scheduleRequest.setMember_idx(Prefs.getString(Constants.MEMBER_IDX,""));
        scheduleRequest.setPage_num(mScheduleResponse.getNextPage());
        CommonRouter.api().plan_list(Tools.getInstance().getMapper(scheduleRequest)).enqueue(new Callback<ScheduleModel>() {
            @Override
            public void onResponse(Call<ScheduleModel> call, Response<ScheduleModel> response) {
                mScheduleResponse = response.body();
                if (Tools.getInstance(mActivity).isSuccessResponse(response)) {
                    if (mScheduleResponse.getData_array() != null) {
                        Timber.i("할일 수 : " + mScheduleResponse.getData_array().size());
                        mTodoList.addAll(mScheduleResponse.getData_array());
                    }
                    mTodoAdapter.setNewData(mTodoList);
                }
            }

            @Override
            public void onFailure(Call<ScheduleModel> call, Throwable t) {

            }
        });
    }


    //--------------------------------------------------------------------------------------------
    // MARK : Bind Actions
    //--------------------------------------------------------------------------------------------


}

