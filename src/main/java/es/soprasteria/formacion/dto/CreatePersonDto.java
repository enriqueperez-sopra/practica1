package es.soprasteria.formacion.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreatePersonDto {
  private String nif;
  private String name;
  private String lastName;
  private Integer year;
}
