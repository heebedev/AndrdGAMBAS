package com.pjt.andrdgambas.HOME;

public class HomeData {
    public static String USERID=null;
    public static final String CENIP="192.168.200.104";
    String prdSeq;
    String title;
    String nickname;
    String day;
    String term;
    String image;
    String price;
    String like;
    String subs;

    public HomeData(String prdSeq, String title, String nickname, String day, String term, String image, String price, String like, String subs) {
        this.prdSeq = prdSeq;
        this.title = title;
        this.nickname = nickname;
        this.day = day;
        this.term = term;
        this.image = image;
        this.price = price;
        this.like = like;
        this.subs = subs;
    }

    public String getPrdSeq() {
        return prdSeq;
    }

    public void setPrdSeq(String prdSeq) {
        this.prdSeq = prdSeq;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getSubs() {
        return subs;
    }

    public void setSubs(String subs) {
        this.subs = subs;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
