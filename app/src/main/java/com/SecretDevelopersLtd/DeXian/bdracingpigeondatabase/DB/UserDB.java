package com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.DB;

public class UserDB {

    String id;
    String name;
    String email;
    String pass;
    String phone;
    int point;
    int post;
    String register_date;
    String location;

    public UserDB() {
    }

    public UserDB(String id, String name, String email, String pass, String phone, int point, int post, String location, String register_date) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.phone = phone;
        this.point = point;
        this.post = post;
        this.register_date = register_date;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }

    public String getRegister_date() {
        return register_date;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }
}
