package vojtech.kafkaconsumer.entity;

import jakarta.persistence.*;

@Entity
@Table(name="person")
public class StandalonePersonEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return ("Person [ ID = " + getId()
                + ", Name = " + getName()
                + ", Age = " + getAge()
                + " ]");
    }

    @Column(name="name")
    private String name;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }


    @Column(name="age")
    private Integer age;

    public void setAge(Integer age){
        this.age = age;
    }

    public Integer getAge(){
        return this.age;
    }
}