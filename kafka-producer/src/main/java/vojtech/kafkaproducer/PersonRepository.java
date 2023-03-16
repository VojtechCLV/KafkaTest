package vojtech.kafkaproducer;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vojtech.kafkaproducer.entity.PersonEntity;

@Repository
public interface PersonRepository extends CrudRepository<PersonEntity, Long> {

    List<PersonEntity> findByName(String name);

    PersonEntity findById(long id);
}
