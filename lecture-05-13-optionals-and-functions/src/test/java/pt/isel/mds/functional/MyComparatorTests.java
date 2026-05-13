package pt.isel.mds.functional;

import pt.isel.mds.functional.data.Address;
import pt.isel.mds.functional.data.Person;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.lang.IO.println;
import static pt.isel.mds.functional.MyComparator.comparing;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyComparatorTests {
    
    static List<Person> db = List.of(
        new Person("Carlos",
            LocalDate.of(1980, 3, 2),
            new Address("Coimbra", "3050")),
        new Person("Maria",
            LocalDate.of(2005, 10, 25),
            new Address("Coimbra", "2050"))
    );

    private Person greater(Person p1, Person p2, MyComparator<Person> comparator) {
        return comparator.compare(p1,p2) > 0 ? p1 : p2;
    }

    @Test
    public void comparePersonsByNameTest() {
        Person carlos = db.get(0);
        Person maria = db.get(1);

        MyComparator<Person> byName =
            (p1,p2) -> p1.getName().compareTo(p2.getName());
        
        MyComparator<Person> byName2 =
            comparing(p -> p.getName());
        
        MyComparator<Person> byName3 =
            comparing(Person::getName);

        Person res = greater(carlos,maria, byName2);

        assertEquals(maria, res);
    }

    @Test
    public void comparePersonsByAddressTest() {
        Person carlos = db.get(0);
        Person maria = db.get(1);

        var byAddr = comparing(Person::getAddress,
                               comparing(Address::getCity).thenComparing(Address::getZipCode));

        Person res = greater(carlos,maria, byAddr);

        assertEquals(carlos, res);
    }

    @Test
    public void compareWithNullFirstTest() {
        var names = new ArrayList(
                            Arrays.stream(new String[] { null, "Luís", "Abel", null, "Marques", null})
                            .toList()
                    );

        Comparator<String> comp = (s1, s2) -> MyComparator.nullsFirst(String::compareTo).compare(s1, s2);
        names.sort(comp);
        println(names);
    }
}


