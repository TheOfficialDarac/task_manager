package com.theofficialdarac.task_manager.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class User extends BaseObservable {
    private Integer ID;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean isVerified;

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Bindable

    public String getPassword() {
        return password;
    }


    private User() {
        this.ID = 0;
        this.username = "";
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.password = "";
    }

    public User(Integer ID, String username, String email, String password, String firstName, String lastName) {
        this.ID = ID;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public void setUserID(Integer id) {
        this.ID = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {

        if (isValidEmailAddress(email))
            this.email = email;
    }

    public Integer getUserID() {
        return ID;
    }
    @Bindable
    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public static boolean isValidEmailAddress(String email) {
        email = email.trim();
        if (email.length() > 100) return false;
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isValidUsername(String username) {
        if ((username == null) || username == "" || username.trim().length() > 70 || username.trim().length() < 3)
            return false;

        return true;
    }

    public static boolean isValidFirstName(String firstName) {
        if (firstName == null || firstName == "" || firstName.trim().length() > 100  || firstName.trim().length() < 3)
            return false;
        return true;
    }

    public static boolean isValidLastName(String lastName) {
        if (lastName == null || lastName == "" || lastName.trim().length() > 100)
            return false;
        return true;
    }

}