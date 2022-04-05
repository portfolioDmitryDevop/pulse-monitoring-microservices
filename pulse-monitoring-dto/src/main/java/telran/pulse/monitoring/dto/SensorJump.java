package telran.pulse.monitoring.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SensorJump {
    public int sensorId;
    public int previousValue;
    public int currentValue;
    public long timestamp;

    public SensorJump(int sensorId, int previousValue, int currentValue) {
        this.sensorId = sensorId;
        this.previousValue = previousValue;
        this.currentValue = currentValue;
        timestamp = System.currentTimeMillis();
    }
}
