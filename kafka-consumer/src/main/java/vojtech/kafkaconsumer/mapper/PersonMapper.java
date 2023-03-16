package vojtech.kafkaconsumer.mapper;

import org.mapstruct.Mapper;
import vojtech.kafkaconsumer.entity.StandalonePersonEntity;
import vojtech.model.Person;

@Mapper
public interface PersonMapper {
    StandalonePersonEntity sourceToDestination(Person source);
    Person destinationToSource(StandalonePersonEntity destination);
}
