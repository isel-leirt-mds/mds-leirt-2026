package pt.isel.mds;

import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.stream.Stream;

public class StreamTests {
    @Test
    public void streamFromReaderLines() {
        var text = """
                first
                second
                third
                """;
        var reader = new StringReader(text);
        try( var lines = StreamUtils.lines(reader)) {
            lines
                .forEach(System.out::println);
        }
        
    }

    @Test
    public void fileLines() {
        String currentDir = System.getProperty("user.dir");
        System.out.println("Current dir using System:" + currentDir);
        var fileName = "build.gradle";

        try( var lines = StreamUtils.lines(fileName)) {
            lines.forEach(System.out::println);
        }

    }

    
    @Test
    public void spliteratorFromStream() {
        var stream = Stream.of(1,2,3);
        var streamIter = stream.spliterator();

        // try to get the stream spliterator again
        streamIter = stream.spliterator();
    }
}
