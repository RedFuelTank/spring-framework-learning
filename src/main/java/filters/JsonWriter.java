package filters;

import java.io.PrintWriter;
import java.io.Writer;

public class JsonWriter extends PrintWriter {

    public JsonWriter(Writer out) {
        super(out);
    }

    @Override
    public void println(int x) {
        super.println(x);
    }
}
