package vojtech.kafkaproducer.person2;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Person2Repository extends CrudRepository<Person2, Long> {

	List<Person2> findByFirstName(String firstName);

	Person2 findById(long id);
}
