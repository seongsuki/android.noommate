package noommate.android.activity.main.home;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

public class HomeScheduleListItem extends AbstractExpandableItem<HomeScheduleDetailItem> implements MultiItemEntity {
    public String title;


    public HomeScheduleListItem(String title) {
        this.title = title;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return HomeScheduleListAdapter.SCHEDULE_LIST;
    }
}
