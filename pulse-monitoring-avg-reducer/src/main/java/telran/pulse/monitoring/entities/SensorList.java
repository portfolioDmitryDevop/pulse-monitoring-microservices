package telran.pulse.monitoring.entities;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

@RedisHash
public class SensorList {
    @Id
    int id;
    @Getter
    List<Integer> listValue = new ArrayList<>();

    public SensorList(int id) {
        this.id = id;
    }
}
