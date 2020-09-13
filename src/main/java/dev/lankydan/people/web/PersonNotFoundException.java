package dev.lankydan.people.web;

import java.util.UUID;

public class PersonNotFoundException extends RuntimeException {

  public PersonNotFoundException(UUID id) {
    super("Person with " + id.toString() + " does not exist!");
  }
}
