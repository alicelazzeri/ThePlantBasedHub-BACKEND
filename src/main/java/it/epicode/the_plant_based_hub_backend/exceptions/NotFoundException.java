package it.epicode.the_plant_based_hub_backend.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(long id) {
        super("Item with id: " + id +  " not found.");
    }
}
