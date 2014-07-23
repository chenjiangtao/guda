package net.zoneland.ums.common.mysql.dataobject;

public class InReadyKey {
    private String batchno;

    private Integer serialno;

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno == null ? null : batchno.trim();
    }

    public Integer getSerialno() {
        return serialno;
    }

    public void setSerialno(Integer serialno) {
        this.serialno = serialno;
    }
}