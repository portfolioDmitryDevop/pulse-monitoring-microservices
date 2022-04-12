package telran.pulse.monitoring.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "doctors")
@NoArgsConstructor @AllArgsConstructor
@Getter
public class Doctor {
    @Id
    String email;
    @Column(name = "name")
    String name;

}
