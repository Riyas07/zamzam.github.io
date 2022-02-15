/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ZamZam.ZamZam.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author ELCOT
 */
@Document(collection = "createuser")
public class createUser {

    String username;
    String email;
    String password;
    String address;
    String pincode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    @Override
    public String toString() {
        return "createUser{" + "username=" + username + ", email=" + email + ", password=" + password + ", address=" + address + ", pincode=" + pincode + '}';
    }

}
