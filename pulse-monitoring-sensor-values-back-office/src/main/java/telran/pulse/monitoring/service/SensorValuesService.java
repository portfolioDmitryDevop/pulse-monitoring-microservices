package telran.pulse.monitoring.service;

import java.time.LocalDateTime;

public interface SensorValuesService {

    int getAverageDates(int sensor_id, LocalDateTime from, LocalDateTime to);
    int getJumpsCountDates(int sensorId, LocalDateTime from, LocalDateTime to);

}
