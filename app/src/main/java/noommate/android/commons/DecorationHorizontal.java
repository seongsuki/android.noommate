package noommate.android.commons;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class DecorationHorizontal extends RecyclerView.ItemDecoration {

  private final int decorationWidth;
  private final int sectionWidth;
  private Context context;

  public DecorationHorizontal(Context context) {
    this.context = context;
    decorationWidth = context.getResources().getDimensionPixelSize(Tools.getInstance().getInstance().dpTopx(context, 20));
    sectionWidth = context.getResources().getDimensionPixelSize(Tools.getInstance().dpTopx(context, 20));

  }

  public DecorationHorizontal(Context context, int width, int section) {
    this.context = context;
    decorationWidth = width;
    sectionWidth = section;

  }

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
    super.getItemOffsets(outRect, view, parent, state);

    if (parent != null && view != null) {

      int itemPosition = parent.getChildAdapterPosition(view);
      int totalCount = parent.getAdapter().getItemCount();

      if (itemPosition == 0) { // 처음인 경우
        outRect.left = sectionWidth;
      } else if (itemPosition >= 0 && itemPosition < totalCount) { // 나머지
        outRect.left = decorationWidth;
      }

      if (itemPosition == totalCount - 1) {
        outRect.right = sectionWidth;
      }
    }
  }
}
