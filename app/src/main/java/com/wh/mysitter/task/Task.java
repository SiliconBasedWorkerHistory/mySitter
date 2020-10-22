package com.wh.mysitter.task;

import androidx.core.net.ConnectivityManagerCompat;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "con")
    private String Con;

    @ColumnInfo(name = "title")
    private String Title;

    @ColumnInfo(name = "createTime")
    private String CreateTime;

    @ColumnInfo(name = "modifyTime")
    private String ModifyTime;

    @ColumnInfo(name = "device")
    private String DeviceName;

    @ColumnInfo(name = "done", defaultValue = "false")
    private boolean ReadDone;

    @ColumnInfo(name = "localOnly",defaultValue = "true")
    private boolean LocalOnly;

    public Task() {
        this.CreateTime = String.valueOf(System.currentTimeMillis());
        this.ModifyTime = String.valueOf(System.currentTimeMillis());
        this.ReadDone = false;
        this.LocalOnly = true;
    }

    @Ignore
    public Task(int id) {
        this.id = id;
    }

    @Ignore
    public Task(int id,String con, String title, String deviceName,String createTime,String modifyTime,boolean isDone,boolean localOnly) {
        this.id = id;
        this.Con = con;
        this.Title = title;
        this.DeviceName = deviceName;
        this.CreateTime = createTime;
        this.ModifyTime = modifyTime;
        this.ReadDone = isDone;
        this.LocalOnly = localOnly;
    }

    @Ignore
    public Task(String con, String title, String deviceName) {
        this.Con = con;
        this.Title = title;
        this.DeviceName = deviceName;
        this.CreateTime = String.valueOf(System.currentTimeMillis());
        this.ModifyTime = String.valueOf(System.currentTimeMillis());
        this.ReadDone = false;
        this.LocalOnly = true;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCon(String con) {
        Con = con;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public void setModifyTime(String modifyTime) {
        ModifyTime = modifyTime;
    }

    public void setReadDone(boolean readDone) {
        ReadDone = readDone;
    }

    public void setLocalOnly(boolean localOnly) {
        LocalOnly = localOnly;
    }

    public int getId() {
        return id;
    }

    public String getCon() {
        return Con;
    }

    public String getTitle() {
        return Title;
    }

    public String getDeviceName() {
        return DeviceName;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public String getModifyTime() {
        return ModifyTime;
    }

    public boolean isReadDone() {
        return ReadDone;
    }

    public boolean isLocalOnly() {
        return LocalOnly;
    }

    public static class TaskSmall{
        private String con;

        private String title;

        public TaskSmall() {
        }

        public TaskSmall(String con, String title) {
            this.con = con;
            this.title = title;
        }

        public String getCon() {
            return con;
        }

        public void setCon(String con) {
            this.con = con;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}