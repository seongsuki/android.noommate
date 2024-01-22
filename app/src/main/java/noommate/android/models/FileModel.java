package noommate.android.models;

import lombok.Data;

@Data
public class FileModel extends BaseModel {
  // 파일 경로
  private String file_path;
  // 파일 원본 이름
  private String orig_name;
  // 파일 가로 사이즈
  private String img_width;
  // 파일 세로 사이즈
  private String img_height;

  public String getFile_path() {
    return file_path;
  }

  public void setFile_path(String file_path) {
    this.file_path = file_path;
  }

  public String getOrig_name() {
    return orig_name;
  }

  public void setOrig_name(String orig_name) {
    this.orig_name = orig_name;
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
}
