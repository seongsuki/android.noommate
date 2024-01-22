package noommate.android.commons;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import noommate.android.R;


public class EmptyView extends LinearLayout {
  AppCompatTextView mEmptyTextView;


  public EmptyView(Context context, String emptyText) {
    super(context);
    initView(emptyText, 0);
  }

  public EmptyView(Context context, String emptyText, int emptyImage) {
    super(context);
    initView(emptyText, emptyImage);
  }

  /**
   * 뷰 초기화
   */
  private void initView(String emptyText, int emptyImage) {
    String infService = Context.LAYOUT_INFLATER_SERVICE;
    LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
    View v = li.inflate(R.layout.view_empty, this, false);
    AppCompatTextView emptyTextView = v.findViewById(R.id.empty_text_view);
    AppCompatImageView emptyImageView = v.findViewById(R.id.empty_image_view);
    emptyTextView.setText(emptyText);

    if (emptyImage != 0) {
      emptyImageView.setVisibility(VISIBLE);
      emptyImageView.setImageResource(emptyImage);
    } else {
      emptyImageView.setVisibility(GONE);
    }

    addView(v);
  }
}
