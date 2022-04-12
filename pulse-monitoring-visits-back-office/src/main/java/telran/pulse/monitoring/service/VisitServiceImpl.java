package telran.pulse.monitoring.service;

import org.springframework.stereotype.Service;
import telran.pulse.monitoring.dto.VisitData;
import telran.pulse.monitoring.entity.Doctor;
import telran.pulse.monitoring.entity.Patient;
import telran.pulse.monitoring.entity.Visit;
import telran.pulse.monitoring.repo.DoctorRepository;
import telran.pulse.monitoring.repo.PatientRepository;
import telran.pulse.monitoring.repo.VisitRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VisitServiceImpl implements VisitService{

    DoctorRepository doctorRepository;
    PatientRepository patientRepository;
    VisitRepository visitRepository;

    public VisitServiceImpl(DoctorRepository doctorRepository, PatientRepository patientRepository, VisitRepository visitRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.visitRepository = visitRepository;
    }

    @Override
    @Transactional
    public void addPatient(int patientId, String name) {
        if (patientRepository.existsById(patientId)){
            throw new IllegalArgumentException("Duplicate patient ID");
        }
        patientRepository.save(new Patient(patientId, name));
    }

    @Override
    @Transactional
    public void addDoctor(String email, String name) {
        if (doctorRepository.existsById(email)){
            throw new IllegalArgumentException("Duplicate doctor email");
        }
        doctorRepository.save(new Doctor(email, name));
    }

    @Override
    @Transactional
    public void addVisit(int patientId, String email, LocalDateTime dateTime) {
        Patient patient = patientRepository.findById(patientId).orElse(null);
        if (patient == null) {
            throw new IllegalArgumentException(String.format("patient with id %d not found",
                    patientId));
        }
        Doctor doctor = doctorRepository.findById(email).orElse(null);
        if (doctor == null) {
            throw new IllegalArgumentException(String.format("Doctor with email %s doesn't exist",
                    email));
        }
        Visit visit = new Visit(dateTime, doctor, patient);
        visitRepository.save(visit);
    }

    @Override
    public List<VisitData> getVisits(int patientId, LocalDateTime from, LocalDateTime to) {
        return visitRepository.getVisitsPatientDates(patientId, from, to);
    }
}
