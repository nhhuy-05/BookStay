package vn.edu.fpt.projectprm392.models;

public class Room {
    private int Thumbnail;
    private String Name;
    private String address;

    public Room(int thumbnail, String name, String address) {
        Thumbnail = thumbnail;
        Name = name;
        this.address = address;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
