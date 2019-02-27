package com.snapshots.server.db.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
public class Snapshot {

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String fileName;
    @NotBlank
    private String timestamp;
    @NotBlank
    private String location;

    public Snapshot(String fileName, String timestamp, String location) {
        this.fileName = fileName;
        this.timestamp = timestamp;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
