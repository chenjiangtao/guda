package net.zoneland.ums.common.mysql.dataobject;

public class RecvidApp {
    private String recvid;

    private String appid;

    public String getRecvid() {
        return recvid;
    }

    public void setRecvid(String recvid) {
        this.recvid = recvid == null ? null : recvid.trim();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }
}