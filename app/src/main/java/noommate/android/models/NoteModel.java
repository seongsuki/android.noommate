package noommate.android.models;

import java.util.ArrayList;

public class NoteModel extends BaseListModel {
    // 하우스 코드
    private String house_code;
    // 알림장 키
    private String note_idx;
    private String member_nickname;
    // 신고 사유
    private String report_contents;
    // 신고 카테고리
    private String report_type;
    // 차단 여부
    private String block_yn;
    // 신고 여부
    private String report_yn;
    // 내 알림장 여부
    private String my_note_yn;
    // 얼굴
    private String member_role1;
    // 표정
    private String member_role2;
    // 배경
    private String member_role3;
    // 날짜
    private String ins_date;
    // 리스트
    private ArrayList<NoteModel> data_array;
    // 내용
    private String contents;

    public String getHouse_code() {
        return house_code;
    }

    public void setHouse_code(String house_code) {
        this.house_code = house_code;
    }

    public ArrayList<NoteModel> getData_array() {
        return data_array;
    }

    public void setData_array(ArrayList<NoteModel> data_array) {
        this.data_array = data_array;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getNote_idx() {
        return note_idx;
    }

    public void setNote_idx(String note_idx) {
        this.note_idx = note_idx;
    }

    public String getReport_contents() {
        return report_contents;
    }

    public void setReport_contents(String report_contents) {
        this.report_contents = report_contents;
    }

    public String getReport_type() {
        return report_type;
    }

    public void setReport_type(String report_type) {
        this.report_type = report_type;
    }

    public String getBlock_yn() {
        return block_yn;
    }

    public void setBlock_yn(String block_yn) {
        this.block_yn = block_yn;
    }

    public String getReport_yn() {
        return report_yn;
    }

    public void setReport_yn(String report_yn) {
        this.report_yn = report_yn;
    }

    public String getMy_note_yn() {
        return my_note_yn;
    }

    public void setMy_note_yn(String my_note_yn) {
        this.my_note_yn = my_note_yn;
    }

    public String getMember_role1() {
        return member_role1;
    }

    public void setMember_role1(String member_role1) {
        this.member_role1 = member_role1;
    }

    public String getMember_role2() {
        return member_role2;
    }

    public void setMember_role2(String member_role2) {
        this.member_role2 = member_role2;
    }

    public String getMember_role3() {
        return member_role3;
    }

    public void setMember_role3(String member_role3) {
        this.member_role3 = member_role3;
    }

    public String getIns_date() {
        return ins_date;
    }

    public void setIns_date(String ins_date) {
        this.ins_date = ins_date;
    }

    public String getMember_nickname() {
        return member_nickname;
    }

    public void setMember_nickname(String member_nickname) {
        this.member_nickname = member_nickname;
    }
}
