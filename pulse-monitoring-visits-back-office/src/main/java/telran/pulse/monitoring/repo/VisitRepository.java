package telran.pulse.monitoring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import telran.pulse.monitoring.dto.VisitData;
import telran.pulse.monitoring.entity.Visit;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Integer> {

    @Query(value = "select doctor_id from visits where patient_id=:patientId order by date desc limit 1", nativeQuery = true)
    String getDoctorEmail(@Param("patientId") int patientId);

    //JPQL
    @Query("select v.patient.id as patientId, v.patient.name as patientName, v.doctor.name as doctorName, v.date as visitDate " +
            "from Visit v " +
            "where v.patient.id = :patientId " +
            "and v.date between :from and :to")
    List<VisitData> getVisitsPatientDates(@Param("patientId") int patientId,
                                          @Param("from") LocalDateTime from,
                                          @Param("to") LocalDateTime to);
}
