package vn.edu.fpt.projectprm392.models;

import java.util.Date;

public class BookingPayment {
    private String paymentId;
    private String bookingId;
    private Date paymentDate;
    private String status;
    private String accountId;

    public BookingPayment() {
    }

    public BookingPayment(String paymentId, String bookingId, Date paymentDate, String status, String accountId) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.paymentDate = paymentDate;
        this.status = status;
        this.accountId = accountId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
