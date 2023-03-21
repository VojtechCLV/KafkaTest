package vojtech.kafkaproducer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vojtech.kafkaproducer.entity.PersonEntity;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    @Query("select p from PersonEntity p where p.name = ?1")
    List<PersonEntity> findByName(String name);

    @Query("SELECT p FROM PersonEntity p")
    List<String> findNames();

    @Query("SELECT p.name, p.age FROM PersonEntity p")
    List<String> findNameAge();


    PersonEntity findById(long id);
}
