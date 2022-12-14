package telran.pulse.monitoring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import telran.pulse.monitoring.dto.DoctorPatientData;
import telran.pulse.monitoring.repo.*;

import static telran.pulse.monitoring.api.ApiConstants.*;

@RestController
public class DoctorDataProviderController {

    DoctorRepository doctorRepository;
    PatientRepository patientRepository;
    VisitRepository visitRepository;

    @Autowired
    public DoctorDataProviderController(DoctorRepository doctorRepository, PatientRepository patientRepository, VisitRepository visitRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.visitRepository = visitRepository;
    }

    @GetMapping(value=DOCTOR_PATIENT_DATA_URL+"{id}")
    DoctorPatientData getDoctorPatientData(@PathVariable("id") int id) {

        String email = visitRepository.getDoctorEmail(id);
        String doctorName = doctorRepository.getById(email).getName();
        String patientName = patientRepository.getById(id).getName();
        return new DoctorPatientData(email , doctorName ,patientName);
    }
}
