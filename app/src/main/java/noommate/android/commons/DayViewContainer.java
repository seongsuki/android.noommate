package noommate.android.commons;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.kizitonwose.calendarview.ui.ViewContainer;

import io.github.florent37.shapeofview.shapes.RoundRectView;
import noommate.android.R;

public class DayViewContainer extends ViewContainer {

  public final AppCompatTextView mDayTextView;
  public final RoundRectView mRecordPointLayout;
  public final AppCompatTextView mTodayTextView;

  public DayViewContainer(@NonNull View view) {
    super(view);
    mDayTextView = view.findViewById(R.id.calendar_day_view);
    mRecordPointLayout = view.findViewById(R.id.record_point_layout);
    mTodayTextView = view.findViewById(R.id.today_text_view);
  }
}
