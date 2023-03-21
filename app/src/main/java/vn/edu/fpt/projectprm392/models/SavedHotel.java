package vn.edu.fpt.projectprm392.models;

import java.util.Date;

public class SavedHotel {
    private String userId;
    private Hotel hotel;
    private Date startDate;
    private Date endDate;
    private String location;

    public SavedHotel() {
    }

    public SavedHotel(String userId, Hotel hotel, Date startDate, Date endDate, String location) {
        this.userId = userId;
        this.hotel = hotel;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
