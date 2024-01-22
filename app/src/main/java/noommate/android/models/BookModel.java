package noommate.android.models;

import java.util.ArrayList;

public class BookModel extends BaseListModel{
    // 가계부 키
    private String book_idx;
    // 하우스 키
    private String house_idx;
    // 하우스 코드
    private String house_code;
    // 달
    private String month;
    // 가스
    private String book_item_1;
    // 수도세
    private String book_item_2;
    // 전기세
    private String book_item_3;
    // 그 외 키
    private String item_idx;
    // 그 외 명
    private String item_name;
    // 그 외 금액
    private String item_bill;
    // 그 외 넘버
    private String item_no;
    // 전체 가계부
    private ArrayList<BookModel> detail_list;
    private ArrayList<BookModel> data_array;
    private ArrayList<BookModel> item_list;
    // 그 외
    private String item_array;


    public String getBook_idx() {
        return book_idx;
    }

    public void setBook_idx(String book_idx) {
        this.book_idx = book_idx;
    }

    public String getHouse_idx() {
        return house_idx;
    }

    public void setHouse_idx(String house_idx) {
        this.house_idx = house_idx;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getBook_item_1() {
        return book_item_1;
    }

    public void setBook_item_1(String book_item_1) {
        this.book_item_1 = book_item_1;
    }

    public String getBook_item_2() {
        return book_item_2;
    }

    public void setBook_item_2(String book_item_2) {
        this.book_item_2 = book_item_2;
    }

    public String getBook_item_3() {
        return book_item_3;
    }

    public void setBook_item_3(String book_item_3) {
        this.book_item_3 = book_item_3;
    }

    public ArrayList<BookModel> getDetail_list() {
        return detail_list;
    }

    public void setDetail_list(ArrayList<BookModel> detail_list) {
        this.detail_list = detail_list;
    }

    public String getHouse_code() {
        return house_code;
    }

    public void setHouse_code(String house_code) {
        this.house_code = house_code;
    }

    public String getItem_array() {
        return item_array;
    }

    public void setItem_array(String item_array) {
        this.item_array = item_array;
    }

    public String getItem_idx() {
        return item_idx;
    }

    public void setItem_idx(String item_idx) {
        this.item_idx = item_idx;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_bill() {
        return item_bill;
    }

    public void setItem_bill(String item_bill) {
        this.item_bill = item_bill;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public ArrayList<BookModel> getData_array() {
        return data_array;
    }

    public void setData_array(ArrayList<BookModel> data_array) {
        this.data_array = data_array;
    }

    public ArrayList<BookModel> getItem_list() {
        return item_list;
    }

    public void setItem_list(ArrayList<BookModel> item_list) {
        this.item_list = item_list;
    }
}
