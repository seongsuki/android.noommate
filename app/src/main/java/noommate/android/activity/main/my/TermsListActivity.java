package noommate.android.activity.main.my;

import android.content.Context;
import android.content.Intent;

import butterknife.OnClick;
import noommate.android.R;
import noommate.android.activity.RocateerActivity;
import noommate.android.activity.commons.terms.TermsActivity;

public class TermsListActivity extends RocateerActivity {
    //--------------------------------------------------------------------------------------------
    // MARK : GET START INTENT
    //--------------------------------------------------------------------------------------------
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, TermsListActivity.class);
        return intent;
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Area
    //--------------------------------------------------------------------------------------------

    //--------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------

    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    @Override
    protected int inflateLayout() {
        return R.layout.activity_terms_list;
    }

    @Override
    protected void initLayout() {
        mToolbarTitle.setText("이용약관");

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

    /**
     * 이용약관 이동
     */
    @OnClick({R.id.service_button, R.id.pri_button, R.id.marketing_button})
    public void termsTouched() {
        Intent termsActivity = TermsActivity.getStartIntent(mActivity, TermsActivity.TermsType.SERVICE);
        startActivity(termsActivity,TRANS.PUSH);
    }



}
