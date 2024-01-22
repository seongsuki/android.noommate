package noommate.android.models;

import noommate.android.activity.RocateerActivity;

public class ActivityModel {

  private String className;
  private RocateerActivity rocateerActivity;

  public ActivityModel(String className, RocateerActivity rocateerActivity) {
    this.className = className;
    this.rocateerActivity = rocateerActivity;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public RocateerActivity getRocateerActivity() {
    return rocateerActivity;
  }

  public void setRocateerActivity(RocateerActivity rocateerActivity) {
    this.rocateerActivity = rocateerActivity;
  }
}

