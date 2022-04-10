package telran.pulse.monitoring.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "patients")
@NoArgsConstructor @AllArgsConstructor
@Getter
public class Patient {
    @Id
    int id;
    @Column(name = "name")
    String name;
}
