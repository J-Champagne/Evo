package ca.uqam.latece.evo.server.core.exceptions;

public class StorageFileNotFoundException extends RuntimeException {
  public StorageFileNotFoundException(String message) {
    super(message);
  }
}
