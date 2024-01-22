package noommate.android.models;

public class HouseModel extends BaseListModel {
    // 하우스 이름
    private String house_name;
    // 하우스 이미지
    private String house_img;
    // 하우스 키
    private String house_idx;
    // 하우스 코드
    private String house_code;


    public String getHouse_name() {
        return house_name;
    }

    public void setHouse_name(String house_name) {
        this.house_name = house_name;
    }

    public String getHouse_img() {
        return house_img;
    }

    public void setHouse_img(String house_img) {
        this.house_img = house_img;
    }

    public String getHouse_idx() {
        return house_idx;
    }

    public void setHouse_idx(String house_idx) {
        this.house_idx = house_idx;
    }

    public String getHouse_code() {
        return house_code;
    }

    public void setHouse_code(String house_code) {
        this.house_code = house_code;
    }
}
