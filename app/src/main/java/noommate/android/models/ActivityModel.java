package noommate.android.models;

import noommate.android.activity.NoommateActivity;

public class ActivityModel {

  private String className;
  private NoommateActivity noommateActivity;

  public ActivityModel(String className, NoommateActivity noommateActivity) {
    this.className = className;
    this.noommateActivity = noommateActivity;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public NoommateActivity getNoommateActivity() {
    return noommateActivity;
  }

  public void setNoommateActivity(NoommateActivity noommateActivity) {
    this.noommateActivity = noommateActivity;
  }
}

