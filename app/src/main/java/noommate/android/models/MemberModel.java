package noommate.android.models;

import java.util.ArrayList;

import lombok.Data;

@Data
public class MemberModel extends BaseModel {
    /// 회원 아이디
    private String member_id;
    private String ins_date;
    private String alarm_hour;
    // 하우스 이미지
    private String house_img;
    private String coc_cnt;
    // 하우스 키
    private String house_idx;
    private String member_new_pw;
    private String member_confirm_pw;
    private String member_leave_yn;
    /// 회원 비밀번호
    private String member_pw;
    // 회원 이메일
    private String member_email;
    /// 회원 비밀번호 확인
    private String member_pw_confirm;
    // 하우스 코드
    private String house_code;
    // 하우스 이름
    private String house_name;
    /// 회원 새로운 비밀번호 - 비멀번호 변경 시 사용
    private String new_member_pw;
    /// 회원 새로운 비밀번호 확인 - 비밀번호 변경 시 사용
    private String new_member_pw_check;
    /// SNS 회원가입시 타입 (C: 일반, K: 카카오톡, F: 페이스북, N: 네이버)
    private String member_join_type;
    /// 회원 이름
    private String member_name;
    /// 회원 닉네임
    private String member_nickname;
    /// 회원 생년월일
    private String member_birth;
    /// 회원 휴대폰 번호
    private String member_phone;
    /// 회원 성별: (0: 남성, 1: 여성, 2: 무관-사용 안함)
    private String member_gender;
    /// 회원 이미지
    private String member_img;
    /// 회원 알림 설정: (Y: 사용함, N: 사용안함)
    private String alarm_yn;
    /// 회원 탈퇴 사유: (0: 사용하지 않음, 1: 컨텐츠 부족, 2: 부적절한 컨텐츠, 3: 기타)
    private String member_leave_type;
    /// 회원탈퇴 사유 내용
    private String member_leave_reason;
    // 캐릭터 얼굴
    private String member_role1;
    // 캐릭터 얼굴2
    private String member_role2;
    // 캐익터 얼굴3
    private String member_role3;
    // 나 여부
    private String my_yn;
    // 아이디 중복 체크
    private String id_check;
    private String my_schedule_count;
    private String mate_cnt;
    private String schedule_idx; //스케줄 키
    private String schedule_date; //날짜
    private String plan_idx;//일정키
    private String plan_name; //일정명
    private String schedule_yn; //스케줄 완료 유
    private String note_idx; //알림장키
    private String contents; //내용,


    // 리스트
    private ArrayList<MemberModel> data_array;
    // 메이트 리스트
    private ArrayList<MemberModel> mate_array;
    // 일정 리스트
    private ArrayList<MemberModel> my_schedule_array;
    // 알림장 리스트
    private ArrayList<MemberModel> note_array;
    // 페이지 리스트
    private ArrayList<MemberModel> page_array;

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_pw() {
        return member_pw;
    }

    public void setMember_pw(String member_pw) {
        this.member_pw = member_pw;
    }

    public String getMember_pw_confirm() {
        return member_pw_confirm;
    }

    public void setMember_pw_confirm(String member_pw_confirm) {
        this.member_pw_confirm = member_pw_confirm;
    }

    public String getNew_member_pw() {
        return new_member_pw;
    }

    public void setNew_member_pw(String new_member_pw) {
        this.new_member_pw = new_member_pw;
    }

    public String getNew_member_pw_check() {
        return new_member_pw_check;
    }

    public void setNew_member_pw_check(String new_member_pw_check) {
        this.new_member_pw_check = new_member_pw_check;
    }

    public String getMember_join_type() {
        return member_join_type;
    }

