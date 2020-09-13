package dev.lankydan.core.web;

import dev.lankydan.core.db.PersistenceException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PersistenceExceptionHandler implements ExceptionMapper<PersistenceException> {

  @Override
  public Response toResponse(PersistenceException exception) {
    return Response.serverError().build();
  }
}
