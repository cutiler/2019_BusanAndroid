package net.koreate.test_20190719_network_valley.vo;

public class BoardVO {

    int bno;
    String img;
    String content;
    String name;

    public BoardVO() {}

    public BoardVO(int bno, String img, String content, String name) {
        this.bno = bno;
        this.img = img;
        this.content = content;
        this.name = name;
    }

    public int getBno() {
        return bno;
    }

    public void setBno(int bno) {
        this.bno = bno;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BoardVO{" +
                "bno=" + bno +
                ", img='" + img + '\'' +
                ", content='" + content + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
