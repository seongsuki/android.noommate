package noommate.android.models;

import lombok.Data;

@Data
public class BannerModel extends BaseListModel {
  /// 배너 인덱스
  private String banner_idx;
  /// 배너 제목
  private String title;
  /// 배너 이미지 URL
  private String img_url;
  /// 배너 링크
  private String link_url;

  public BannerModel() {
  }

  public String getBanner_idx() {
    return banner_idx;
  }

  public void setBanner_idx(String banner_idx) {
    this.banner_idx = banner_idx;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getImg_url() {
    return img_url;
  }

  public void setImg_url(String img_url) {
    this.img_url = img_url;
  }

  public String getLink_url() {
    return link_url;
  }

  public void setLink_url(String link_url) {
    this.link_url = link_url;
  }
}
