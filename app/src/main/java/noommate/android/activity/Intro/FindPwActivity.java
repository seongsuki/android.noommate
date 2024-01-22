package noommate.android.activity.Intro;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.florent37.shapeofview.shapes.RoundRectView;
import noommate.android.R;
import noommate.android.activity.RocateerActivity;
import noommate.android.models.MemberModel;

public class FindPwActivity  extends RocateerActivity {
    //--------------------------------------------------------------------------------------------
    // MARK : GET START INTENT
    //--------------------------------------------------------------------------------------------
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, FindPwActivity.class);
        return intent;
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Area
    //--------------------------------------------------------------------------------------------
    @BindView(R.id.id_default_text_view)
    AppCompatTextView mIdDefaultTextView;
    @BindView(R.id.email_default_text_view)
    AppCompatTextView mEmailDefaultTextView;
    @BindView(R.id.id_edit_text)
    AppCompatEditText mIdEditText;
    @BindView(R.id.email_edit_text)
    AppCompatEditText mEmailEditText;
    @BindView(R.id.email_round_view)
    RoundRectView mEmailRoundView;
    @BindView(R.id.id_round_view)
    RoundRectView mIdRoundView;

    // --------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------

    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    @Override
    protected int inflateLayout() {
        return R.layout.activity_find_pw;
    }

    @Override
    protected void initLayout() {
        initToolbar("비밀번호 찾기");
    }

    @Override
    protected void initRequest() {

    }

    //--------------------------------------------------------------------------------------------
    // MARK : Local functions
    //--------------------------------------------------------------------------------------------


    //--------------------------------------------------------------------------------------------
    // MARK : Bind Actions
    //--------------------------------------------------------------------------------------------

    @OnClick(R.id.find_pw_button)
    public void findPwTouched() {
        mEmailDefaultTextView.setVisibility(View.VISIBLE);
        mIdDefaultTextView.setVisibility(View.VISIBLE);
        mEmailRoundView.setBorderColor(mActivity.getColor(R.color.colorAccent));
        mIdRoundView.setBorderColor(mActivity.getColor(R.color.colorAccent));
        mEmailEditText.setTextColor(mActivity.getColor(R.color.colorAccent));
        mIdEditText.setTextColor(mActivity.getColor(R.color.colorAccent));

    }


}
