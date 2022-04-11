package telran.pulse.monitoring;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import telran.pulse.monitoring.dto.SensorJump;
import telran.pulse.monitoring.entities.JumpDoc;
import telran.pulse.monitoring.repo.JumpsRepository;

import java.util.function.Consumer;

@SpringBootApplication
@Log4j2
public class JumpsPopulatorApplication {
    @Autowired
    JumpsRepository jumpsRepository;
    public static void main(String[] args) {
        SpringApplication.run(JumpsPopulatorApplication.class, args);
    }
    @Bean
    Consumer<SensorJump> jumpsConsumer() {
        return this::jumpsProcessing;
    }

    private void jumpsProcessing(SensorJump sensorJump) {
        log.trace("received sensor id {} previous value {} current value {}",
                sensorJump.sensorId, sensorJump.previousValue, sensorJump.currentValue);
        jumpsRepository.insert(JumpDoc.build(sensorJump));
    }
}
