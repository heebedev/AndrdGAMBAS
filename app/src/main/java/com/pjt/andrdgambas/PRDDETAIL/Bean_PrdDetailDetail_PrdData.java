package com.pjt.andrdgambas.PRDDETAIL;

public class Bean_PrdDetailDetail_PrdData {

    String prdTitle, prdContext, prdImage, chSeqno;

    public Bean_PrdDetailDetail_PrdData(String prdTitle, String prdContext, String prdImage, String chSeqno) {
        this.prdTitle = prdTitle;
        this.prdContext = prdContext;
        this.prdImage = prdImage;
        this.chSeqno = chSeqno;
    }

    public String getPrdTitle() {
        return prdTitle;
    }

    public void setPrdTitle(String prdTitle) {
        this.prdTitle = prdTitle;
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

    public String getChSeqno() {
        return chSeqno;
    }

    public void setChSeqno(String chSeqno) {
        this.chSeqno = chSeqno;
    }
}
