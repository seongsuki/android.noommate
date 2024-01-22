package noommate.android.activity.main.home;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import noommate.android.models.ScheduleModel;

public class HomeScheduleDetailItem extends AbstractExpandableItem<HomeScheduleDetailItem> implements MultiItemEntity {
    ScheduleModel scheduleModel;

    public HomeScheduleDetailItem(ScheduleModel scheduleModel) {
        this.scheduleModel = scheduleModel;
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public int getItemType() {
        return HomeScheduleListAdapter.SCHEDULE_DETAIL;
    }
}


