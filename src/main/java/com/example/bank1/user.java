package com.example.bank1;

public class user {
    private String time="";
    private String from="";
    private String to="";
    private Double amount=0.0;
    private String acc_no="";
    private String address="";
    private String email="";
    private String mobile="";
    private String username="";

    public user(String acc_no, String address, String email, String mobile, String username) {
        this.acc_no = acc_no;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
        this.username = username;
    }

    public user(String time, String from, String to, Double amount) {
        this.time = time;
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAcc_no() {
        return acc_no;
    }

    public void setAcc_no(String acc_no) {
        this.acc_no = acc_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
