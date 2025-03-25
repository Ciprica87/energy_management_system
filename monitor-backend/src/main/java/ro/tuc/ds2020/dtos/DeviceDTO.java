package ro.tuc.ds2020.dtos;

import java.util.UUID;

public class DeviceDTO {
    private UUID id;
    private int maxConsumption;
    private UUID user_id;

    public DeviceDTO() {
    }

    // All-args constructor
    public DeviceDTO(UUID id, int maxConsumption, UUID user_id) {
        this.id = id;
        this.maxConsumption = maxConsumption;
        this.user_id = user_id;
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

    public UUID getUserID() {
        return user_id;
    }

    public void setUserID(UUID user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "DeviceDTO{" +
                "id=" + id +
                ", maxConsumption=" + maxConsumption +
                ", user_id=" + user_id +
                '}';
    }
}
