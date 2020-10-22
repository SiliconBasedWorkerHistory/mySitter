package com.wh.mysitter.bean;

public class Task {
    private String con;
    private Long createTime;
    private String deviceName;
    private int id;
    private boolean readDone;
    private String title;

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isReadDone() {
        return readDone;
    }

    public void setReadDone(boolean readDone) {
        this.readDone = readDone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
