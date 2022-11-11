package com.udacity.jwdnd.course1.cloudstorage.model;

public class Files {
    private int fileid;
    private String filename;


    private String contenttype;
    private String filesize;
    private Integer userid;
    private byte[] filedata;

    public Files(Integer fileId, String filename, String contenttype, String filesize, Integer userid, byte[] filedata) {
        this.fileid = fileId;
        this.filename = filename;
        this.contenttype = contenttype;
        this.filesize = filesize;
        this.userid = userid;
        this.filedata = filedata;
    }

    public Files() {
    }

    public int getFileid() {
        return fileid;
    }

    public void setFileId(int fileId) {
        this.fileid = fileId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public byte[] getFiledata() {
        return filedata;
    }

    public void setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }
}
