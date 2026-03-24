package pt.isel.mds.reflection.entities;

public class A implements X {
    private static int SV= 23;
    private int value = 3;

    public A(int value) {
        this.value = value;
    }

    public A() {
       this(0);
    }

    public int getValue() { return value; }

    @Override
    public String toString() {
        return "v=" + value;
    }
}
