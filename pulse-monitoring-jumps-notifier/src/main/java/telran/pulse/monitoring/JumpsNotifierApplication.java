package telran.pulse.monitoring;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import telran.pulse.monitoring.dto.SensorJump;

import java.util.function.Consumer;

@SpringBootApplication
@Log4j2
public class JumpsNotifierApplication {

    public static void main(String[] args) {
        SpringApplication.run(JumpsNotifierApplication.class, args);
    }
    @Bean
    Consumer<SensorJump> criticalJumpsConsumer() {
        return this::jumpsProcessing;
    }

    private void jumpsProcessing(SensorJump sensorJump) {
        log.trace("received sensor id {} previous value {} current value {}",
                sensorJump.sensorId, sensorJump.previousValue, sensorJump.currentValue);
    }
}
