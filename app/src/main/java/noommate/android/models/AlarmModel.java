package noommate.android.models;

import java.util.ArrayList;

import lombok.Data;


@Data
public class AlarmModel extends BaseListModel {
  /// 알림 인덱스
  private String alarm_idx;
  /// 알림 제목
  private String msg;
  /// 알림 데이터: JSON 형식 > 파싱해서 써야함.
  private AlarmModel data;
  /// 읽음 유무: (Y: 읽음, N: 읽지 않음)
  private String read_yn;
  /// 삭제 유무: (Y: 삭제, N: 정상)
  private String del_yn;
  /// 알림 등록일
  private String ins_date;
  // 알림
  private String alarm_type;
  // 알림 유무
  private String alarm_yn;
  /// 알림 수정일
  private String upd_date;
  /// 모든 푸시 알림 상태: (Y: 수신, N: 수신거부)
  private String all_alarm_yn;
  /// 이벤트 푸시 알림 상태: (Y: 수신, N: 수신거부)
  private String alarm_my_item_yn;
  /// 공지사항 푸시 알림 상태: (Y: 수신, N: 수신거부)
  private String alarm_call_out_yn;
  /// 이메일 푸시 알림 상태: (Y: 수신, N: 수신거부)
  private String email_alarm_yn;
  /// 새로운 알림 카운트
  private String new_alarm_conut;
  /// 알림 리스트
  private ArrayList<AlarmModel> data_array;

  public String getAlarm_idx() {
    return alarm_idx;
  }

  public void setAlarm_idx(String alarm_idx) {
    this.alarm_idx = alarm_idx;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public AlarmModel getData() {
    return data;
  }

  public void setData(AlarmModel data) {
    this.data = data;
  }

  public String getRead_yn() {
    return read_yn;
  }

  public void setRead_yn(String read_yn) {
    this.read_yn = read_yn;
  }

  public String getDel_yn() {
    return del_yn;
  }

  public void setDel_yn(String del_yn) {
    this.del_yn = del_yn;
  }

  public String getIns_date() {
    return ins_date;
  }

  public void setIns_date(String ins_date) {
    this.ins_date = ins_date;
  }

  public String getUpd_date() {
    return upd_date;
  }

  public void setUpd_date(String upd_date) {
    this.upd_date = upd_date;
  }

  public String getAll_alarm_yn() {
    return all_alarm_yn;
  }

  public void setAll_alarm_yn(String all_alarm_yn) {
    this.all_alarm_yn = all_alarm_yn;
  }


  public String getEmail_alarm_yn() {
    return email_alarm_yn;
  }

  public void setEmail_alarm_yn(String email_alarm_yn) {
    this.email_alarm_yn = email_alarm_yn;
  }

  public String getNew_alarm_conut() {
    return new_alarm_conut;
  }

  public void setNew_alarm_conut(String new_alarm_conut) {
    this.new_alarm_conut = new_alarm_conut;
  }

  public ArrayList<AlarmModel> getData_array() {
    return data_array;
  }

  public void setData_array(ArrayList<AlarmModel> data_array) {
    this.data_array = data_array;
  }

  public String getAlarm_my_item_yn() {
    return alarm_my_item_yn;
  }

  public void setAlarm_my_item_yn(String alarm_my_item_yn) {
    this.alarm_my_item_yn = alarm_my_item_yn;
  }

  public String getAlarm_call_out_yn() {
    return alarm_call_out_yn;
  }

  public void setAlarm_call_out_yn(String alarm_call_out_yn) {
    this.alarm_call_out_yn = alarm_call_out_yn;
  }

  public String getAlarm_type() {
    return alarm_type;
  }

  public void setAlarm_type(String alarm_type) {
    this.alarm_type = alarm_type;
  }

  public String getAlarm_yn() {
    return alarm_yn;
  }

  public void setAlarm_yn(String alarm_yn) {
    this.alarm_yn = alarm_yn;
  }
}
