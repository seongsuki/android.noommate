package noommate.android.commons;

import android.app.Activity;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NoommateWebViewClient extends WebViewClient {
  private Activity activity;

  public NoommateWebViewClient(Activity activity) {
    this.activity = activity;
  }

  @Override
  public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

    return super.shouldOverrideUrlLoading(view, request);
  }
}
