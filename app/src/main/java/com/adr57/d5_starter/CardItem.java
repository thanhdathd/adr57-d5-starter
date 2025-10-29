package com.adr57.d5_starter;

public class CardItem {
    private int id;
    private String imageUrl;
    private String title;
    private String description;
    private int likes;
    private boolean isLiked;

    public CardItem(int id, String imageUrl, String title, String description, int likes, boolean isLiked) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
        this.likes = likes;
        this.isLiked = isLiked;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }

    public boolean isLiked() { return isLiked; }
    public void setLiked(boolean liked) { isLiked = liked; }
}
