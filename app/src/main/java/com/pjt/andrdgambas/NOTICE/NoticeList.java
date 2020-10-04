package com.pjt.andrdgambas.NOTICE;

public class NoticeList {

    String nName;
    String nDetailName;
    String nCode;

    public NoticeList() {
    }

    public NoticeList(String nName, String nDetailName, String nCode) {
        this.nName = nName;
        this.nDetailName = nDetailName;
        this.nCode = nCode;
    }

    public String getnName() {
        return nName;
    }

    public void setnName(String nName) {
        this.nName = nName;
    }

    public String getnDetailName() {
        return nDetailName;
    }

    public void setnDetailName(String nDetailName) {
        this.nDetailName = nDetailName;
    }

    public String getnCode() {
        return nCode;
    }

    public void setnCode(String nCode) {
        this.nCode = nCode;
    }
}
