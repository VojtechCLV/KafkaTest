package vojtech.kafkaproducer.entity;

import jakarta.persistence.*;

@Entity
@Table(name="person")
public class PersonEntity {

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

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "age", nullable = false)
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + ", age=" + age + "]";
    }
}
