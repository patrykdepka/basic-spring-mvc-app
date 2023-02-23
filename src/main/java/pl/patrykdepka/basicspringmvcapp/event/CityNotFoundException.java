package pl.patrykdepka.basicspringmvcapp.event;

public class CityNotFoundException extends RuntimeException {

    public CityNotFoundException(String message) {
        super(message);
    }
}