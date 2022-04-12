package telran.pulse.monitor.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import telran.pulse.monitor.entities.SensorDoc;

public interface SensorRepository extends MongoRepository<SensorDoc, ObjectId> {
}
