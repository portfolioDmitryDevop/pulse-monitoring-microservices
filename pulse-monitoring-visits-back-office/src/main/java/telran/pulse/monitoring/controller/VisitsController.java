package telran.pulse.monitoring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import telran.pulse.monitoring.dto.DoctorData;
import telran.pulse.monitoring.dto.PatientData;
import telran.pulse.monitoring.dto.VisitData;
import telran.pulse.monitoring.service.VisitService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/visits")
public class VisitsController {
    @Autowired
    VisitService visitService;

    @PostMapping("/patients")
    PatientData addPatient(@RequestBody PatientData patient) {
        visitService.addPatient(patient.id, patient.name);
        return patient;
    }

    @PostMapping("/doctors")
    DoctorData addDoctor(@RequestBody @Valid DoctorData doctor) {
        visitService.addDoctor(doctor.email, doctor.name);
        return doctor;
    }

    @PostMapping
    Map<String, Object> addVisit(@RequestBody Map<String, Object> visit){
        visitService.addVisit((Integer) visit.get("patientId"),
                (String) visit.get("email"),
                LocalDateTime.parse((String) visit.get("date")));
        return visit;
    }

    @GetMapping("/{patientId}")
    private List<VisitData> getVisitsPatientDates(@PathVariable int patientId,
                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return visitService.getVisits(patientId, from, to);
    }

}
