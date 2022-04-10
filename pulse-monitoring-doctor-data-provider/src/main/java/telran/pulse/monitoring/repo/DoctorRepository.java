package telran.pulse.monitoring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import telran.pulse.monitoring.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, String> {
}
