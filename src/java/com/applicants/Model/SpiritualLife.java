package com.applicants.Model;

/**
 *
 * @author efsan1
 */
public class SpiritualLife {

    private int id;
    private String attend;
    private String whichChurch;
    private double attendDuration;
    private String ministriesIn;
    private String commitedJesus;
    private String commitedTest;
    private String viewBefSaved;
    private String hearGospel;
    private String gospelMess;
    private String backGround;

    public SpiritualLife() {
        id = 0;
        attend = null;
        whichChurch = null;
        attendDuration = 0;
        ministriesIn = null;
        commitedJesus = null;
        commitedTest = null;
        viewBefSaved = null;
        hearGospel = null;
        gospelMess = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAttend() {
        return attend;
    }

    public void setAttend(String attend) {
        this.attend = attend;
    }

    public String getWhichChurch() {
        return whichChurch;
    }

    public void setWhichChurch(String whichChurch) {
        this.whichChurch = whichChurch;
    }

    public double getAttendDuration() {
        return attendDuration;
    }

    public void setAttendDuration(double attendDuration) {
        this.attendDuration = attendDuration;
    }

    public String getMinistriesIn() {
        return ministriesIn;
    }

    public void setMinistriesIn(String ministriesIn) {
        this.ministriesIn = ministriesIn;
    }

    public String getCommitedJesus() {
        return commitedJesus;
    }

    public void setCommitedJesus(String commitedJesus) {
        this.commitedJesus = commitedJesus;
    }

    public String getCommitedTest() {
        return commitedTest;
    }

    public void setCommitedTest(String commitedTest) {
        this.commitedTest = commitedTest;
    }

    public String getViewBefSaved() {
        return viewBefSaved;
    }

    public void setViewBefSaved(String viewBefSaved) {
        this.viewBefSaved = viewBefSaved;
    }

    public String getHearGospel() {
        return hearGospel;
    }

    public void setHearGospel(String hearGospel) {
        this.hearGospel = hearGospel;
    }

    public String getGospelMess() {
        return gospelMess;
    }

    public void setGospelMess(String prgospelMess) {
        this.gospelMess = prgospelMess;
    }

    public String getBackGround() {
        return backGround;
    }

    public void setBackGround(String backGround) {
        this.backGround = backGround;
    }
    
    

}
