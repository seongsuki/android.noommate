package noommate.android.commons;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DecorationSpacingItem extends RecyclerView.ItemDecoration {

  private int spanCount;
  private int spacingPx;
  private int spacingEdge;
  private boolean includeEdge;

  public DecorationSpacingItem(int spanCount, int spacingPx, int spacingEdge, boolean includeEdge) {
    this.spanCount = spanCount;
    this.spacingPx = spacingPx;
    this.spacingEdge = spacingEdge;
    this.includeEdge = includeEdge;
  }

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
    int position = parent.getChildAdapterPosition(view); // item position
    int column = position % spanCount; // item column

    if (includeEdge) {

      GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
      int spanIndex = lp.getSpanIndex();


      if(spanIndex == 0) {
        //왼쪽 아이템
        outRect.left = spacingEdge;
        outRect.right = spacingPx - column * spacingPx / spanCount;;

      } else if(spanIndex == 2) {
        //오른쪽 아이템
        outRect.left = spacingPx - column * spacingPx / spanCount;;
        outRect.right = spacingEdge;
      }


      outRect.left = spacingPx - column * spacingPx / spanCount;
      outRect.right = (column + 1) * spacingPx / spanCount;

      if (position < spanCount) { // top edge
        outRect.top = spacingPx;
      }
      outRect.bottom = spacingPx; // item bottom
    } else {
      outRect.left = column * spacingPx / spanCount;
      outRect.right = spacingPx - (column + 1) * spacingPx / spanCount;
      if (position >= spanCount) {
        outRect.top = spacingPx; // item top
      }
    }
  }
}
