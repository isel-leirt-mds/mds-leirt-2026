package pt.isel.mds;

//import pt.isel.mpd.more_streams.sliperators.LinesSpliterator;

import pt.isel.mds.sliperators.LinesSpliterator;

import java.io.Reader;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class StreamUtils {

    /** cria e retorna Stream que produz a sequência e linhas
     * do reader passado por parâmetro
     * @param reader
     * @return
     */
    public static Stream<String> lines(Reader reader) {
        var spliterator = new LinesSpliterator(reader);
        var stream = StreamSupport.stream(spliterator, false);
         return stream.onClose(() -> spliterator.close());
    }

    /** cria e retorna Stream que produz a sequência e linhas
     * presentes no ficheiro de nome "fileName"
     * A implementação deve garantir que o ficheiro só é aberto
     * quando a Stream for consumida
     * @param fileName
     * @return
     */
    public static Stream<String> lines(String fileName) {
        // TODO
        return null;
    }
}
