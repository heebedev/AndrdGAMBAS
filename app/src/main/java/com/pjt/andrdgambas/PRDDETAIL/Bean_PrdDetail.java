package com.pjt.andrdgambas.PRDDETAIL;

public class Bean_PrdDetail {

    private String term, releaseDay, prdTitle, prdPrice, prdContext, prdImage, prdRegistDate, chName, cgName, subsValidation;

    public Bean_PrdDetail(String term, String releaseDay, String prdTitle, String prdPrice, String prdContext, String prdImage, String prdRegistDate, String chName, String cgName, String subsValidation) {
        this.term = term;
        this.releaseDay = releaseDay;
        this.prdTitle = prdTitle;
        this.prdPrice = prdPrice;
        this.prdContext = prdContext;
        this.prdImage = prdImage;
        this.prdRegistDate = prdRegistDate;
        this.chName = chName;
        this.cgName = cgName;
        this.subsValidation = subsValidation;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getReleaseDay() {
        return releaseDay;
    }

    public void setReleaseDay(String releaseDay) {
        this.releaseDay = releaseDay;
    }

    public String getPrdTitle() {
        return prdTitle;
    }

    public void setPrdTitle(String prdTitle) {
        this.prdTitle = prdTitle;
    }

    public String getPrdPrice() {
        return prdPrice;
    }

    public void setPrdPrice(String prdPrice) {
        this.prdPrice = prdPrice;
    }

    public String getPrdContext() {
        return prdContext;
    }

    public void setPrdContext(String prdContext) {
        this.prdContext = prdContext;
    }

    public String getPrdImage() {
        return prdImage;
    }

    public void setPrdImage(String prdImage) {
        this.prdImage = prdImage;
    }

    public String getPrdRegistDate() {
        return prdRegistDate;
    }

    public void setPrdRegistDate(String prdRegistDate) {
        this.prdRegistDate = prdRegistDate;
    }

    public String getChName() {
        return chName;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    public String getCgName() {
        return cgName;
    }

    public void setCgName(String cgName) {
        this.cgName = cgName;
    }

    public String getSubsValidation() {
        return subsValidation;
    }

    public void setSubsValidation(String subsValidation) {
        this.subsValidation = subsValidation;
    }
}
