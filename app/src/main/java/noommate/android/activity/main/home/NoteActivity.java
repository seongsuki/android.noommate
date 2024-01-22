package noommate.android.activity.main.home;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import noommate.android.activity.NoommateActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import noommate.android.R;
import noommate.android.commons.Constants;
import noommate.android.commons.EmptyView;
import noommate.android.commons.Tools;
import noommate.android.dialog.MoreDialog;
import noommate.android.dialog.ReportDialog;
import noommate.android.models.NoteModel;
import noommate.android.models.api.CommonRouter;

public class NoteActivity extends NoommateActivity {
    //--------------------------------------------------------------------------------------------
    // MARK : GET START INTENT
    //--------------------------------------------------------------------------------------------
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, NoteActivity.class);
        return intent;
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Area
    //--------------------------------------------------------------------------------------------
    @BindView(R.id.note_recycler_view)
    RecyclerView mNoteRecyclerView;

    //--------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------
    private NoteModel mNoteResponse = new NoteModel();
    private NoteAdapter mNoteAdapter;
    private ArrayList<NoteModel> mNoteList = new ArrayList<>();

    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    @Override
    protected int inflateLayout() {
        return R.layout.activity_note;
    }

    @Override
    protected void initLayout() {


    }

    @Override
    protected void initRequest() {
        initNoteAdapter();
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Local functions
    //--------------------------------------------------------------------------------------------

    /**
     * 알림장 리스트
     */
    private void initNoteAdapter() {
        mNoteAdapter = new NoteAdapter(R.layout.row_note_list, mNoteList);
        mNoteAdapter.setEmptyView(new EmptyView(mActivity, "아직 작성된 알림장이 없어요."));
        mNoteAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.more_button) {
                    MoreDialog moreDialog = new MoreDialog(mActivity, new MoreDialog.OnMoreListener() {
                        @Override
                        public void onReportEvent() {
                            ReportDialog reportDialog = new ReportDialog(mActivity, mNoteList.get(position).getNote_idx(), new ReportDialog.ReportListener() {
                                @Override
                                public void onRefresh() {
                                    Intent feedRefresh = new Intent(Constants.HOME_REFRESH);
                                    mActivity.sendBroadcast(feedRefresh);
                                    mNoteList.clear();
                                    mNoteResponse.resetPage();
                                    noteListAPI();
                                }
                            });
                            reportDialog.show(getSupportFragmentManager(),"");

                        }

                        @Override
                        public void onBlockEvent() {
                            showConfirmDialog("해당 글을 차단할까요?", "취소", "확인", new DialogEventListener() {
                                @Override
                                public void onReceivedEvent() {
                                    blockModUpAPI(mNoteList.get(position).getNote_idx());
                                }
                            });

                        }
                    });
                    moreDialog.show(getSupportFragmentManager(),"");
                } else if (view.getId() == R.id.edit_button) {
                    Intent addNoteActivity = AddNoteActivity.getStartIntent(mActivity, mNoteList.get(position).getNote_idx(), new AddNoteActivity.OnAddNoteListener() {
                        @Override
                        public void OnRefresh() {
                            Intent feedRefresh = new Intent(Constants.HOME_REFRESH);
                            mActivity.sendBroadcast(feedRefresh);
                            mNoteList.clear();
                            mNoteResponse.resetPage();
                            noteListAPI();
                        }
                    });
                    startActivity(addNoteActivity,TRANS.PRESENT);
                } else if (view.getId() == R.id.delete_button) {
                    showConfirmDialog("작성한 알림장을 삭제할까요?", "취소", "삭제할래요.", new DialogEventListener() {
                        @Override
                        public void onReceivedEvent() {
                            noteDelAPI(mNoteList.get(position).getNote_idx());
                        }
                    });

                } else if (view.getId() == R.id.unblock_button) {
                    showConfirmDialog("차단을 해제할까요?", "취소", "차단 해제하기", new DialogEventListener() {
                        @Override
                        public void onReceivedEvent() {
                            blockModUpAPI(mNoteList.get(position).getNote_idx());
                        }
                    });
                }
            }
        });
        mNoteRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mNoteRecyclerView.setAdapter(mNoteAdapter);
        noteListAPI();

    }

    /**
     * 알림장 리스트
     */
    private void noteListAPI() {
        NoteModel noteRequest = new NoteModel();
        noteRequest.setMember_idx(Prefs.getString(Constants.MEMBER_IDX,""));
        noteRequest.setHouse_code(Prefs.getString(Constants.HOUSE_CODE,""));
        noteRequest.setPage_num(mNoteResponse.getNextPage());
        CommonRouter.api().note_list(Tools.getInstance().getMapper(noteRequest)).enqueue(new Callback<NoteModel>() {
            @Override
            public void onResponse(Call<NoteModel> call, Response<NoteModel> response) {
                mNoteResponse = response.body();
                if (mNoteResponse.getData_array() != null) {
                    mNoteList.addAll(mNoteResponse.getData_array());
                    mNoteAdapter.setNewData(mNoteList);
                }
            }

            @Override
            public void onFailure(Call<NoteModel> call, Throwable t) {

            }
        });
    }

    /**
     * 알림장 차단 API
     */
    private void blockModUpAPI(String noteIdx) {
        NoteModel noteRequest = new NoteModel();
        noteRequest.setNote_idx(noteIdx);
        noteRequest.setMember_idx(Prefs.getString(Constants.MEMBER_IDX,""));
        CommonRouter.api().block_mod_up(Tools.getInstance().getMapper(noteRequest)).enqueue(new Callback<NoteModel>() {
            @Override
            public void onResponse(Call<NoteModel> call, Response<NoteModel> response) {
                if (Tools.getInstance(mActivity).isSuccessResponse(response)) {
                    Intent feedRefresh = new Intent(Constants.HOME_REFRESH);
                    mActivity.sendBroadcast(feedRefresh);
                    mNoteList.clear();
                    mNoteResponse.resetPage();
                    noteListAPI();
                }
            }

            @Override
            public void onFailure(Call<NoteModel> call, Throwable t) {

            }
        });
    }

    /**
     * 알림장 삭제 API
     */
    private void noteDelAPI(String noteIdx) {
        NoteModel noteRequest = new NoteModel();
        noteRequest.setNote_idx(noteIdx);
        CommonRouter.api().note_del(Tools.getInstance().getMapper(noteRequest)).enqueue(new Callback<NoteModel>() {
            @Override
            public void onResponse(Call<NoteModel> call, Response<NoteModel> response) {
                if (Tools.getInstance(mActivity).isSuccessResponse(response)) {
                    Intent feedRefresh = new Intent(Constants.HOME_REFRESH);
                    mActivity.sendBroadcast(feedRefresh);
                    mNoteList.clear();
                    mNoteResponse.resetPage();
                    noteListAPI();
                }
            }

            @Override
            public void onFailure(Call<NoteModel> call, Throwable t) {

            }
        });
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Actions
    //--------------------------------------------------------------------------------------------

    /**
     * 작성
     */
    @OnClick(R.id.add_button)
    public void addTouched() {
        Intent addNoteActivity = AddNoteActivity.getStartIntent(mActivity, "", new AddNoteActivity.OnAddNoteListener() {
            @Override
            public void OnRefresh() {
                Intent feedRefresh = new Intent(Constants.HOME_REFRESH);
                mActivity.sendBroadcast(feedRefresh);
                mNoteList.clear();
                mNoteResponse.resetPage();
                noteListAPI();
            }
        });
        startActivity(addNoteActivity,TRANS.PRESENT);
    }





}
