package noommate.android.models;

import lombok.Data;

@Data
public class BaseListModel extends BaseModel {

  // 현재 페이지 번호
  private int page_num = 0;
  // 리스트 갯수
  private int list_cnt = 0;
  // 총 페이지 수
  private int total_page = 1;
  // 총 아이템 갯수
  private int total_cnt = 0;


  /**
   * 다음 페이지 얻어오기
   * @return
   */
  public int getNextPage() {
    if (getPage_num() < 1) {
      return 1;
    } else {
      return getPage_num() + 1;
    }
  }

  /**
   * 페이지 초기화
   */
  public void resetPage() {
    this.setPage_num(0);
  }

  /**
   * 불러올 페이지가 더 있는지 확인
   * @return
   */
  public boolean isMore() {
    if (getTotal_page() > getPage_num()) {
      return true;
    } else {
      return false;
    }
  }

  public int getPage_num() {
    return page_num;
  }

  public void setPage_num(int page_num) {
    this.page_num = page_num;
  }

  public int getList_cnt() {
    return list_cnt;
  }

  public void setList_cnt(int list_cnt) {
    this.list_cnt = list_cnt;
  }

  public int getTotal_page() {
    return total_page;
  }

  public void setTotal_page(int total_page) {
    this.total_page = total_page;
  }

  public int getTotal_cnt() {
    return total_cnt;
  }

  public void setTotal_cnt(int total_cnt) {
    this.total_cnt = total_cnt;
  }
}
