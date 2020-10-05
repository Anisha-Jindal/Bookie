package com.example.bookie;

public class model_allbook {
    private String name;
    private String author;
    private String image;
    private String created_date;

    private String email;
    private String mobile;
    private String college;
    private String address;

    public model_allbook(String name, String author, String image, String created_date, String email, String mobile, String college, String address) {
        this.name = name;
        this.author = author;
        this.image = image;
        this.created_date = created_date;
        this.email = email;
        this.mobile = mobile;
        this.college = college;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getImage() {
        return image;
    }

    public String getCreated_date() {
        return created_date;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCollege() {
        return college;
    }

    public String getAddress() {
        return address;
    }
}
