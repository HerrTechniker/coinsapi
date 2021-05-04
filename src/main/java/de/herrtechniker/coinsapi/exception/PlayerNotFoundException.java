package de.herrtechniker.coinsapi.exception;

public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException() {super("The current Player was not found!");}

    public PlayerNotFoundException(String errorMessage) {super(errorMessage);}
}
