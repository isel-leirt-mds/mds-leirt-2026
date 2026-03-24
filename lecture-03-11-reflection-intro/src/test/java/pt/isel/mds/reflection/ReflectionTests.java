package pt.isel.mds.reflection;

import org.junit.jupiter.api.Test;


import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pt.isel.mds.reflection.ReflectionUtils.*;

public class ReflectionTests {

    private void showAllSuperInterfaces(Class<?> type ) {
        var superInterfaces = type.getInterfaces();
        for(var t : superInterfaces) {
            showAllSuperInterfaces(t);
            System.out.println(t.getName());

        }
    }

    @Test
    public void getAllSuperInterfacesOfList() {
        showAllSuperInterfaces(List.class);
    }
    @Test
    public void stringImplementsComparableTest() {
        assertTrue(implementsInterface(String.class, Comparable.class));
    }

    @Test
    public void instanceofBImplementsInterfaceXTest() {
        var b = new B();
        assertTrue(implementsInterface(b.getClass(), X.class));
    }

    @Test
    public void checkIfAisASuperClassOfC() {
        assertTrue(isSameOrSubClass(C.class, A.class));
    }

    @Test
    public void changeWithReflectionFieldVOfClassA()
            throws SecurityException,
            NoSuchFieldException,
            IllegalAccessException {
        Object o  = new A();

        Class<?> oClass = o.getClass();
        Field f = oClass.getDeclaredField("v");
        f.setAccessible(true);
        f.set(o, 10);

        System.out.println("v value = " + ((A) o).getV());
    }

    @Test
    public void setObjectFieldTest() {
        A a = new A();

        setObjectField(a, "v", 6);

        System.out.println(a);
    }

    @Test
    public void instanceOfTest() {
        Y a = new B();
        assertTrue(isInstanceOf(a, X.class));
    }

    @Test
    public void isSameOrSubClassWithEqualParametersTest() {
        assertTrue(isSameOrSubClass(B.class, B.class));
    }

    @Test
    public void isSameOrSubClassWithDirectDescendantTest() {
        assertTrue(isSameOrSubClass(B.class, A.class));
    }

    @Test
    public void isSameOrSubClassOnDifferentHierarchiesTest() {
        assertFalse(isSameOrSubClass(B.class, String.class));
    }

    @Test
    public void implementsInterfaceXTest() {
        System.out.println(X.class.getName());
        assertTrue(implementsInterface(B.class, X.class));
    }

}
