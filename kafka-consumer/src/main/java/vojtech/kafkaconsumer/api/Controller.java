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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vojtech.kafkaconsumer.entity.PersonEntity;
import vojtech.kafkaconsumer.mapper.PersonMapper;
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
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/kafka")
public class Controller {
    private final PersonMapper mapper = Mappers.getMapper(PersonMapper.class);

    public static class NoSuchIdFoundEception extends Exception {
        public NoSuchIdFoundEception(String errorMessage) {
            super(errorMessage);
        }
    }

    @Autowired
    PersonRepository personRepository;

    @Operation(summary = "Find entries with specified name",
            description = "Returns all entries with name matching the specified name")
    @GetMapping("/find")
    public ResponseEntity<String> findByName(@RequestParam String name) {

        List<PersonEntity> personList = personRepository.findByName(name);

        if (name.isEmpty()) {
            return ResponseEntity.badRequest().body("You did not provide a valid name");
        }
        else if (personList.isEmpty()) {
            return ResponseEntity.ok("Sorry, no person with name " + name + " has been found");
        }
        return ResponseEntity.ok("Found " + personList.size() + " entities matching the name " + name + ":" +
                prettyPrintNoName(personList));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ID found, entity fetched", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PersonEntity.class)) }),
            @ApiResponse(responseCode = "500", description = "Error occurred, invalid ID?", content = @Content) })
    @GetMapping("/find-person-by-id")
    public PersonEntity findPersonById(@RequestParam Long id) throws NoSuchIdFoundEception {

        Optional<PersonEntity> entity = personRepository.findById(id);

        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new NoSuchIdFoundEception("Oh no, where did you get that ID " + id + "? :O");
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
