package com.cypher.exception;

import jakarta.persistence.EntityNotFoundException;

public class EntropyTooLowException extends EntityNotFoundException {

    public EntropyTooLowException() {
        super("L'entropie du mot de passe est trop faible");
    }
}
