package pt.isel.mds.reflection.entities;

import java.util.ArrayList;
import java.util.List;

public class C extends B {
    private double v2;
    private C next = null;

    public C(int v1, double v2) {
        super(v1);
        this.v2 = v2;
    }

    public C(int v1, double v2, C next) {
        this(v1, v2);
        this.next = next;
    }

    public C() {}

    public List<C> getChain() {
        var list = new ArrayList<C>();
        C curr = next;
        while (curr != null) {
            list.add(curr);
            curr = curr.next;
        }
        return list;
    }

    @Override
    public String toString() {
        return super.toString()  + ", v2=" + v2;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        C c1 = (C) o;
        if (v2 != c1.v2)  return false;
        if (next == null) return c1.next == null;
        return next.equals(c1.next);
    }

}
