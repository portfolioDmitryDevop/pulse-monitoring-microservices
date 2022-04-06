package telran.pulse.monitoring.service;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;
import telran.pulse.monitoring.dto.Sensor;
import telran.pulse.monitoring.dto.SensorJump;
import telran.pulse.monitoring.entities.SensorRedis;
import telran.pulse.monitoring.repo.SensorRepository;

import java.util.*;
import java.util.function.Consumer;

@Service
@ManagedResource
@Slf4j
public class AnalyserService {


    @Autowired
    private StreamBridge streamBridge;
    @Autowired
    private SensorRepository sensorRepository;

    @Value("${app.jump.threshold:50}")
    int jumpPercentThreshold;

    @ManagedOperation
    public int getJumpPercentThreshold() {
        return jumpPercentThreshold;
    }

    @ManagedOperation
    public void setJumpPercentThreshold(int jumpPercentThreshold) {
        this.jumpPercentThreshold = jumpPercentThreshold;
    }

    @Value("${app.critical.threshold:100}")
    int criticalPercentThreshold;

    @Bean
    Consumer<Sensor> pulseConsumer() {
        return this::pulseProcessing;
    }

    private void pulseProcessing(Sensor sensor) {
        log.trace("Received sensor id {}; value {} ", sensor.id, sensor.value);
        SensorRedis sensorRedis = sensorRepository.findById(sensor.id).orElse(null);
        if (sensorRedis == null) {
            log.debug("for sensor id {} not found record in redis", sensor.id);
            sensorRedis = new SensorRedis(sensor.id);
        } else {
            int lastValue = sensorRedis.getLastValue();
            int delta = Math.abs(lastValue - sensor.value);
            double percent = (double) delta / lastValue * 100;
            if (percent > jumpPercentThreshold) {
                log.debug("sensor id {} has values jump {}", sensor.id, delta);
                SensorJump sensorJump = new SensorJump(sensor.id, lastValue, sensor.value);
                streamBridge.send("jumps-out-0", sensorJump);
                if (percent > criticalPercentThreshold) {
                    log.debug("sensor id {} has critical values jump {}", sensor.id, delta);
                    streamBridge.send("critical-jumps-out-0", sensorJump);
                }
            }
        }
        sensorRedis.addCurrentValue(sensor.value);
        sensorRepository.save(sensorRedis);

//        System.out.printf("sequence number %d, sensor id %d, waiting time %d\n",
//                sensor.seqNum,
//                sensor.id,
//                System.currentTimeMillis() - sensor.timestamp);
    }


}
