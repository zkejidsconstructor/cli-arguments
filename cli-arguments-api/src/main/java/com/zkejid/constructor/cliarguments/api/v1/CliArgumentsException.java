package com.zkejid.constructor.cliarguments.api.v1;

public class CliArgumentsException extends RuntimeException {

  public CliArgumentsException(String message) {
    super(message);
  }

  public CliArgumentsException(String message, Throwable cause) {
    super(message, cause);
  }
}
