package com.pjt.andrdgambas.MYCHANNEL;

public class MyChannel {

    String channelSeqno;
    String channelContent;
    String channelNickname;
    String channelImage;
    String channelRegisterDate;
    String channelValidation;
    String productSeqno;
    String productTerm;
    String productReleaseDay;
    String productTitle;
    String productPrice;
    String productContent;
    String productImage;
    String productRegisterDate;
    String productValidation;
    String categorySeqno;


    public MyChannel(String productSeqno, String productTerm, String productReleaseDay, String productTitle, String productPrice, String productContent, String productImage, String productRegisterDate, String productValidation, String categorySeqno) {
        this.productSeqno = productSeqno;
        this.productTerm = productTerm;
        this.productReleaseDay = productReleaseDay;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.productContent = productContent;
        this.productImage = productImage;
        this.productRegisterDate = productRegisterDate;
        this.productValidation = productValidation;
        this.categorySeqno = categorySeqno;
    }

    public MyChannel(String channelSeqno, String channelContent, String channelNickname, String channelImage, String channelRegisterDate, String channelValidation) {
        this.channelSeqno = channelSeqno;
        this.channelContent = channelContent;
        this.channelNickname = channelNickname;
        this.channelImage = channelImage;
        this.channelRegisterDate = channelRegisterDate;
        this.channelValidation = channelValidation;
    }

    public String getProductSeqno() {
        return productSeqno;
    }

    public void setProductSeqno(String productSeqno) {
        this.productSeqno = productSeqno;
    }

    public String getProductTerm() {
        return productTerm;
    }

    public void setProductTerm(String productTerm) {
        this.productTerm = productTerm;
    }

    public String getProductReleaseDay() {
        return productReleaseDay;
    }

    public void setProductReleaseDay(String productReleaseDay) {
        this.productReleaseDay = productReleaseDay;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductContent() {
        return productContent;
    }

    public void setProductContent(String productContent) {
        this.productContent = productContent;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductRegisterDate() {
        return productRegisterDate;
    }

    public void setProductRegisterDate(String productRegisterDate) {
        this.productRegisterDate = productRegisterDate;
    }

    public String getProductValidation() {
        return productValidation;
    }

    public void setProductValidation(String productValidation) {
        this.productValidation = productValidation;
    }

    public String getCategorySeqno() {
        return categorySeqno;
    }

    public void setCategorySeqno(String categorySeqno) {
        this.categorySeqno = categorySeqno;
    }


    public String getChannelSeqno() {
        return channelSeqno;
    }

    public void setChannelSeqno(String channelSeqno) {
        this.channelSeqno = channelSeqno;
    }

    public String getChannelContent() {
        return channelContent;
    }

    public void setChannelContent(String channelContent) {
        this.channelContent = channelContent;
    }

    public String getChannelNickname() {
        return channelNickname;
    }

    public void setChannelNickname(String channelNickname) {
        this.channelNickname = channelNickname;
    }

    public String getChannelImage() {
        return channelImage;
    }

    public void setChannelImage(String channelImage) {
        this.channelImage = channelImage;
    }

    public String getChannelRegisterDate() {
        return channelRegisterDate;
    }

    public void setChannelRegisterDate(String channelRegisterDate) {
        this.channelRegisterDate = channelRegisterDate;
    }

    public String getChannelValidation() {
        return channelValidation;
    }

    public void setChannelValidation(String channelValidation) {
        this.channelValidation = channelValidation;
    }
}
