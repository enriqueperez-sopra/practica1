package es.soprasteria.formacion.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "person")
public class PersonEntity {

  @Id
  @Column(name = "nif")
  private String nif;

  @Column(name = "name")
  private String name;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "year")
  private Integer year;
}
