package telran.pulse.monitoring.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;

@RedisHash
public class SensorLastValues {
    @Id
    int id;
    @Getter @Setter
    int lastValue;

    public SensorLastValues(int id) {
        this.id = id;
    }
}
