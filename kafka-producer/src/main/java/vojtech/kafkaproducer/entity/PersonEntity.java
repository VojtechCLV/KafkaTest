package vojtech.kafkaproducer.entity;

import jakarta.persistence.*;
import vojtech.model.Person;


@Entity
@Table(name="person")
public class PersonEntity extends Person {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    private Integer age;

    public PersonEntity() {
    }

    public PersonEntity(String name, int age) {
        this.name = name;
        this.age = age;
    }


    public long getId() {
        return id;
    }

    @Override
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    @Column(name = "age", nullable = false)
    public int getAge() {
        return age;
    }
    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + ", age=" + age + "]";
    }
}
