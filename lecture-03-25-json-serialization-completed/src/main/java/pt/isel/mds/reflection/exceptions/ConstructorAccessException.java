package pt.isel.mds.reflection.exceptions;

public class ConstructorAccessException extends RuntimeException {
    public ConstructorAccessException(String msg) {
        super(msg);
    }

    public ConstructorAccessException(Exception inner) {
        super(inner);
    }
}
