package pt.isel.mds;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecordTests {

    private record Rational(int numerator, int denominator) {
        Rational add(Rational r1 ) {
            if (denominator == r1.denominator()) {
                return new Rational(numerator + r1.numerator() ,
                        r1.denominator());
            }
            else {
                int nd = denominator * r1.denominator();
                return new Rational(numerator*r1.denominator + r1.numerator()*denominator , nd);

            }
        }
    }

    @Test
    public void test1Rational() {
        Rational rational1 = new Rational(1, 2);
        Rational rational2 = new Rational(1, 2);

        System.out.println(rational1.numerator());
        System.out.println(rational1);
        assertEquals(rational1, rational2);

        var rational3 = rational1.add(rational2);
        System.out.println(rational3);

        Rational rational4 = new Rational(1, 3);
        System.out.println(rational4.add(rational1));

    }
}
