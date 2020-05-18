package es.soprasteria.formacion.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponsePersonDto {
  private String nif;
  private String fullName;
  private Integer age;
}
