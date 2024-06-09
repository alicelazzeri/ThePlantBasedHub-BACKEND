package it.epicode.capstone_project_alicelazzeri.exceptions;


public class FileSizeExceededException extends RuntimeException {
    public FileSizeExceededException(String message) {
        super(message);
    }
}
