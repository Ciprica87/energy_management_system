package ro.tuc.ds2020.dtos;

import java.util.UUID;

public class DeviceMessage {
    private UUID id;
    private int maxConsumption;
    private UUID userId;
    private String request;

    public DeviceMessage(UUID id, int maxConsumption, UUID userId) {
        this.id = id;
        this.maxConsumption = maxConsumption;
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getMaxConsumption() {
        return maxConsumption;
    }

    public void setMaxConsumption(int maxConsumption) {
        this.maxConsumption = maxConsumption;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
