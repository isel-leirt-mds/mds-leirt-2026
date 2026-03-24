package pt.isel.mds.reflection.entities;

public class B extends A implements Y{
    public enum Size {
        SMALL,MEDIUM, LARGE
    }

    private Size type;

    public B(int v1) {
        super(v1);
    }

    public B(int v1, Size t) {
        super(v1);
        type = t;
    }

    public B() {}
}

