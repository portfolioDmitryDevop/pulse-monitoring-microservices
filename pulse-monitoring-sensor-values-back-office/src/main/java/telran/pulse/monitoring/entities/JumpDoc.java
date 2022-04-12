package telran.pulse.monitoring.entities;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Document(collection = "jumps")
@Getter
public class JumpDoc {
    private int sensorId;
    private int previousValue;
    private int value;
    private LocalDateTime dateTime;
}
