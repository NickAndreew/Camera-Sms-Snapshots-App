package cam.imitation.db.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

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

    private Long sendDelay;

    public Snapshot(String fileName, String timestamp, String location, long sendDelay) {
        this.fileName = fileName;
        this.timestamp = timestamp;
        this.location = location;
        this.sendDelay = Optional.ofNullable(sendDelay).orElse(5000L);
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

    public Long getSendDelay() {
        return sendDelay;
    }

    public void setSendDelay(Long sendDelay) {
        this.sendDelay = sendDelay;
    }
}
