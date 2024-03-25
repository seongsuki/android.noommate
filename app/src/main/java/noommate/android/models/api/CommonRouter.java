package noommate.android.models.api;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import noommate.android.models.AlarmModel;
import noommate.android.models.BookModel;
import noommate.android.models.FaqModel;
import noommate.android.models.FileModel;
import noommate.android.models.HouseModel;
import noommate.android.models.MemberModel;
import noommate.android.models.NoteModel;
import noommate.android.models.NoticeModel;
import noommate.android.models.QnaModel;
import noommate.android.models.ScheduleModel;

public class CommonRouter extends BaseRouter {

  public static CommonAPI api() {
    return (CommonAPI) retrofit(CommonAPI.class);
  }

  public interface CommonAPI {
    /**
     * 회원 가입
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("join_v_1_0_0/member_reg_in")
    Call<MemberModel> member_reg_in(@FieldMap Map<String, Object> map);

    /**
     * 추가 정보 입력
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("sns_join_v_1_0_0/sns_member_reg_in")
    Call<MemberModel> sns_member_reg_in(@FieldMap Map<String, Object> map);


    /**
     * 하우스 들어가기
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("house_v_1_0_0/house_join_in")
    Call<MemberModel> house_join_in(@FieldMap Map<String, Object> map);


    /**
     * 아이디 중복체크
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("join_v_1_0_0/member_id_check")
    Call<MemberModel> member_id_check(@FieldMap Map<String, Object> map);

    /**
     * 비밀번호 변경
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("find_pw_to_email_v_1_0_0/member_pw_reset_send_email")
    Call<MemberModel> member_pw_reset_send_email(@FieldMap Map<String, Object> map);

    /**
     * 비밀번호 찾기
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("member_info_v_1_0_0/pw_mod_up")
    Call<MemberModel> pw_mod_up(@FieldMap Map<String, Object> map);


    /**
     * 아이디 중복체크
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("join_v_1_0_0/passwordemail_check_in")
    Call<MemberModel> passwordemail_check_in(@FieldMap Map<String, Object> map);

    /**
     * 회원 로그인
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("login_v_1_0_0/member_login")
    Call<MemberModel> member_login(@FieldMap Map<String, Object> map);

    /**
     * SNS 로그인
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("sns_login_v_1_0_0/sns_member_login")
    Call<MemberModel> sns_member_login(@FieldMap Map<String, Object> map);

    /**
     * 홈
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("house_v_1_0_0/house_list")
    Call<MemberModel> house_list(@FieldMap Map<String, Object> map);

    /**
     * 홈
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("house_v_1_0_0/house_mod_up")
    Call<MemberModel> house_mod_up(@FieldMap Map<String, Object> map);

    /**
     * 홈
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("house_v_1_0_0/today_schedule_end")
    Call<MemberModel> today_schedule_end(@FieldMap Map<String, Object> map);

    /**
     * 할일 리스트
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("house_v_1_0_0/today_schedule_list")
    Call<ScheduleModel> today_schedule_list(@FieldMap Map<String, Object> map);

    /**
     * 회원 로그아웃
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("logout_v_1_0_0/member_logout")
    Call<MemberModel> member_logout(@FieldMap Map<String, Object> map);

    /**
     * 하우스 만들기
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("house_v_1_0_0/house_reg_in")
    Call<HouseModel> house_reg_in(@FieldMap Map<String, Object> map);

    /**
     * 알림장 리스트
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("note_v_1_0_0/note_list")
    Call<NoteModel> note_list(@FieldMap Map<String, Object> map);

    /**
     * 알림장 작성
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("note_v_1_0_0/note_reg_in")
    Call<NoteModel> note_reg_in(@FieldMap Map<String, Object> map);


    /**
     * 알림장 수정
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("note_v_1_0_0/note_mod_up")
    Call<NoteModel> note_mod_up(@FieldMap Map<String, Object> map);

    /**
     * 알림장 상세
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("note_v_1_0_0/note_detail")
    Call<NoteModel> note_detail(@FieldMap Map<String, Object> map);

    /**
     * 알림장 차단
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("note_v_1_0_0/block_mod_up")
    Call<NoteModel> block_mod_up(@FieldMap Map<String, Object> map);

    /**
     * 알림장 신고
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("note_v_1_0_0/report_reg_in")
    Call<NoteModel> report_reg_in(@FieldMap Map<String, Object> map);

    /**
     * 알림장 신고
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("note_v_1_0_0/note_del")
    Call<NoteModel> note_del(@FieldMap Map<String, Object> map);

    /**
     * 할일 리스트
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("plan_v_1_0_0/schedule_list")
    Call<ScheduleModel> schedule_list(@FieldMap Map<String, Object> map);

    /**
     * 할일 삭제
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("plan_v_1_0_0/plan_del")
    Call<ScheduleModel> plan_del(@FieldMap Map<String, Object> map);

    /**
     * 할일 리스트
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("plan_v_1_0_0/plan_list")
    Call<ScheduleModel> plan_list(@FieldMap Map<String, Object> map);

    /**
     * 할일 상세
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("plan_v_1_0_0/plan_detail")
    Call<ScheduleModel> plan_detail(@FieldMap Map<String, Object> map);

    /**
     * 일정기록_달력
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("plan_v_1_0_0/schedule_date_list")
    Call<ScheduleModel> schedule_date_list(@FieldMap Map<String, Object> map);

    /**
     * 일정기록 일자별
     */
    @FormUrlEncoded
    @POST("plan_v_1_0_0/schedule_date_member_list")
    Call<ScheduleModel> schedule_date_member_list(@FieldMap Map<String, Object> map);

