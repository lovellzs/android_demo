package com.sz.testokhttp.bean;

/**
 * Created by apple on 2017/11/29.
 */

public class Notify {
    public enum NotifyType {
        NotifyNone(0), NotifyUnReadMsgCount(1), // 未读消息
        NotifyQuestion(2), // 提问
        NotifyGuest(3), // 访客
        NotifyFollowers(4), // 关注
        NotifyNewSender(5), // 最新发信的人
        NotifyPayResult(6), // 支付结果
        NotifyLoveShow(7), // 恋爱秀
        NotifyPayShow(8), // 支付
        NotifyUnReadVoipCount(11), //
        NotifyNormal(12), // 普通推送消息
        NotifyFeedLike(13), // 点赞
        NotifyVideoCall(14), // 视频通话
        NotifyAllUser(15);// 全局推送(透传)

        private int type = 0;

        NotifyType(int type) {
            this.type = type;
        }

        public int getValue() {
            return type;
        }
    }

    private int type;
    private int total_unaccepted_num;
    private int total_unaccepted_voip_num;

    private String distance;

    private int age;
    private int height;

    private String mod_username;

    private int result;
    private String uid = "";
    private String avatar;
    private String desc; // nickname
    private String nickname;
    private String title;// ticker text专用
    // *****************************************
    private int question_id;
    private String question;
    private String first_answer;
    private String second_answer;
    private String third_answer;
    private String payment_type;
    private String push_type; // notification(厂商通知转透传) transmission(透传)

    private boolean followed;// followed(是否被我关注)

    private String cancel;
    private String ok;

    // *****************************************

    private String first_desc;
    private String second_desc;
    private String third_desc;
    private long expire_at;
    private String notify_no;
    private String receiver_uid;

    private int sound = 0;
    private int vibrate = 0;
    private int light = 0;
    // ****************************************

    private String url;

    private int created_at;

    public String province; // # 省份

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getFirst_answer() {
        return first_answer;
    }

    public void setFirst_answer(String first_answer) {
        this.first_answer = first_answer;
    }

    public String getSecond_answer() {
        return second_answer;
    }

    public void setSecond_answer(String second_answer) {
        this.second_answer = second_answer;
    }

    public String getThird_answer() {
        return third_answer;
    }

    public void setThird_answer(String third_answer) {
        this.third_answer = third_answer;
    }

    public int getTotal_unaccepted_num() {
        return total_unaccepted_num;
    }

    public void setTotal_unaccepted_num(int total_unaccepted_num) {
        this.total_unaccepted_num = total_unaccepted_num;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public String getFirst_desc() {
        return first_desc;
    }

    public void setFirst_desc(String first_desc) {
        this.first_desc = first_desc;
    }

    public String getSecond_desc() {
        return second_desc;
    }

    public void setSecond_desc(String second_desc) {
        this.second_desc = second_desc;
    }

    public String getThird_desc() {
        return third_desc;
    }

    public void setThird_desc(String third_desc) {
        this.third_desc = third_desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getExpire_at() {
        return expire_at;
    }

    public void setExpire_at(long expire_at) {
        this.expire_at = expire_at;
    }

    public String getNotify_no() {
        return notify_no;
    }

    public void setNotify_no(String notify_no) {
        this.notify_no = notify_no;
    }

    public String getReceiver_uid() {
        return receiver_uid;
    }

    public void setReceiver_uid(String receiver_uid) {
        this.receiver_uid = receiver_uid;
    }

    public int getSound() {
        return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }

    public int getVibrate() {
        return vibrate;
    }

    public void setVibrate(int vibrate) {
        this.vibrate = vibrate;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPush_type() {
        return push_type;
    }

    public void setPush_type(String push_type) {
        this.push_type = push_type;
    }

    public int getTotal_unaccepted_voip_num() {
        return total_unaccepted_voip_num;
    }

    public void setTotal_unaccepted_voip_num(int total_unaccepted_voip_num) {
        this.total_unaccepted_voip_num = total_unaccepted_voip_num;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getMod_username() {
        return mod_username;
    }

    public void setMod_username(String mod_username) {
        this.mod_username = mod_username;
    }

    @Override
    public String toString() {
        return "Notify{" +
                "type=" + type +
                ", total_unaccepted_num=" + total_unaccepted_num +
                ", total_unaccepted_voip_num=" + total_unaccepted_voip_num +
                ", distance='" + distance + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", mod_username='" + mod_username + '\'' +
                ", result=" + result +
                ", uid='" + uid + '\'' +
                ", avatar='" + avatar + '\'' +
                ", desc='" + desc + '\'' +
                ", nickname='" + nickname + '\'' +
                ", title='" + title + '\'' +
                ", question_id=" + question_id +
                ", question='" + question + '\'' +
                ", first_answer='" + first_answer + '\'' +
                ", second_answer='" + second_answer + '\'' +
                ", third_answer='" + third_answer + '\'' +
                ", payment_type='" + payment_type + '\'' +
                ", push_type='" + push_type + '\'' +
                ", followed=" + followed +
                ", cancel='" + cancel + '\'' +
                ", ok='" + ok + '\'' +
                ", first_desc='" + first_desc + '\'' +
                ", second_desc='" + second_desc + '\'' +
                ", third_desc='" + third_desc + '\'' +
                ", expire_at=" + expire_at +
                ", notify_no='" + notify_no + '\'' +
                ", receiver_uid='" + receiver_uid + '\'' +
                ", sound=" + sound +
                ", vibrate=" + vibrate +
                ", light=" + light +
                ", url='" + url + '\'' +
                ", created_at=" + created_at +
                ", province='" + province + '\'' +
                '}';
    }
}