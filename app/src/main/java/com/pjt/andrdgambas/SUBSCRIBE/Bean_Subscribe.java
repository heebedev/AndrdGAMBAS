package com.pjt.andrdgambas.SUBSCRIBE;

public class Bean_Subscribe {

    //선언
    // Subslist
    private String subsSeqno;
    private String subsRegistDate;
    private String subsValidation;
    private String uSeqno;
    private String prdSeqno;
    private String term;
    private String releaseDay;
    private String prdTitle;
    private String prdPrice;
    private String prdImage;
    private String prdRegistDate;
    private String cgSeqno;
    private String chSeqno;
    private String chContext;
    private String chNickname;
    private String chImage;
    private String chValidation;
    private String createrUSeqno;
    private String cgName;

    // ContentsList 추가
    private String ctSeqno;
    private String ctTitle;
    private String ctContext;
    private String ctfile;
    private String ctRegistDate;
    private String ctValidation;
    private String ctReleaseDate;

    // contentview like 추가
    private String countlikecontents;
    private String checkmylikecontents;

    // 댓글 리스트 list 추가
    private String cmSeqno;
    private String cmcontext;
    private String cmRegistDate;
    private String cmValidation;
    private String uName;


    // Constructor : SubsList
    public Bean_Subscribe(String subsSeqno, String subsRegistDate, String subsValidation, String uSeqno, String prdSeqno, String term, String releaseDay, String prdTitle, String prdPrice, String prdImage, String prdRegistDate, String cgSeqno, String chSeqno, String chContext, String chNickname, String chImage, String chValidation, String createrUSeqno, String cgName) {
        this.subsSeqno = subsSeqno;
        this.subsRegistDate = subsRegistDate;
        this.subsValidation = subsValidation;
        this.uSeqno = uSeqno;
        this.prdSeqno = prdSeqno;
        this.term = term;
        this.releaseDay = releaseDay;
        this.prdTitle = prdTitle;
        this.prdPrice = prdPrice;
        this.prdImage = prdImage;
        this.prdRegistDate = prdRegistDate;
        this.cgSeqno = cgSeqno;
        this.chSeqno = chSeqno;
        this.chContext = chContext;
        this.chNickname = chNickname;
        this.chImage = chImage;
        this.chValidation = chValidation;
        this.createrUSeqno = createrUSeqno;
        this.cgName = cgName;
    }

    // Constructor : contetns List
    public Bean_Subscribe(String ctSeqno, String ctTitle, String ctContext, String ctfile, String ctRegistDate, String ctValidation, String prdSeqno, String ctReleaseDate) {

        this.ctSeqno = ctSeqno;
        this.ctTitle = ctTitle;
        this.ctContext = ctContext;
        this.ctfile = ctfile;
        this.ctRegistDate = ctRegistDate;
        this.ctValidation = ctValidation;
        this.prdSeqno = prdSeqno;
        this.ctReleaseDate = ctReleaseDate;
    }

    // Constructor : contetns View (좋아요 갯수 포함)
    public Bean_Subscribe(String ctSeqno, String ctTitle, String ctContext, String ctfile, String ctRegistDate, String ctValidation, String prdSeqno, String ctReleaseDate, String countlikecontents, String checkmylikecontents) {

        this.ctSeqno = ctSeqno;
        this.ctTitle = ctTitle;
        this.ctContext = ctContext;
        this.ctfile = ctfile;
        this.ctRegistDate = ctRegistDate;
        this.ctValidation = ctValidation;
        this.prdSeqno = prdSeqno;
        this.ctReleaseDate = ctReleaseDate;
        this.countlikecontents = countlikecontents;
        this.checkmylikecontents = checkmylikecontents;
    }

    // Constructor : 댓글리스트
    public Bean_Subscribe( String cmSeqno, String cmcontext, String cmRegistDate, String cmValidation, String ctSeqno, String uSeqno, String uName) {

        this.cmSeqno = cmSeqno;
        this.cmcontext = cmcontext;
        this.cmRegistDate = cmRegistDate;
        this.cmValidation = cmValidation;
        this.ctSeqno = ctSeqno;
        this.uSeqno = uSeqno;
        this.uName = uName;
    }

