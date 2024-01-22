package noommate.android.models;

import lombok.Data;

@Data
public class EventModel extends BaseListModel {
  /// 이벤트 인덱스
  private String event_idx;
  /// 이벤트 제목
  private String title;
  /// 이벤트 내용
  private String contents;
  /// 이벤트 이미지 URL
  private String img_url;
  /// 이벤트 이미지 가로 길이
  private String img_width;
  /// 이벤트 이미지 세로 길이
  private String img_height;
  /// 이벤트 링크
  private String link_url;

  public String getEvent_idx() {
    return event_idx;
  }

  public void setEvent_idx(String event_idx) {
    this.event_idx = event_idx;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContents() {
    return contents;
  }

  public void setContents(String contents) {
    this.contents = contents;
  }

  public String getImg_url() {
    return img_url;
  }

  public void setImg_url(String img_url) {
    this.img_url = img_url;
  }

  public String getImg_width() {
    return img_width;
  }

  public void setImg_width(String img_width) {
    this.img_width = img_width;
  }

  public String getImg_height() {
    return img_height;
  }

  public void setImg_height(String img_height) {
    this.img_height = img_height;
  }

  public String getLink_url() {
    return link_url;
  }

  public void setLink_url(String link_url) {
    this.link_url = link_url;
  }
}
