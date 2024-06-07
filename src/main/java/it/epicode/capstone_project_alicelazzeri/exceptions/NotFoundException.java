package it.epicode.capstone_project_alicelazzeri.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(long id) {
        super("Item with id: " + id +  " not found.");
    }
}
