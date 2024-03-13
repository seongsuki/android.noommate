package noommate.android.models;

import java.util.List;

public class CockModel extends BaseModel{
    private String member_nickname;
    private String token;
    private String title;
    private String msg;
    private String body;
    private List<String> registration_ids;
    private String sound;
    private Boolean mutable_content;
    private String priority;
    private String index;
    private CockModel notification;
    private Integer count;
    private String click_action;
    private CockModel message;
    private CockModel data;

    public CockModel getMessage() {
        return message;
    }

    public void setMessage(CockModel message) {
        this.message = message;
    }

    public String getMember_nickname() {
        return member_nickname;
    }

    public void setMember_nickname(String member_nickname) {
        this.member_nickname = member_nickname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getRegistration_ids() {
        return registration_ids;
    }

    public void setRegistration_ids(List<String> registration_ids) {
        this.registration_ids = registration_ids;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public Boolean getMutable_content() {
        return mutable_content;
    }

    public void setMutable_content(Boolean mutable_content) {
        this.mutable_content = mutable_content;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public CockModel getNotification() {
        return notification;
    }

    public void setNotification(CockModel notification) {
        this.notification = notification;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getClick_action() {
        return click_action;
    }

    public void setClick_action(String click_action) {
        this.click_action = click_action;
    }

    public CockModel getData() {
        return data;
    }

    public void setData(CockModel data) {
        this.data = data;
    }
}
