package vojtech.kafkaproducer;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vojtech.kafkaproducer.entity.PersonEntity;

@Repository
public interface PersonRepository extends CrudRepository<PersonEntity, Long> {

    @Query("select p from PersonEntity p where p.name = ?1")
    List<PersonEntity> findByName(String name);


    @Query("SELECT p FROM PersonEntity p")
    List<String> findNames();


    PersonEntity findById(long id);
}
