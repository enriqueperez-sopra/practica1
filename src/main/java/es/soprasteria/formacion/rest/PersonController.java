package es.soprasteria.formacion.rest;

import es.soprasteria.formacion.dto.CreatePersonDto;
import es.soprasteria.formacion.dto.ResponsePersonDto;
import es.soprasteria.formacion.service.PersonService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/person")
public class PersonController {

  @Autowired
  private PersonService personService;

  @ApiOperation(value="Recupera listado de personas")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Usuarios recuperados correctamente"),
      @ApiResponse(code = 404, message = "No hay usuarios en base de datos"),
  })
  @GetMapping(value = "/", produces = {
      MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE
  })
  public ResponseEntity<List<ResponsePersonDto>> listPersons() {
    List<ResponsePersonDto> persons = personService.getAllPersons();
    if (persons == null || persons.isEmpty()) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(persons);
    }
  }


  @ApiOperation(value="Recupera una persona en función de su NIF")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Usuario recuperado correctamente"),
      @ApiResponse(code = 404, message = "El usuario no existe en base de datos"),
  })
  @GetMapping(value = "/{nif}", produces = {
      MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE
  })
  public ResponseEntity<ResponsePersonDto> findPerson(@ApiParam("NIF del usuario a recuperar") @PathVariable(name="nif") String nif) {
    ResponsePersonDto person = personService.getByNif(nif);
    if (person == null) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(person);
    }
  }

  @ApiOperation(value="Crea un usuario en base de datos")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Usuario creado en base de datos"),
      @ApiResponse(code = 400, message = "El usuario ya existe en base de datos"),
  })
  @PostMapping(value = "/", produces = {
      MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE
  })
  public ResponseEntity<ResponsePersonDto> createPerson(@RequestBody CreatePersonDto newPerson) {
    ResponsePersonDto person = personService.createPerson(newPerson);
    if (person == null) {
      return ResponseEntity.badRequest().build();
    } else {
      return ResponseEntity.ok(person);
    }
  }

  @ApiOperation(value="Actualiza un usuario en base de datos")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Usuario actualizado en base de datos"),
      @ApiResponse(code = 400, message = "El usuario no existe previamente en base de datos"),
  })
  @PutMapping(value = "/{nif}", produces = {
      MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE
  })
  public ResponseEntity<ResponsePersonDto> updatePerson(@PathVariable(name="nif") String nif,
      @RequestBody CreatePersonDto newPerson) {
    newPerson.setNif(nif);
    ResponsePersonDto person = personService.updatePerson(newPerson);
    if (person == null) {
      return ResponseEntity.badRequest().build();
    } else {
      return ResponseEntity.status(HttpStatus.CREATED).body(person);
    }
  }

  @ApiOperation(value="Elimina un usuario de base de datos a partir de su NIF")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Usuario eliminado de base de datos")
  })
  @DeleteMapping("/{nif}")
  public ResponseEntity updatePerson(@PathVariable(name="nif") String nif) {
    personService.deletePerson(nif);
    return ResponseEntity.noContent().build();
  }
}
