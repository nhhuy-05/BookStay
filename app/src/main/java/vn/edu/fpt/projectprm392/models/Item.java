package vn.edu.fpt.projectprm392.models;

public class Item {

    private int resouceId;
    private String name;

    public Item(int resouceId, String name) {
        this.resouceId = resouceId;
        this.name = name;
    }

    public int getResouceId() {
        return resouceId;
    }

    public void setResouceId(int resouceId) {
        this.resouceId = resouceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
