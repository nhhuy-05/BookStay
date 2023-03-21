package vn.edu.fpt.projectprm392.models;

public class Place {

    private int resourceId;
    private String name;

    private String address;
    private String phone;
    private String email;

    private String roomType;
    private String statusRoom;


    public Place(int resourceId, String name) {
        this.resourceId = resourceId;
        this.name = name;
    }

    public Place(int resourceId, String name, String address, String roomType, String statusRoom) {
        this.resourceId = resourceId;
        this.name = name;
        this.address = address;
        this.roomType = roomType;
        this.statusRoom = statusRoom;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getStatusRoom() {
        return statusRoom;
    }

    public void setStatusRoom(String statusRoom) {
        this.statusRoom = statusRoom;
    }
}
