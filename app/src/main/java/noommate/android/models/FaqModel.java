package noommate.android.models;

import java.util.ArrayList;

import lombok.Data;


@Data
public class FaqModel extends BaseListModel {

  /// FAQ 카테고리 키
  private String faq_category_idx;
  /// FAQ 카테고리 제목
  private String faq_category_name;
  /// FAQ 인덱스
  private String faq_idx;
  /// FAQ 제목
  private String title;
  /// FAQ 내용
  private String contents;
  /// FAQ 리스트 또는 FAQ 카테고리 리스트
  private ArrayList<FaqModel> data_array;

  public String getFaq_category_idx() {
    return faq_category_idx;
  }

  public void setFaq_category_idx(String faq_category_idx) {
    this.faq_category_idx = faq_category_idx;
  }

  public String getFaq_category_name() {
    return faq_category_name;
  }

  public void setFaq_category_name(String faq_category_name) {
    this.faq_category_name = faq_category_name;
  }

  public String getFaq_idx() {
    return faq_idx;
  }

  public void setFaq_idx(String faq_idx) {
    this.faq_idx = faq_idx;
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

  public ArrayList<FaqModel> getData_array() {
    return data_array;
  }

  public void setData_array(ArrayList<FaqModel> data_array) {
    this.data_array = data_array;
  }
}
