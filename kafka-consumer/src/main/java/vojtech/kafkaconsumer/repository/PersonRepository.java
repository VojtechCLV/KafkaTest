package vojtech.kafkaconsumer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vojtech.kafkaconsumer.entity.PersonEntity;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<PersonEntity, Long> {

    List<PersonEntity> findByName(String name);

    PersonEntity findById(long id);
}
