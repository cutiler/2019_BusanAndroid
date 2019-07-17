package net.koreate.test_20190716_adapterview.vo;

public class DogVO {

    private int _id; // CursorAdapter id
    private String kind;
    private String name;

    // getter / setter / toString
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DogVO{" +
                "_id=" + _id +
                ", kind='" + kind + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
