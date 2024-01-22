package noommate.android.activity.address;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import butterknife.BindView;
import noommate.android.R;
import noommate.android.activity.RocateerActivity;
import noommate.android.commons.RocateerWebViewClient;
import timber.log.Timber;

public class AddressActivity extends RocateerActivity {
  //--------------------------------------------------------------------------------------------
  // MARK : GET START INTENT
  //--------------------------------------------------------------------------------------------
  public static Intent getStartIntent(Context context) {
    Intent intent = new Intent(context, AddressActivity.class);
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

  //--------------------------------------------------------------------------------------------
  // MARK : Override
  //--------------------------------------------------------------------------------------------
  @Override
  protected int inflateLayout() {
    return R.layout.activity_address;
  }

  @Override
  protected void initLayout() {
    initToolbar("다음 주소 검색");
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
    mWebView.addJavascriptInterface(new AddressActivity.AndroidBridge(), "noommate");

    WebSettings webSettings = mWebView.getSettings();
    webSettings.setDefaultTextEncodingName("utf-8");
    webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    webSettings.setUseWideViewPort(true);
    webSettings.setJavaScriptEnabled(true);

    mWebView.setWebChromeClient(new WebChromeClient());
    mWebView.setWebViewClient(new RocateerWebViewClient(this));
    mWebView.setVerticalScrollbarOverlay(true); //스크롤설정

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      //혼합콘텐츠,타사쿠키사용
      webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

      CookieManager cookieManager = CookieManager.getInstance();
      cookieManager.setAcceptCookie(true);
      cookieManager.setAcceptThirdPartyCookies(mWebView, true);
    }
    mWebView.loadUrl("http://p-api.moaboom.site/daum_address_web_view_v_1_0_0/daum_address_api_view");
  }
  //--------------------------------------------------------------------------------------------
  // MARK : Bind Actions
  //--------------------------------------------------------------------------------------------
  private class AndroidBridge {
    @JavascriptInterface
    public void addr(final String member_addr_postcode, final String member_addr) {

      Timber.i("post:" + member_addr_postcode);
      Timber.i("addr:" + member_addr);

      Intent intent = new Intent();
      intent.putExtra("member_post", member_addr_postcode);
      intent.putExtra("member_addr", member_addr);
      setResult(RESULT_OK, intent);
      finish();
    }
  }
}