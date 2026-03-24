package pt.isel.mds.reflection;

public class A implements X {
    private int v = 3;
    
    public int getV() { return v; }

    @Override
    public String toString() {
        return "v=" + v;
    }
}
