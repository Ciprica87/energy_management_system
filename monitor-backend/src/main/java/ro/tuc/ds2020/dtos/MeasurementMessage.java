package ro.tuc.ds2020.dtos;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class MeasurementMessage {
    private UUID id;
    private int value;
    private Date timestamp;
    private UUID deviceId;

    public MeasurementMessage(UUID id, int value, Date timestamp, UUID deviceId) {
        this.id = id;
        this.value = value;
        this.timestamp = timestamp;
        this.deviceId = deviceId;
    }

    public MeasurementMessage(){

    }

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
}
