package telran.pulse.monitoring.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visits")
@NoArgsConstructor
@Getter
public class Visit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    Patient patient;

    public Visit(LocalDateTime date, Doctor doctor, Patient patient) {
        this.date = date;
        this.doctor = doctor;
        this.patient = patient;
    }

}
