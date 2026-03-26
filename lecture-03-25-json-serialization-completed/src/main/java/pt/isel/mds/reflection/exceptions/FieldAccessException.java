package pt.isel.mds.reflection.exceptions;

public class FieldAccessException  extends RuntimeException {
    public FieldAccessException(String msg) {
        super(msg);
    }

    public FieldAccessException(Exception inner) {
        super(inner);
    }
}