package noommate.android.activity.main.home;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.pixplicity.easyprefs.library.Prefs;

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
import noommate.android.models.NoteModel;
import noommate.android.models.api.CommonRouter;

public class AddNoteActivity extends NoommateActivity {
    public interface OnAddNoteListener {
        void OnRefresh();
    }
    //--------------------------------------------------------------------------------------------
    // MARK : GET START INTENT
    //--------------------------------------------------------------------------------------------
    public static Intent getStartIntent(Context context,String noteIdx, OnAddNoteListener onAddNoteListener) {
        Intent intent = new Intent(context, AddNoteActivity.class);
        mOnAddNoteListener = onAddNoteListener;
        mNoteIdx = noteIdx;
        return intent;
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Area
    //--------------------------------------------------------------------------------------------
    @BindView(R.id.contents_edit_text)
    AppCompatEditText mContentsEditText;
    @BindView(R.id.count_text_view)
    AppCompatTextView mCountTextView;
    @BindView(R.id.add_button)
    AppCompatTextView mAddButton;
    @BindView(R.id.edit_layout)
    RoundRectView mEditLayout;

    //--------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------
    private static OnAddNoteListener mOnAddNoteListener;
    private static String mNoteIdx;

    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    @Override
    protected int inflateLayout() {
        return R.layout.activity_add_note;
    }

    @Override
    protected void initLayout() {
        if (mNoteIdx.equals("")) {
            mAddButton.setText("등록");
            mAddButton.setBackgroundColor(mActivity.getColor(R.color.color_87b7ff));
        } else {
            noteDetailAPI();
            mAddButton.setText("수정");
            mAddButton.setBackgroundColor(mActivity.getColor(R.color.colorAccent));
        }

        mContentsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCountTextView.setText(String.valueOf(mContentsEditText.getText().length()));
                if (mContentsEditText.getText().length() > 0) {
                    mEditLayout.setBorderColor(mActivity.getColor(R.color.color_87b7ff));
                } else {
                    mEditLayout.setBorderColor(mActivity.getColor(R.color.color_c8ccd5));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void initRequest() {
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Local functions
    //--------------------------------------------------------------------------------------------

    /**
     * 알림장 등록
     */
    private void noteRegInAPI() {
        NoteModel noteRequest= new NoteModel();
        noteRequest.setMember_idx(Prefs.getString(Constants.MEMBER_IDX,""));
        noteRequest.setHouse_code(Prefs.getString(Constants.HOUSE_CODE,""));
        noteRequest.setContents(mContentsEditText.getText().toString());
        CommonRouter.api().note_reg_in(Tools.getInstance().getMapper(noteRequest)).enqueue(new Callback<NoteModel>() {
            @Override
            public void onResponse(Call<NoteModel> call, Response<NoteModel> response) {
                if (Tools.getInstance(mActivity).isSuccessResponse(response)) {
                    finishWithRemove();
                    mOnAddNoteListener.OnRefresh();
                }
            }

            @Override
            public void onFailure(Call<NoteModel> call, Throwable t) {

            }
        });
    }

    /**
     * 알림장 등록
     */
    private void noteModUpAPI() {
        NoteModel noteRequest= new NoteModel();
        noteRequest.setNote_idx(mNoteIdx);
        noteRequest.setContents(mContentsEditText.getText().toString());
        CommonRouter.api().note_mod_up(Tools.getInstance().getMapper(noteRequest)).enqueue(new Callback<NoteModel>() {
            @Override
            public void onResponse(Call<NoteModel> call, Response<NoteModel> response) {
                if (Tools.getInstance(mActivity).isSuccessResponse(response)) {
                    finishWithRemove();
                    mOnAddNoteListener.OnRefresh();
                }
            }

            @Override
            public void onFailure(Call<NoteModel> call, Throwable t) {

            }
        });
    }

    /**
     * 알림장 상세 API
     */
    private void noteDetailAPI() {
        NoteModel noteRequest = new NoteModel();
        noteRequest.setNote_idx(mNoteIdx);
        CommonRouter.api().note_detail(Tools.getInstance().getMapper(noteRequest)).enqueue(new Callback<NoteModel>() {
            @Override
            public void onResponse(Call<NoteModel> call, Response<NoteModel> response) {
                NoteModel mNoteResponse = response.body();
                if (Tools.getInstance(mActivity).isSuccessResponse(response)) {
                    mContentsEditText.setText(mNoteResponse.getContents());
//                    mCountTextView.setText(mContentsEditText.getText().toString().length());

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
     * 등록
     */
    @OnClick(R.id.add_button)
    public void addTouched() {
        if (mNoteIdx.equals("")) {
            noteRegInAPI();
        } else {
            noteModUpAPI();

        }
    }




}
