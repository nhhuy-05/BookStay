package vn.edu.fpt.projectprm392.fragments.PlaceModel;

public class Place {

    private int resouceId;
    private String name;

    public Place(int resouceId, String name) {
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