    public void setMember_join_type(String member_join_type) {
        this.member_join_type = member_join_type;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_nickname() {
        return member_nickname;
    }

    public void setMember_nickname(String member_nickname) {
        this.member_nickname = member_nickname;
    }

    public String getMember_birth() {
        return member_birth;
    }

    public void setMember_birth(String member_birth) {
        this.member_birth = member_birth;
    }

    public String getMember_phone() {
        return member_phone;
    }

    public void setMember_phone(String member_phone) {
        this.member_phone = member_phone;
    }

    public String getMember_gender() {
        return member_gender;
    }

    public void setMember_gender(String member_gender) {
        this.member_gender = member_gender;
    }

    public String getMember_img() {
        return member_img;
    }

    public void setMember_img(String member_img) {
        this.member_img = member_img;
    }

    public String getAlarm_yn() {
        return alarm_yn;
    }

    public void setAlarm_yn(String alarm_yn) {
        this.alarm_yn = alarm_yn;
    }

    public String getMember_leave_type() {
        return member_leave_type;
    }

    public void setMember_leave_type(String member_leave_type) {
        this.member_leave_type = member_leave_type;
    }

    public String getMember_leave_reason() {
        return member_leave_reason;
    }

    public void setMember_leave_reason(String member_leave_reason) {
        this.member_leave_reason = member_leave_reason;
    }

    public String getMember_email() {
        return member_email;
    }

    public void setMember_email(String member_email) {
        this.member_email = member_email;
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

    public String getHouse_code() {
        return house_code;
    }

    public void setHouse_code(String house_code) {
        this.house_code = house_code;
    }

    public String getId_check() {
        return id_check;
    }

    public void setId_check(String id_check) {
        this.id_check = id_check;
    }

    public String getHouse_name() {
        return house_name;
    }

    public void setHouse_name(String house_name) {
        this.house_name = house_name;
    }

    public ArrayList<MemberModel> getData_array() {
        return data_array;
    }

    public void setData_array(ArrayList<MemberModel> data_array) {
        this.data_array = data_array;
    }

    public String getMy_yn() {
        return my_yn;
    }

    public void setMy_yn(String my_yn) {
        this.my_yn = my_yn;
    }


    public String getHouse_idx() {
        return house_idx;
    }

    public void setHouse_idx(String house_idx) {
        this.house_idx = house_idx;
    }

    public ArrayList<MemberModel> getMate_array() {
        return mate_array;
    }

    public void setMate_array(ArrayList<MemberModel> mate_array) {
        this.mate_array = mate_array;
    }

    public ArrayList<MemberModel> getMy_schedule_array() {
        return my_schedule_array;
    }

    public void setMy_schedule_array(ArrayList<MemberModel> my_schedule_array) {
        this.my_schedule_array = my_schedule_array;
    }

    public ArrayList<MemberModel> getNote_array() {
        return note_array;
    }

    public void setNote_array(ArrayList<MemberModel> note_array) {
        this.note_array = note_array;
    }

    public String getMy_schedule_count() {
        return my_schedule_count;
    }

    public void setMy_schedule_count(String my_schedule_count) {
        this.my_schedule_count = my_schedule_count;
    }

    public String getMate_cnt() {
        return mate_cnt;
    }

    public void setMate_cnt(String mate_cnt) {
        this.mate_cnt = mate_cnt;
    }

    public String getSchedule_idx() {
        return schedule_idx;
    }

    public void setSchedule_idx(String schedule_idx) {
        this.schedule_idx = schedule_idx;
    }

    public String getSchedule_date() {
        return schedule_date;
    }

    public void setSchedule_date(String schedule_date) {
        this.schedule_date = schedule_date;
    }

    public String getPlan_idx() {
        return plan_idx;
    }

    public void setPlan_idx(String plan_idx) {
        this.plan_idx = plan_idx;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public String getSchedule_yn() {
        return schedule_yn;
    }

    public void setSchedule_yn(String schedule_yn) {
        this.schedule_yn = schedule_yn;
    }

    public String getNote_idx() {
        return note_idx;
    }

    public void setNote_idx(String note_idx) {
        this.note_idx = note_idx;
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

    public String getHouse_img() {
        return house_img;
    }

    public void setHouse_img(String house_img) {
        this.house_img = house_img;
    }

    public String getMember_new_pw() {
        return member_new_pw;
    }

    public void setMember_new_pw(String member_new_pw) {
        this.member_new_pw = member_new_pw;
    }

    public String getMember_confirm_pw() {
        return member_confirm_pw;
    }

    public void setMember_confirm_pw(String member_confirm_pw) {
        this.member_confirm_pw = member_confirm_pw;
    }

    public String getMember_leave_yn() {
        return member_leave_yn;
    }

    public void setMember_leave_yn(String member_leave_yn) {
        this.member_leave_yn = member_leave_yn;
    }

    public String getAlarm_hour() {
        return alarm_hour;
    }

    public void setAlarm_hour(String alarm_hour) {
        this.alarm_hour = alarm_hour;
    }

    public String getCoc_cnt() {
        return coc_cnt;
    }

    public void setCoc_cnt(String coc_cnt) {
        this.coc_cnt = coc_cnt;
    }

    public ArrayList<MemberModel> getPage_array() {
        return page_array;
    }

    public void setPage_array(ArrayList<MemberModel> page_array) {
        this.page_array = page_array;
    }
}
