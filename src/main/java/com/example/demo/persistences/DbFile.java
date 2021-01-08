package com.example.demo.persistences;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "files")
public class DbFile {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="id")
    private String id;

    @Column(name = "name")
    private String fileName;

    @Column(name = "type")
    private String fileType; //pdf, docx, png ?

    @Column(name = "task_id")
    private  int task_id;

    @Column(name = "upload_date", columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private Date uploadDate;

    @Lob
    @Column(name = "data")
    private byte[] data;

    public DbFile() {}

    public DbFile(String fileName, String fileType, byte[] data, int task_id, Date uploadDate) {
//        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
        this.task_id = task_id;
        this.uploadDate = uploadDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getFileType() {
        return fileType;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }
}
