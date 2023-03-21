package vn.edu.fpt.projectprm392.models;

public class Item {

    private int resouceId;
    private int name;

    public Item(int resouceId, int name) {
        this.resouceId = resouceId;
        this.name = name;
    }

    public int getResouceId() {
        return resouceId;
    }

    public void setResouceId(int resouceId) {
        this.resouceId = resouceId;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }
}
