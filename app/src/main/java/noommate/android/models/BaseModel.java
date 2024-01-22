package noommate.android.models;

import lombok.Data;

@Data
public class BaseModel {
  private boolean isSelected = false;
  private String code;
  private String code_msg;
  private String member_idx;
  private String device_os;
  private String gcm_key;

  public BaseModel() {
  }

  public BaseModel(String code, String code_msg, String member_idx, String device_os, String gcm_key) {
    this.code = code;
    this.code_msg = code_msg;
    this.member_idx = member_idx;
    this.device_os = device_os;
    this.gcm_key = gcm_key;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getCode_msg() {
    return code_msg;
  }

  public void setCode_msg(String code_msg) {
    this.code_msg = code_msg;
  }

  public String getMember_idx() {
    return member_idx;
  }

  public void setMember_idx(String member_idx) {
    this.member_idx = member_idx;
  }

  public String getDevice_os() {
    return device_os;
  }

  public void setDevice_os(String device_os) {
    this.device_os = device_os;
  }

  public String getGcm_key() {
    return gcm_key;
  }

  public void setGcm_key(String gcm_key) {
    this.gcm_key = gcm_key;
  }

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected(boolean selected) {
    isSelected = selected;
  }
}
