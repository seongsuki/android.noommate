package noommate.android.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MenuModel implements Parcelable {
  private int index;
  private String title;
  private String description;

  public MenuModel(int index, String title, String description) {
    this.index = index;
    this.title = title;
    this.description = description;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.index);
    dest.writeString(this.title);
    dest.writeString(this.description);
  }

  protected MenuModel(Parcel in) {
    this.index = in.readInt();
    this.title = in.readString();
    this.description = in.readString();
  }

  public static final Creator<MenuModel> CREATOR = new Creator<MenuModel>() {
    @Override
    public MenuModel createFromParcel(Parcel source) {
      return new MenuModel(source);
    }

    @Override
    public MenuModel[] newArray(int size) {
      return new MenuModel[size];
    }
  };
}
