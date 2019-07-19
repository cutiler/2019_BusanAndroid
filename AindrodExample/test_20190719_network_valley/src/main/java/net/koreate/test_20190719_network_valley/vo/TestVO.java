package net.koreate.test_20190719_network_valley.vo;

public class TestVO {

    String id;
    String pw;

    public TestVO() {}

    public TestVO(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    @Override
    public String toString() {
        return "TestVO{" +
                "id='" + id + '\'' +
                ", pw='" + pw + '\'' +
                '}';
    }
}
