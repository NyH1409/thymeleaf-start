package com.api.app.model.exception;

public class ForbiddenException extends ApiException {
  public ForbiddenException(String message) {
    super(message, ExceptionType.CLIENT_EXCEPTION);
  }
}
