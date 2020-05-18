package es.soprasteria.formacion.service;

import es.soprasteria.formacion.dao.PersonRepository;
import es.soprasteria.formacion.dto.CreatePersonDto;
import es.soprasteria.formacion.dto.ResponsePersonDto;
import es.soprasteria.formacion.entity.PersonEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

  @Autowired
  private PersonRepository personRepository;

  @Override
  public ResponsePersonDto getByNif(String nif) {
    PersonEntity entity = personRepository.findById(nif).orElse(null);
    if (entity == null) {
      return null;
    } else {
      ResponsePersonDto dto = ResponsePersonDto.builder()
          .nif(entity.getNif())
          .fullName(entity.getName() + " " + entity.getLastName())
          .age(LocalDate.now().getYear() - entity.getYear())
          .build();
      return dto;
    }
  }

  @Override
  public List<ResponsePersonDto> getAllPersons() {
    List<PersonEntity> entities = personRepository.findAll();
    List<ResponsePersonDto> personDtos = entities.stream()
        .map(personEntity -> ResponsePersonDto.builder()
            .nif(personEntity.getNif())
            .fullName(personEntity.getName() + " " + personEntity.getLastName())
            .age(LocalDate.now().getYear() - personEntity.getYear())
            .build())
        .collect(Collectors.toList());
    return personDtos;
  }

  @Override
  public ResponsePersonDto createPerson(CreatePersonDto newPerson) {
    if (personRepository.existsById(newPerson.getNif())) {
      log.info("El usuario existe en base de datos.");
      return null;
    } else {
      return savePerson(newPerson);
    }
  }

  @Override
  public ResponsePersonDto updatePerson(CreatePersonDto personToUpdate) {
    if (!personRepository.existsById(personToUpdate.getNif())) {
      log.info("El usuario no existe en base de datos.");
      return null;
    } else {
      return savePerson(personToUpdate);
    }
  }

  @Override
  public void deletePerson(String nif) {
    personRepository.deleteById(nif);
  }

  private ResponsePersonDto savePerson(CreatePersonDto personToSave) {
    PersonEntity personEntity = new PersonEntity();
    personEntity.setNif(personToSave.getNif());
    personEntity.setName(personToSave.getName());
    personEntity.setLastName(personToSave.getLastName());
    personEntity.setYear(personToSave.getYear());

    PersonEntity persistedEntity = personRepository.save(personEntity);

    ResponsePersonDto personDto = ResponsePersonDto.builder()
        .nif(persistedEntity.getNif())
        .fullName(persistedEntity.getName() + " " + persistedEntity.getLastName())
        .age(LocalDate.now().getYear() - persistedEntity.getYear())
        .build();
    return personDto;
  }
}
