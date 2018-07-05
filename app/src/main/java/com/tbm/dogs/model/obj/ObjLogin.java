package com.tbm.dogs.model.obj;

public class ObjLogin {
    String hero_id;
    String fullname;
    String address;
    String mobile;
    String email;
    String balance;
    String image;
    String gcm_id;
    String service_name;
    String token;

    public ObjLogin(String hero_id, String fullname, String address, String mobile, String email, String balance, String image, String gcm_id, String service_name, String token) {
        this.hero_id = hero_id;
        this.fullname = fullname;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
        this.balance = balance;
        this.image = image;
        this.gcm_id = gcm_id;
        this.service_name = service_name;
        this.token = token;
    }

    public String getHero_id() {
        return hero_id;
    }

    public void setHero_id(String hero_id) {
        this.hero_id = hero_id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGcm_id() {
        return gcm_id;
    }

    public void setGcm_id(String gcm_id) {
        this.gcm_id = gcm_id;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
