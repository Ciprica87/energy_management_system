package ro.tuc.ds2020.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Device {
    @Id
    /*@GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")*/
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "maxConsumption", nullable = false)
    private int maxConsumption;

    @Column(name = "user_id", nullable = false)
    private UUID user_id;

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

    public UUID getUserID(){
        return user_id;
    }

    public void setUserID(UUID userID){
        this.user_id = userID;
    }
}