    // Method : G & S
    public String getSubsSeqno() {
        return subsSeqno;
    }

    public void setSubsSeqno(String subsSeqno) {
        this.subsSeqno = subsSeqno;
    }

    public String getSubsRegistDate() {
        return subsRegistDate;
    }

    public void setSubsRegistDate(String subsRegistDate) {
        this.subsRegistDate = subsRegistDate;
    }

    public String getSubsValidation() {
        return subsValidation;
    }

    public void setSubsValidation(String subsValidation) {
        this.subsValidation = subsValidation;
    }

    public String getuSeqno() {
        return uSeqno;
    }

    public void setuSeqno(String uSeqno) {
        this.uSeqno = uSeqno;
    }

    public String getPrdSeqno() {
        return prdSeqno;
    }

    public void setPrdSeqno(String prdSeqno) {
        this.prdSeqno = prdSeqno;
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

    public String getCgSeqno() {
        return cgSeqno;
    }

    public void setCgSeqno(String cgSeqno) {
        this.cgSeqno = cgSeqno;
    }

    public String getChSeqno() {
        return chSeqno;
    }

    public void setChSeqno(String chSeqno) {
        this.chSeqno = chSeqno;
    }

    public String getChContext() {
        return chContext;
    }

    public void setChContext(String chContext) {
        this.chContext = chContext;
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

    public String getChValidation() {
        return chValidation;
    }

    public void setChValidation(String chValidation) {
        this.chValidation = chValidation;
    }

    public String getCreaterUSeqno() {
        return createrUSeqno;
    }

    public void setCreaterUSeqno(String createrUSeqno) {
        this.createrUSeqno = createrUSeqno;
    }

    public String getCgName() {
        return cgName;
    }

    public void setCgName(String cgName) {
        this.cgName = cgName;
    }

    public String getCtSeqno() {
        return ctSeqno;
    }

    public void setCtSeqno(String ctSeqno) {
        this.ctSeqno = ctSeqno;
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

    public String getCtfile() {
        return ctfile;
    }

    public void setCtfile(String ctfile) {
        this.ctfile = ctfile;
    }

    public String getCtRegistDate() {
        return ctRegistDate;
    }

    public void setCtRegistDate(String ctRegistDate) {
        this.ctRegistDate = ctRegistDate;
    }

    public String getCtValidation() {
        return ctValidation;
    }

    public void setCtValidation(String ctValidation) {
        this.ctValidation = ctValidation;
    }

    public String getCtReleaseDate() {
        return ctReleaseDate;
    }

    public void setCtReleaseDate(String ctReleaseDate) {
        this.ctReleaseDate = ctReleaseDate;
    }

    public String getCountlikecontents() {
        return countlikecontents;
    }

    public void setCountlikecontents(String countlikecontents) {
        this.countlikecontents = countlikecontents;
    }

    public String getCheckmylikecontents() {
        return checkmylikecontents;
    }

    public void setCheckmylikecontents(String checkmylikecontents) {
        this.checkmylikecontents = checkmylikecontents;
    }

    public String getCmSeqno() {
        return cmSeqno;
    }

    public void setCmSeqno(String cmSeqno) {
        this.cmSeqno = cmSeqno;
    }

    public String getCmcontext() {
        return cmcontext;
    }

    public void setCmcontext(String cmcontext) {
        this.cmcontext = cmcontext;
    }

    public String getCmRegistDate() {
        return cmRegistDate;
    }

    public void setCmRegistDate(String cmRegistDate) {
        this.cmRegistDate = cmRegistDate;
    }

    public String getCmValidation() {
        return cmValidation;
    }

    public void setCmValidation(String cmValidation) {
        this.cmValidation = cmValidation;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }
}//-----
