package vojtech.kafkaconsumer;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vojtech.kafkaconsumer.entity.StandalonePersonEntity;

import java.util.List;

@Repository
public interface StandalonePersonRepository extends CrudRepository<StandalonePersonEntity, Long> {

    List<StandalonePersonEntity> findByName(String name);

    StandalonePersonEntity findById(long id);
}
