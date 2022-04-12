package telran.pulse.monitoring.service;

import telran.pulse.monitoring.dto.VisitData;
import telran.pulse.monitoring.entity.Visit;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitService {
    void addPatient(int patientId, String name);
    void addDoctor(String email, String name);
    void addVisit(int patientId, String email, LocalDateTime dateTime);
    List<VisitData> getVisits(int patientId, LocalDateTime from, LocalDateTime to);
}
