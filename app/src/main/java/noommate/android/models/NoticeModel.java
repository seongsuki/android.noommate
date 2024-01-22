package noommate.android.models;


import java.util.ArrayList;

import lombok.Data;

@Data
public class NoticeModel extends BaseListModel {
  /// 공지사항 인덱스
  private String notice_idx;
  /// 공지사항 제목
  private String title;
  /// 공지사항 이미지
  private String img_path;
  /// 이미지 가로 길이 (px)
  private String img_width;
  /// 이미지 세로 길이 (px)
  private String img_height;
  /// 공지사항 내용
  private String contents;
  /// 공지사항 등록일
  private String ins_date;
  /// 공지사항 리스트
  private ArrayList<NoticeModel> data_array;

  public String getNotice_idx() {
    return notice_idx;
  }

  public void setNotice_idx(String notice_idx) {
    this.notice_idx = notice_idx;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getImg_path() {
    return img_path;
  }

  public void setImg_path(String img_path) {
    this.img_path = img_path;
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

  public String getContents() {
    return contents;
  }

  public void setContents(String contents) {
    this.contents = contents;
  }

  public String getIns_date() {
    return ins_date;
  }

  public void setIns_date(String ins_date) {
    this.ins_date = ins_date;
  }

  public ArrayList<NoticeModel> getData_array() {
    return data_array;
  }

  public void setData_array(ArrayList<NoticeModel> data_array) {
    this.data_array = data_array;
  }
}
