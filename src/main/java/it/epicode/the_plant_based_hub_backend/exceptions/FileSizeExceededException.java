package it.epicode.the_plant_based_hub_backend.exceptions;


public class FileSizeExceededException extends RuntimeException {
    public FileSizeExceededException(String message) {
        super(message);
    }
}
