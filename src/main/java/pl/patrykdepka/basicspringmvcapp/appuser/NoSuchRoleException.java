package pl.patrykdepka.basicspringmvcapp.appuser;

public class NoSuchRoleException extends RuntimeException {

    public NoSuchRoleException(String message) {
        super(message);
    }
}
