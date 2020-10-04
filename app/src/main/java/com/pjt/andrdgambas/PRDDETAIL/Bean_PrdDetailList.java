package com.pjt.andrdgambas.PRDDETAIL;

public class Bean_PrdDetailList {

    String rtitle,rcontext,rgrade;

    public Bean_PrdDetailList(String rtitle, String rcontext, String rgrade) {
        this.rtitle = rtitle;
        this.rcontext = rcontext;
        this.rgrade = rgrade;
    }

    public String getRtitle() {
        return rtitle;
    }

    public void setRtitle(String rtitle) {
        this.rtitle = rtitle;
    }

    public String getRcontext() {
        return rcontext;
    }

    public void setRcontext(String rcontext) {
        this.rcontext = rcontext;
    }

    public String getRgrade() {
        return rgrade;
    }

    public void setRgrade(String rgrade) {
        this.rgrade = rgrade;
    }

}
