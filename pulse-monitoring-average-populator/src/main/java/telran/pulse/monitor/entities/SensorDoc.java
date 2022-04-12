package telran.pulse.monitor.entities;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import telran.pulse.monitoring.dto.Sensor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Document(collection = "average_values")
@Getter
public class SensorDoc {
    private int sensorID;
    private int value;
    private LocalDateTime dateTime;

    public static SensorDoc build(Sensor sensor){
        SensorDoc sensorDoc = new SensorDoc();
        sensorDoc.sensorID = sensor.id;
        sensorDoc.value = sensor.value;
        sensorDoc.dateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(sensor.timestamp),
                ZoneId.systemDefault()
        );
        return sensorDoc;
    }


}
