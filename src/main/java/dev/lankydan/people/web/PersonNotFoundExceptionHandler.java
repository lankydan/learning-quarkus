package dev.lankydan.people.web;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PersonNotFoundExceptionHandler implements ExceptionMapper<PersonNotFoundException> {

  @Override
  public Response toResponse(PersonNotFoundException exception) {
    return Response.status(Response.Status.NOT_FOUND).build();
  }
}
