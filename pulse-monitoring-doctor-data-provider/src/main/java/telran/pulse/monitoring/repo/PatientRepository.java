package telran.pulse.monitoring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import telran.pulse.monitoring.entity.Doctor;
import telran.pulse.monitoring.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
}
