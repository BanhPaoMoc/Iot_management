package com.example.iot_management.models;

public class User {
    private String hoTen;
    private String email;
    private String soDienThoai;
    private String matKhau;
    private Boolean role;



    // Constructor mặc định (bắt buộc cho Firebase)
    public User() {}

    // Constructor có tham số
    public User(String hoTen, String email, String soDienThoai, String matKhau, Boolean role) {
        this.hoTen = hoTen;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.matKhau = matKhau;
        this.role = role;
    }

    // Constructor có tham số
    public User(String hoTen, String email, String soDienThoai, String matKhau) {
        this.hoTen = hoTen;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.matKhau = matKhau;
    }

    // Getters và Setters
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    public Boolean getRole() { return role = Boolean.valueOf(true ? "Admin" : "User"); }
    public void setRole(String role) { this.role = Boolean.valueOf(role); }
}
