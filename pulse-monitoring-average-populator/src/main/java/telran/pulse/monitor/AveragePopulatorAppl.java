package telran.pulse.monitor;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import telran.pulse.monitor.entities.SensorDoc;
import telran.pulse.monitor.repo.SensorRepository;
import telran.pulse.monitoring.dto.Sensor;

import java.util.function.Consumer;

@SpringBootApplication
@Log4j2
public class AveragePopulatorAppl {

    @Autowired
    SensorRepository sensorRepository;

    public static void main(String[] args) {
        SpringApplication.run(AveragePopulatorAppl.class, args);
    }

    @Bean
    Consumer<Sensor> averageConsumer(){
        return this::averageProcessing;
    }

    private void averageProcessing(Sensor sensor) {
        log.debug("received sensor id {} with average value {}",
                sensor.id, sensor.value);
        sensorRepository.insert(SensorDoc.build(sensor));
    }


}
