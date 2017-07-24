package com.example.manishacomputer.navigation;

/**
 * Created by MANISHA COMPUTER on 7/17/2017.
 */

public class Blood_detail {

    private String id;
    private String Username;
    private String Email;
    private String Password;
    private String Phone;
    private String Address;
    private String Gender;
    private String Image;
    private String Flag;

    public Blood_detail(String id, String username, String email, String password, String phone, String address, String gender, String image, String flag) {
        this.id = id;
        Username = username;
        Email = email;
        Password = password;
        Phone = phone;
        Address = address;
        Gender = gender;
        Image = image;
        Flag = flag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }


}
