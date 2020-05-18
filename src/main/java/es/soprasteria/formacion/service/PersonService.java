package es.soprasteria.formacion.service;

import es.soprasteria.formacion.dto.CreatePersonDto;
import es.soprasteria.formacion.dto.ResponsePersonDto;
import java.util.List;

public interface PersonService {

  ResponsePersonDto getByNif(String nif);

  List<ResponsePersonDto> getAllPersons();

  ResponsePersonDto createPerson(CreatePersonDto newPerson);

  ResponsePersonDto updatePerson(CreatePersonDto personToUpdate);

  void deletePerson(String nif);
}
