package com.example.demo.domain;

import com.example.demo.DemoApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class FileParser {
    private final String path;

    public FileParser(String path) {
        this.path = path;
    }

   static String readLine(BufferedReader bufferedReader) throws IOException {
        String ret = bufferedReader.readLine();
        if (ret == null) {
            return null;
        }
        if (ret.isBlank()) {
            return readLine(bufferedReader);
        } else {
            return ret.trim();
        }
    }

    public void parse() throws IOException {
        try (LineNumberReader reader = new LineNumberReader(new InputStreamReader(Objects.requireNonNull(DemoApplication.class.getClassLoader().getResourceAsStream(path))))) {
            try {
                parse(reader);
            } catch (RuntimeException e) {
                throw new RuntimeException("something went wrong on line: " + reader.getLineNumber(), e);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    abstract void parse(LineNumberReader reader) throws IOException;

}
