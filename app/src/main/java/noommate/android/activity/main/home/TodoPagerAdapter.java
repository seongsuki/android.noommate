package noommate.android.activity.main.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.islamkhsh.CardSliderAdapter;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

import noommate.android.R;
import noommate.android.activity.NoommateActivity;
import noommate.android.commons.EmptyView;
import noommate.android.commons.SpacingItemDecoration;
import noommate.android.commons.Tools;
import noommate.android.models.MemberModel;

public class TodoPagerAdapter extends CardSliderAdapter<TodoPagerAdapter.BannerHolder> {
    public interface HomeBannerListener {
        void onIntent(int position, int listPosition);
    }

    private Context mContext;
    private ArrayList<ArrayList<MemberModel>> mTodoList;
    private ArrayList<MemberModel> mDetailList;
    private NoommateActivity mActivity;
    private HomeScheduleAdapter mHomeScheduleAdapter;
    private static HomeBannerListener mHomeBannerListener;


    public TodoPagerAdapter(NoommateActivity context, ArrayList<ArrayList<MemberModel>> mTodoList, HomeBannerListener homeBannerListener) {
        this.mActivity = context;
        this.mTodoList = mTodoList;
        this.mHomeBannerListener = homeBannerListener;
    }

    @Override
    public int getItemCount() {
        return mTodoList.size();
    }

    @Override
    public TodoPagerAdapter.BannerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home_todo, parent, false);
        return new TodoPagerAdapter.BannerHolder(view);
    }

    @Override
    public void bindVH(@NotNull TodoPagerAdapter.BannerHolder bannerHolder, int j) {

        mDetailList = mTodoList.get(j);
        mHomeScheduleAdapter = new HomeScheduleAdapter(R.layout.row_home_schedule, mDetailList);
        mHomeScheduleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mTodoList.get(j).get(position).getSchedule_yn() != null) {
                    if (mTodoList.get(j).get(position).getSchedule_yn().equals("N")) {
                        mActivity.showConfirmDialog(mTodoList.get(j).get(position).getPlan_name() + " 을(를) 완료하셨나요?", "취소", "수행 완료했어요.", new NoommateActivity.DialogEventListener() {
                            @Override
                            public void onReceivedEvent() {
                                mHomeBannerListener.onIntent(j, position);
                            }
                        });

                    }
                }
            }
        });

        bannerHolder.mTodoRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        if (bannerHolder.mTodoRecyclerView.getItemDecorationCount() <= 0) {
            bannerHolder.mTodoRecyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.getInstance().dpTopx(mActivity, 15), true));
        }
        bannerHolder.mTodoRecyclerView.setAdapter(mHomeScheduleAdapter);





        if (mDetailList.size() == 1) {
            mDetailList.add(new MemberModel());
            mDetailList.add(new MemberModel());
            mDetailList.add(new MemberModel());
        } else if (mDetailList.size() == 2) {
            mDetailList.add(new MemberModel());
            mDetailList.add(new MemberModel());
        } else if (mDetailList.size() == 3) {
            mDetailList.add(new MemberModel());
        }

    }


    /**
     * 리스트 갱신
     */
    public void setNewData(ArrayList<ArrayList<MemberModel>> imageList) {
        this.mTodoList = imageList;
        this.notifyDataSetChanged();
    }

    class BannerHolder extends RecyclerView.ViewHolder {
        RecyclerView mTodoRecyclerView;

        public BannerHolder(View view) {
            super(view);
            mTodoRecyclerView = view.findViewById(R.id.todo_recycler_view);
        }
    }
}
