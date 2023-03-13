package vn.edu.fpt.projectprm392.models;

public class District {
    private int id;
    private String name;

    public District(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public District() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
