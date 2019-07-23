package net.koreate.test_20190722_socket.vo;

public class ChatMessage {

    private String who;
    private String msg;

    // 이하 getter setter toString

    @Override
    public String toString() {
        return "ChatMessage{" +
                "who='" + who + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
