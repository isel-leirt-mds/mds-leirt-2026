package pt.isel.mds.reflection.entities;


import pt.isel.mds.reflection.annotations.JsonName;


public class A implements X {
    private static int SV= 23;

    @JsonName(name="AlternateValue")
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

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != getClass()) return false;
        return value == ((A) o).value;
    }
}
