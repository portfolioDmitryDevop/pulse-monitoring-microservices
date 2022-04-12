package telran.pulse.monitoring.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table(name = "visits")
@NoArgsConstructor
@Getter
public class Visit {
    @Id
    int id;
    LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    Patient patient;

    public Visit(LocalDateTime date, Doctor doctor, Patient patient) {
        this.id = ThreadLocalRandom.current().nextInt(20, Integer.MAX_VALUE);//as workaround of existing visits
        this.date = date;
        this.doctor = doctor;
        this.patient = patient;
    }

}
