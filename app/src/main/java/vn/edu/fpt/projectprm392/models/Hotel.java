package vn.edu.fpt.projectprm392.models;

public class Hotel {
    private int id;
    private String name;
    private int districtId;
    private String phone;
    private String image;

    private int price;

    public Hotel() {
    }

    public Hotel(int id, String name, int districtId, String phone, String image, int price) {
        this.id = id;
        this.name = name;
        this.districtId = districtId;
        this.phone = phone;
        this.image = image;
        this.price = price;
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

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
