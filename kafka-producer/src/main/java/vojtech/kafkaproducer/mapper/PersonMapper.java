package vojtech.kafkaproducer.mapper;

import org.mapstruct.Mapper;
import vojtech.model.Person;

@Mapper
public interface PersonMapper {
    org.openapitools.model.Person sourceToDestination(Person source);
    Person destinationToSource(org.openapitools.model.Person destination);
}
