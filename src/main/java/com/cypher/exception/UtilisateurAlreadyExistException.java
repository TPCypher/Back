package com.cypher.exception;

import jakarta.persistence.EntityNotFoundException;

public class UtilisateurAlreadyExistException extends EntityNotFoundException {

    public UtilisateurAlreadyExistException() {
        super("Utilisateur déjà existant");
    }
}
