package pt.isel.mds.sliperators;

import java.io.*;
import java.util.Spliterators;
import java.util.function.Consumer;

/**
 * Spliterator para produzir a sequência de linhas
 * obtidas do Reader recebido por parâmetro de construção
 */
public class LinesSpliterator extends Spliterators.AbstractSpliterator<String> {
    
    private BufferedReader reader;
    private boolean closed;


    public LinesSpliterator(Reader reader) {
        super(Long.MAX_VALUE, ORDERED | NONNULL);
        this.reader = new BufferedReader(reader);
    }


    public void close() {
        if (!closed) {
            try {
                reader.close();
                closed = true;
            } catch (IOException e) {
                closed = true;
            }
        }

    }

    /**
     * return the new line or null if it is already closed,
     * there are no more lines or an exception occurs when reading a line
     * @return
     */
    private String getNext() {
        if (closed) return null;
        try {
           return reader.readLine();
        }
        catch(IOException e) {
            close();
            return null;
        }

    }
    
    @Override
    public boolean tryAdvance(Consumer<? super String> action) {
        String line = getNext();

        if (line == null) return false;

        // line consuming
        action.accept(line);
        return true;
    }
}
