package vojtech.kafkaconsumer.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import vojtech.kafkaconsumer.entity.PersonEntity;
import vojtech.kafkaconsumer.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Path("/kafka")
public class Controller {

    @Autowired
    PersonRepository personRepository;

    @GET
    @Path("/find/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByName(@PathParam("name") String name) {

        List<PersonEntity> personList = personRepository.findByName(name);

        if (name.isEmpty()) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("You have not entered a name to search for")
                    .build();
        }
        else if (personList.isEmpty()) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("The name you're looking for has not been found")
                    .build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(personList)
                .build();
    }

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/find-person-by-id")
    public Response findPersonById(@QueryParam("id") Long id) {

        Optional<PersonEntity> entity = personRepository.findById(id);

        log.info("Test");

        if (entity.isPresent()) {
            return Response
                    .status(Response.Status.OK)
                    .entity(entity.get())
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("The ID you have requested could not be found")
                    .build();
        }
    }
}
