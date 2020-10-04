package com.pjt.andrdgambas.MYCHANNEL;

public class Content {
    String ctTitle;
    String ctContext;
    String ctRegistDate;

    Content(String ctTitle, String ctContext, String ctRegistDate) {
        this.ctTitle = ctTitle;
        this.ctContext = ctContext;
        this.ctRegistDate = ctRegistDate;
    }

    public String getCtTitle() {
        return ctTitle;
    }

    public void setCtTitle(String ctTitle) {
        this.ctTitle = ctTitle;
    }

    public String getCtContext() {
        return ctContext;
    }

    public void setCtContext(String ctContext) {
        this.ctContext = ctContext;
    }

    public String getCtRegistDate() {
        return ctRegistDate;
    }

    public void setCtRegistDate(String ctRegistDate) {
        this.ctRegistDate = ctRegistDate;
    }
}
