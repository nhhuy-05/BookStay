package vn.edu.fpt.projectprm392.models;

public class BankAccount {
    private String accountId;
    private String accountName;
    private String cardNumber;
    private String expiredDate;
    private String cvv;

    public BankAccount() {
    }

    public BankAccount(String accountId, String accountName, String cardNumber, String expiredDate, String cvv) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.cardNumber = cardNumber;
        this.expiredDate = expiredDate;
        this.cvv = cvv;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
