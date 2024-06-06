/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Admin
 */
public class User {

    private int userId;
    private int settingId;
    private String phone;
    private String email;
    private boolean gender;
    private String fullName;
    private String userName;
    private String password;
    private int status;
    private String note;
    private String thumbnailUrl;

    public User() {
    }

    public User(int settingId, String phone, String email, boolean gender, String fullName, String userName, String password, int status, String note, String thumbnailUrl) {
        this.settingId = settingId;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
        this.status = status;
        this.note = note;
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getUserId() {
        return userId;
    }

    public int getSettingId() {
        return settingId;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public boolean getGender() {
        return gender;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getStatus() {
        return status;
    }

    public String getNote() {
        return note;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setSettingId(int settingId) {
        this.settingId = settingId;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", settingId=" + settingId + ", phone=" + phone + ", email=" + email + ", gender=" + gender + ", fullName=" + fullName + ", userName=" + userName + ", password=" + password + ", status=" + status + ", note=" + note + ", thumbnailUrl=" + thumbnailUrl + '}';
    }

}
