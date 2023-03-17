package vojtech.kafkaconsumer.mapper;

import org.mapstruct.Mapper;
import vojtech.kafkaconsumer.entity.PersonEntity;
import vojtech.model.Person;

@Mapper
public interface PersonMapper {
    PersonEntity sourceToDestination(Person source);
    Person destinationToSource(PersonEntity destination);
}
