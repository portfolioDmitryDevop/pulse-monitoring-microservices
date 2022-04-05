package telran.pulse.monitoring.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;

@RedisHash
public class SensorRedis {
    @Id
    int id;
    ArrayList<Integer> values = new ArrayList<>();

    public SensorRedis(int id) {
        this.id = id;
    }

    public Integer getLastValue() {
        return values.get(values.size()-1);
    }

    public int addCurrentValue(int currentValue){
        values.add(currentValue);
        return values.size()-1;
    }
}
