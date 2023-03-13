package vn.edu.fpt.projectprm392.models;

public class PaymentMethod {
    private int id;
    private String type;

    public PaymentMethod(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public PaymentMethod() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
