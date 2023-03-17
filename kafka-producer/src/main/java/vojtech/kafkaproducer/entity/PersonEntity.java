package vojtech.kafkaproducer.entity;

import jakarta.persistence.*;
import vojtech.model.Person;


@Entity
@Table(name="person")
public class PersonEntity extends Person {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    @Column(name="name")
    private String name;

    @Column(name="age")
    private Integer age;
}
