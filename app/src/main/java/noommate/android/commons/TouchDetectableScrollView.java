package noommate.android.commons;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.widget.NestedScrollView;

public class TouchDetectableScrollView extends NestedScrollView {

  OnMyScrollChangeListener myScrollChangeListener;

  public TouchDetectableScrollView(Context context) {
    super(context);
  }

  public TouchDetectableScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public TouchDetectableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    super.onScrollChanged(l, t, oldl, oldt);

    if (myScrollChangeListener!=null)
    {
      if (t>oldt)
      {
//        myScrollChangeListener.onScrollDown();
      }
      else if (t<oldt){
//        myScrollChangeListener.onScrollUp();
      }
      View view = (View) getChildAt(getChildCount()-1);
      int diff = (view.getBottom()-(getHeight()+getScrollY()));
      if (diff == 0 ) {
        myScrollChangeListener.onBottomReached();
      }
    }
  }

  public OnMyScrollChangeListener getMyScrollChangeListener() {
    return myScrollChangeListener;
  }

  public void setMyScrollChangeListener(OnMyScrollChangeListener myScrollChangeListener) {
    this.myScrollChangeListener = myScrollChangeListener;
  }

  public interface OnMyScrollChangeListener
  {
    //    public void onScrollUp();
//    public void onScrollDown();
    public void onBottomReached();
  }
}
