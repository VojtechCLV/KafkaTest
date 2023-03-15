package vojtech.kafkaconsumer;


import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vojtech.kafkaconsumer.entity.PersonEntity;
//import vojtech.model.Person;

@Repository
public interface PersonRepository extends CrudRepository<PersonEntity, Long> {

    List<PersonEntity> findByName(String name);

    PersonEntity findById(long id);
}
