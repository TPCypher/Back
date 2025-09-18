package com.cypher.exception;

import jakarta.persistence.EntityNotFoundException;

public class UtilisateurNotFoundException extends EntityNotFoundException {
    
    public UtilisateurNotFoundException() {
        super("Utilisateur non trouvé");
    }
}
