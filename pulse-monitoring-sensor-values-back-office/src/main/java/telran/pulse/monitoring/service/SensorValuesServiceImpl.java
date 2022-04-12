package telran.pulse.monitoring.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import telran.pulse.monitoring.entities.SensorDoc;
import telran.pulse.monitoring.repo.JumpsRepository;
import telran.pulse.monitoring.repo.SensorRepository;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class SensorValuesServiceImpl implements SensorValuesService{

    @Autowired
    JumpsRepository jumpsRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public int getAverageDates(int sensorId, LocalDateTime from, LocalDateTime to) {
        MatchOperation matchOperation = match(Criteria.where("dateTime").gte(from).lte(to)
                .and("sensorID").is(sensorId));
        GroupOperation groupOperation = group().avg("value").as("avg_value");
        Aggregation pipeline = newAggregation(matchOperation,groupOperation);
        double res = mongoTemplate.aggregate(pipeline, SensorDoc.class, Document.class).getUniqueMappedResult().getDouble("avg_value");
        return (int) res;
    }

    @Override
    public int getJumpsCountDates(int sensorId, LocalDateTime from, LocalDateTime to) {
        return (int) jumpsRepository.countBySensorIdAndDateTimeBetween(sensorId, from, to);
    }
}
