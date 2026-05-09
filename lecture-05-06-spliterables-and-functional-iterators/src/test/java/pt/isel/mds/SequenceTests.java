package pt.isel.mds;

import org.junit.jupiter.api.Test;

import static java.lang.IO.println;
import static org.junit.jupiter.api.Assertions.*;

import static pt.isel.mds.sequences.Sequence.empty;
import static pt.isel.mds.sequences.Sequence.from;
import static pt.isel.mds.sequences.Sequence.from;

import java.util.List;



public class SequenceTests {
    @Test
    public void emptySequenceTest() {
        var seq = empty();
        
        assertFalse(seq.tryAdvance(t -> {
            fail("not supposed to be here!");
        }));
    }
    
    @Test
    public void fromIterableSequenceTest() {
        var elems = List.of(1,2,3);
        
        var seq = from(elems);
        
        while(seq.tryAdvance(t -> System.out.println(t)));
    }

    @Test
    public void sequenceToListTest() {
        var elems = List.of(1,2,3);

        var list = from(elems).toList();

        println(list);
    }

    @Test
    public void filterSequenceTest() {
        var elems = List.of(10, 11, 13, 15, 18, 20, 23);
        var expected = List.of(10, 18, 20);
        var seq = from(elems).filter(n -> n%2 == 0);

        assertEquals(expected, seq.toList());
    }

    @Test
    public void concatSequenceTest() {
        var seq1 = from(List.of(10, 11, 13, 15, 18, 20, 23));
        var seq2 = from(List.of(10, 18, 20));

        var expected = List.of(10, 11, 13, 15, 18, 20, 23, 10, 18, 20);
        var res = seq1.concat(seq2).toList();
        assertEquals(expected, res);
        println(res);
    }
}
