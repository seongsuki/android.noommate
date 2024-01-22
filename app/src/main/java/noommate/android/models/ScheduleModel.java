package noommate.android.models;

import java.util.ArrayList;

public class ScheduleModel extends BaseListModel {
    // 하우스 키
    private String house_idx;
    // 오늘 날짜
    private String today;
    // 스캐줄
    private String schedule_w;
    // 타이틀
    private String plan_name;
    // 시작 날짜
    private String s_date;
    // 종료 날짜
    private String e_date;
    // 알림 여부
    private String alarm_yn;
    private String alarm_hour;
    private String item_array;
    // 일정 키
    private String plan_idx;
    // 할일 키
    private String plan_item_idx;
    private String schedule_date;
    // 닉네임
    private String member_nickname;
    // 캐릭터 얼굴
    private String member_role1;
    // 캐릭터 얼굴2
    private String member_role2;
    // 캐익터 얼굴3
    private String member_role3;
    private String members;
    // 나 여부
    private String my_yn;
    // 리스트
    private ArrayList<ScheduleModel> data_array;
    // 멤버 리스트
    private ArrayList<MemberModel> member_list;
    // 일정 리스트
    private ArrayList<ScheduleModel> plan_item_list;
    // 스캐쥴 멤버
    private ArrayList<MemberModel> schedule_member_list;
    private ArrayList<MemberModel> schedule_item_member_list;
    // 유저 리스트
    private ArrayList<MemberModel> select_member;
    private String member_arr;
    // 요일 리스트
    private String week_arr;


    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public String getPlan_idx() {
        return plan_idx;
    }

    public void setPlan_idx(String plan_idx) {
        this.plan_idx = plan_idx;
    }

    public String getPlan_item_idx() {
        return plan_item_idx;
    }

    public void setPlan_item_idx(String plan_item_idx) {
        this.plan_item_idx = plan_item_idx;
    }

    public String getMember_nickname() {
        return member_nickname;
    }

    public void setMember_nickname(String member_nickname) {
        this.member_nickname = member_nickname;
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

    public String getMy_yn() {
        return my_yn;
    }

    public void setMy_yn(String my_yn) {
        this.my_yn = my_yn;
    }

    public ArrayList<ScheduleModel> getData_array() {
        return data_array;
    }

    public void setData_array(ArrayList<ScheduleModel> data_array) {
        this.data_array = data_array;
    }

    public ArrayList<MemberModel> getMember_list() {
        return member_list;
    }

    public void setMember_list(ArrayList<MemberModel> member_list) {
        this.member_list = member_list;
    }


    public String getHouse_idx() {
        return house_idx;
    }

    public void setHouse_idx(String house_idx) {
        this.house_idx = house_idx;
    }

    public String getAlarm_yn() {
        return alarm_yn;
    }

    public void setAlarm_yn(String alarm_yn) {
        this.alarm_yn = alarm_yn;
    }

    public String getAlarm_hour() {
        return alarm_hour;
    }

    public void setAlarm_hour(String alarm_hour) {
        this.alarm_hour = alarm_hour;
    }

    public String getItem_array() {
        return item_array;
    }

    public void setItem_array(String item_array) {
        this.item_array = item_array;
    }


    public String getMember_arr() {
        return member_arr;
    }

    public void setMember_arr(String member_arr) {
        this.member_arr = member_arr;
    }

    public String getS_date() {
        return s_date;
    }

    public void setS_date(String s_date) {
        this.s_date = s_date;
    }

    public String getE_date() {
        return e_date;
    }

    public void setE_date(String e_date) {
        this.e_date = e_date;
    }

    public String getSchedule_date() {
        return schedule_date;
    }

    public void setSchedule_date(String schedule_date) {
        this.schedule_date = schedule_date;
    }

    public ArrayList<ScheduleModel> getPlan_item_list() {
        return plan_item_list;
    }

    public void setPlan_item_list(ArrayList<ScheduleModel> plan_item_list) {
        this.plan_item_list = plan_item_list;
    }

    public ArrayList<MemberModel> getSchedule_member_list() {
        return schedule_member_list;
    }

    public void setSchedule_member_list(ArrayList<MemberModel> schedule_member_list) {
        this.schedule_member_list = schedule_member_list;
    }

    public ArrayList<MemberModel> getSchedule_item_member_list() {
        return schedule_item_member_list;
    }

    public void setSchedule_item_member_list(ArrayList<MemberModel> schedule_item_member_list) {
        this.schedule_item_member_list = schedule_item_member_list;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }


    public String getWeek_arr() {
        return week_arr;
    }

    public void setWeek_arr(String week_arr) {
        this.week_arr = week_arr;
    }

    public String getSchedule_w() {
        return schedule_w;
    }

    public void setSchedule_w(String schedule_w) {
        this.schedule_w = schedule_w;
    }

    public ArrayList<MemberModel> getSelect_member() {
        return select_member;
    }

    public void setSelect_member(ArrayList<MemberModel> select_member) {
        this.select_member = select_member;
    }
}
