package noommate.android.models;

import java.util.ArrayList;

import lombok.Data;

@Data
public class QnaModel extends BaseListModel {
  /// QNA 인덱스
  private String qa_idx;
  /// QNA 질문 제목
  private String qa_title;
  /// QNA 질문 내용
  private String qa_contents;
  /// QNA 답변 유무: (Y: 답변 있음, N: 답변 없음)
  private String reply_yn;
  /// QNA 답변 내용
  private String reply_contents;
  // qna 타입
  private String qa_type;
  /// QNA 답변일
  private String reply_date;
  /// QNA 등록일
  private String ins_date;
  /// QNA 리스트
  private ArrayList<QnaModel> data_array;

  public String getQa_idx() {
    return qa_idx;
  }

  public void setQa_idx(String qa_idx) {
    this.qa_idx = qa_idx;
  }

  public String getQa_title() {
    return qa_title;
  }

  public void setQa_title(String qa_title) {
    this.qa_title = qa_title;
  }

  public String getQa_contents() {
    return qa_contents;
  }

  public void setQa_contents(String qa_contents) {
    this.qa_contents = qa_contents;
  }

  public String getReply_yn() {
    return reply_yn;
  }

  public void setReply_yn(String reply_yn) {
    this.reply_yn = reply_yn;
  }

  public String getReply_contents() {
    return reply_contents;
  }

  public void setReply_contents(String reply_contents) {
    this.reply_contents = reply_contents;
  }

  public String getReply_date() {
    return reply_date;
  }

  public void setReply_date(String reply_date) {
    this.reply_date = reply_date;
  }

  public String getIns_date() {
    return ins_date;
  }

  public void setIns_date(String ins_date) {
    this.ins_date = ins_date;
  }

  public ArrayList<QnaModel> getData_array() {
    return data_array;
  }

  public void setData_array(ArrayList<QnaModel> data_array) {
    this.data_array = data_array;
  }


  public String getQa_type() {
    return qa_type;
  }

  public void setQa_type(String qa_type) {
    this.qa_type = qa_type;
  }
}
