package com.leslie.square.okio;


import okio.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

public class SquareStream {

    public void readLinesFormFile(File file, Consumer<String> action) throws IOException {
        try (Source fileSource = Okio.source(file);
             BufferedSource bufferedSource = Okio.buffer(fileSource)) {

            while (true) {
                String line = bufferedSource.readUtf8Line();

                if (line == null) break;

                action.accept(line);
            }

        }
    }

    public void writeLinesToFile(File file, CharSequence sequence) throws IOException {
        try (Sink fileSink = Okio.sink(file); BufferedSink bufferedSink = Okio.buffer(fileSink)) {
            String s = sequence.toString();
            bufferedSink.writeUtf8(s);

        }
    }

    public void writeEnv(File file) throws IOException {
        try (Sink fileSink = Okio.sink(file);
             BufferedSink bufferedSink = Okio.buffer(fileSink)) {

            for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
                bufferedSink.writeUtf8(entry.getKey());
                bufferedSink.writeUtf8("=");
                bufferedSink.writeUtf8(entry.getValue());
                bufferedSink.writeUtf8("\n");
            }

        }
    }
}
