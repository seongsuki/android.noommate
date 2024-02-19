package noommate.android.activity.commons.terms;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import butterknife.BindView;
import noommate.android.R;
import noommate.android.activity.NoommateActivity;
import noommate.android.commons.NoommateWebViewClient;
import noommate.android.models.api.BaseRouter;


public class TermsActivity extends NoommateActivity {
  public enum TermsType {
    SERVICE, PRI, MARKETING
  }
  //--------------------------------------------------------------------------------------------
  // MARK : GET START INTENT
  //--------------------------------------------------------------------------------------------
  public static Intent getStartIntent(Context context, TermsType termsType) {
    Intent intent = new Intent(context, TermsActivity.class);
    mTermType = termsType;
    return intent;
  }
  //--------------------------------------------------------------------------------------------
  // MARK : Bind Area
  //--------------------------------------------------------------------------------------------
  @BindView(R.id.web_view)
  WebView mWebView;
  //--------------------------------------------------------------------------------------------
  // MARK : Local variables
  //--------------------------------------------------------------------------------------------
  private static TermsType mTermType;
  //--------------------------------------------------------------------------------------------
  // MARK : Override
  //--------------------------------------------------------------------------------------------
  @Override
  protected int inflateLayout() {
    return R.layout.activity_terms;
  }

  @Override
  protected void initLayout() {
    if (mTermType == TermsType.SERVICE) {
      mToolbarTitle.setText("서비스 이용약관");
    } else if (mTermType == TermsType.PRI) {
      mToolbarTitle.setText("개인정보 처리 방침");
    } else if (mTermType == TermsType.MARKETING){
      mToolbarTitle.setText("마케팅 정보 수신 동의");
    }
    initWebView();
  }

  @Override
  protected void initRequest() {

  }

  //--------------------------------------------------------------------------------------------
  // MARK : Local functions
  //--------------------------------------------------------------------------------------------
  /**
   * 웹뷰 초기
   */
  private void initWebView() {

    WebSettings webSettings = mWebView.getSettings();
    webSettings.setDefaultTextEncodingName("utf-8");
    webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    webSettings.setUseWideViewPort(true);
    webSettings.setJavaScriptEnabled(true);

    mWebView.setWebChromeClient(new WebChromeClient());
    mWebView.setWebViewClient(new NoommateWebViewClient(this));
    mWebView.setVerticalScrollbarOverlay(true); //스크롤설정

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      //혼합콘텐츠,타사쿠키사용
      webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

      CookieManager cookieManager = CookieManager.getInstance();
      cookieManager.setAcceptCookie(true);
      cookieManager.setAcceptThirdPartyCookies(mWebView, true);
    }
    if (mTermType == TermsType.SERVICE) {
      mWebView.loadUrl(BaseRouter.BASE_URL + "terms_web_view_v_1_0_0/terms_detail?type=1");
    } else if (mTermType == TermsType.PRI) {
      mWebView.loadUrl(BaseRouter.BASE_URL + "terms_web_view_v_1_0_0/terms_detail?type=2");
    } else {
      mWebView.loadUrl(BaseRouter.BASE_URL + "terms_web_view_v_1_0_0/terms_detail?type=3");
    }
  }
  //--------------------------------------------------------------------------------------------
  // MARK : Bind Actions
  //--------------------------------------------------------------------------------------------

}