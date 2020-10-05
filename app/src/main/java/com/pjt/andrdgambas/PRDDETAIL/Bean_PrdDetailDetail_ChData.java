package com.pjt.andrdgambas.PRDDETAIL;

public class Bean_PrdDetailDetail_ChData {

    String chNickname, chImage, chContext, average;

    public Bean_PrdDetailDetail_ChData(String chNickname, String chImage, String chContext, String average) {
        this.chNickname = chNickname;
        this.chImage = chImage;
        this.chContext = chContext;
        this.average = average;
    }

    public String getChNickname() {
        return chNickname;
    }

    public void setChNickname(String chNickname) {
        this.chNickname = chNickname;
    }

    public String getChImage() {
        return chImage;
    }

    public void setChImage(String chImage) {
        this.chImage = chImage;
    }

    public String getChContext() {
        return chContext;
    }

    public void setChContext(String chContext) {
        this.chContext = chContext;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }
}
