package telran.pulse.monitoring.repository;

import org.springframework.data.repository.CrudRepository;
import telran.pulse.monitoring.entities.SensorList;

public interface SensorRepository extends CrudRepository<SensorList, Integer> {
}
