package pl.patrykdepka.basicspringmvcapp.event;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(String message) {
        super(message);
    }
}