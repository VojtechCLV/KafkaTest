package vojtech.kafkaconsumer.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import vojtech.kafkaconsumer.entity.PersonEntity;
import vojtech.kafkaconsumer.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@OpenAPIDefinition(info = @Info(
        title = "Consumer API",
        description = "API for handling received and stored person information",
        version = "2.0",
        contact = @Contact(
                name = "Vojtech Moravec",
                email = "email@moravecvoj.tech"
        ),
        license = @License(
                name = "MIT Licence",
                url = "https://opensource.org/licenses/mit-license.php"
        )))
@Slf4j
@Path("/kafka")
public class Controller {

    @Autowired
    PersonRepository personRepository;

    @Operation(summary = "Find entries with specified name",
            description = "Returns all entries with name matching the specified name")
    @GET
    @Path("/find/{name}")
    public String findByName(@PathParam("name") String name) {

        List<PersonEntity> personList = personRepository.findByName(name);

        if (name.isEmpty()) {
            return "You did not provide a valid name";
        }
        else if (personList.isEmpty()) {
            return ("Sorry, no person with name " + name + " has been found");
        }
        return ("Found " + personList.size() + " entities matching the name " + name + ":" +
                prettyPrintNoName(personList));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ID found, entity fetched", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PersonEntity.class)) }),
            @ApiResponse(responseCode = "404", description = "Error occurred, invalid ID?", content = @Content) })
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/find-person-by-id")
    public Response findPersonById(@QueryParam("id") Long id) {

        Optional<PersonEntity> entity = personRepository.findById(id);

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

    String prettyPrintNoName(List<PersonEntity> list) {
        StringBuilder result = new StringBuilder();
        for (PersonEntity personEntity : list) {
            result.append("\n   ID = ").append(personEntity.getId()).append(", Age = ").append(personEntity.getAge());
        }
        return result.toString();
    }
}
