package ro.tuc.ds2020.dtos;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class MeasurementDTO {

    private UUID id;
    private int value;
    private Date timestamp;
    private UUID deviceId;

    // Constructors
    public MeasurementDTO() {
    }

    public MeasurementDTO(UUID id, int value, Date timestamp, UUID deviceId) {
        this.id = id;
        this.value = value;
        this.timestamp = timestamp;
        this.deviceId = deviceId;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    // toString method
    @Override
    public String toString() {
        return "MeasurementDTO{" +
                "id=" + id +
                ", value=" + value +
                ", timestamp=" + timestamp +
                ", deviceId=" + deviceId +
                '}';
    }

    // equals and hashCode based on id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MeasurementDTO)) return false;
        MeasurementDTO that = (MeasurementDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
