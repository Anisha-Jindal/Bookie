package com.example.bookie;

public class model_ownbook {
    private  String id;
    private String name;
    private String author;
    private  String image;
    private String user_id;
    private String created_date;

    public model_ownbook(String id, String name, String author, String image, String user_id, String created_date) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.image = image;
        this.user_id = user_id;
        this.created_date = created_date;
    }

    public String getId() {
        return id;
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

    public String getUser_id() {
        return user_id;
    }

    public String getCreated_date() {
        return created_date;
    }
}
