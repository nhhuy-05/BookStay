package vn.edu.fpt.projectprm392.models;

import java.util.Date;

public class Booking {
    private int id;
    private int userId;
    private int hotelId;
    private Date in_date;
    private Date out_date;
    private int adult;
    private int child;
    private int total_price;
    private String status;
    private int payment_method_id;

    public Booking(int id, int userId, int hotelId, Date in_date, Date out_date, int adult, int child, int total_price, String status, int payment_method_id) {
        this.id = id;
        this.userId = userId;
        this.hotelId = hotelId;
        this.in_date = in_date;
        this.out_date = out_date;
        this.adult = adult;
        this.child = child;
        this.total_price = total_price;
        this.status = status;
        this.payment_method_id = payment_method_id;
    }

    public Booking() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public Date getIn_date() {
        return in_date;
    }

    public void setIn_date(Date in_date) {
        this.in_date = in_date;
    }

    public Date getOut_date() {
        return out_date;
    }

    public void setOut_date(Date out_date) {
        this.out_date = out_date;
    }

    public int getAdult() {
        return adult;
    }

    public void setAdult(int adult) {
        this.adult = adult;
    }

    public int getChild() {
        return child;
    }

    public void setChild(int child) {
        this.child = child;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(int payment_method_id) {
        this.payment_method_id = payment_method_id;
    }
}
