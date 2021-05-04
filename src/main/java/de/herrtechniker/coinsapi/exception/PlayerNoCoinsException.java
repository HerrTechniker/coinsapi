package de.herrtechniker.coinsapi.exception;

public class PlayerNoCoinsException extends RuntimeException {

    public PlayerNoCoinsException() {super("The current Player has no Coins!");}

    public PlayerNoCoinsException(String errorMessage) {super(errorMessage);}
}
