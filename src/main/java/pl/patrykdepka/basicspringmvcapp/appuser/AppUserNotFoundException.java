package pl.patrykdepka.basicspringmvcapp.appuser;

public class AppUserNotFoundException extends RuntimeException {

    public AppUserNotFoundException(String message) {
        super(message);
    }
}
