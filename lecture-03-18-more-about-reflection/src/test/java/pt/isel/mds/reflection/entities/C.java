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
}