    /**
     * 할일 등록
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("plan_v_1_0_0/plan_reg_in")
    Call<ScheduleModel> plan_reg_in(@FieldMap Map<String, Object> map);

    /**
     * 할일 수정
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("plan_v_1_0_0/plan_mod_up")
    Call<ScheduleModel> plan_mod_up(@FieldMap Map<String, Object> map);

    /**
     * 당월 가계부
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("book_v_1_0_0/book_view")
    Call<BookModel> book_view(@FieldMap Map<String, Object> map);

    /**
     * 비용알리기
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("book_v_1_0_0/book_alarm")
    Call<BookModel> book_alarm(@FieldMap Map<String, Object> map);

    /**
     * 전체 가계부
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("book_v_1_0_0/book_list")
    Call<BookModel> book_list(@FieldMap Map<String, Object> map);

    /**
     * 메이트 리스트
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("book_v_1_0_0/mate_list")
    Call<MemberModel> mate_list(@FieldMap Map<String, Object> map);

    /**
     * 가계부 작성
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("book_v_1_0_0/book_reg_in")
    Call<BookModel> book_reg_in(@FieldMap Map<String, Object> map);

    /**
     * 가계부 수정
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("book_v_1_0_0/book_mod_up")
    Call<BookModel> book_mod_up(@FieldMap Map<String, Object> map);

    /**
     * 가계부 상세
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("book_v_1_0_0/book_detail")
    Call<BookModel> book_detail(@FieldMap Map<String, Object> map);



    /**
     * 내 정보
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("member_info_v_1_0_0/member_info_detail")
    Call<MemberModel> member_info_detail(@FieldMap Map<String, Object> map);

    /**
     * 내 정보
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("member_info_v_1_0_0/member_info_mod_up")
    Call<MemberModel> member_info_mod_up(@FieldMap Map<String, Object> map);

    /**
     * 하우스 나가기
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("house_v_1_0_0/house_out_up")
    Call<MemberModel> house_out_up(@FieldMap Map<String, Object> map);

    /**
     * 회원 탈퇴
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("member_out_v_1_0_0/member_out_up")
    Call<MemberModel> member_out_up(@FieldMap Map<String, Object> map);

    /**
     * 공지사항 리스트
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("notice_v_1_0_0/notice_list")
    Call<NoticeModel> notice_list(@FieldMap Map<String, Object> map);

    /**
     * 공지사항 상세
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("notice_v_1_0_0/notice_detail")
    Call<NoticeModel> notice_detail(@FieldMap Map<String, Object> map);

    /**
     * FAQ 리스트
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("faq_v_1_0_0/faq_list")
    Call<FaqModel> faq_list(@FieldMap Map<String, Object> map);

    /**
     * 알림 토글 리스트
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("alarm_v_1_0_0/alarm_toggle_view")
    Call<AlarmModel> alarm_toggle_view(@FieldMap Map<String, Object> map);

    /**
     * 알림 토글 설정
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("alarm_v_1_0_0/alarm_toggle_mod_up")
    Call<AlarmModel> alarm_toggle_mod_up(@FieldMap Map<String, Object> map);

    /**
     * 1:1 문의 목록
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("qa_v_1_0_0/qa_list")
    Call<QnaModel> qa_list(@FieldMap Map<String, Object> map);

    /**
     * 1:1 문의 상세
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("qa_v_1_0_0/qa_detail")
    Call<QnaModel> qa_detail(@FieldMap Map<String, Object> map);

    /**
     * 1:1 문의 등록
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("qa_v_1_0_0/qa_reg_in")
    Call<QnaModel> qa_reg_in(@FieldMap Map<String, Object> map);

    /**
     * 1:1 문의 삭제
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("qa_v_1_0_0/qa_del")
    Call<QnaModel> qa_del(@FieldMap Map<String, Object> map);


    /**
     * 파일 업로드
     *
     * @param file
     * @return
     */
    @Multipart
    @POST("common/fileUpload_action")
    Call<FileModel> fileUpload_action(@Part MultipartBody.Part file);


    /**
     * 알림 리스트
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("alarm_v_1_0_0/alarm_list")
    Call<AlarmModel> alarm_list(@FieldMap Map<String, Object> map);

    /**
     * 알림 삭제
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("alarm_v_1_0_0/alarm_del")
    Call<AlarmModel> alarm_del(@FieldMap Map<String, Object> map);


    /**
     * 알림 전체 삭제
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("alarm_v_1_0_0/alarm_all_del")
    Call<AlarmModel> alarm_all_del(@FieldMap Map<String, Object> map);

  }


}