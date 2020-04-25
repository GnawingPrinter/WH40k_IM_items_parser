package com.example.demo.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;

public abstract class BlobParser extends FileParser {


    public BlobParser(String path) {
        super(path);
    }

    ParsedItem readWh40kImBlobToMap(LineNumberReader reader) throws IOException {
        String line = reader.readLine();
        if (line.startsWith("{")) {
            ParsedItem item = new ParsedItem();
            for (line = readLine(reader); !line.startsWith("}"); line = readLine(reader)) {
                readLine(line, item, reader);
            }
            return item;
        } else {
            throw new IllegalArgumentException("malformed file - expected { after blob name but got: " + line);
        }
    }

    private void readLine(String line, ParsedItem item, BufferedReader bufferedReader) throws IOException {
        String key;
        String value = "";
        if (line.contains("=")) {
            String[] lineSplit = line.split("=", 2);
            if (lineSplit.length != 2) {
                throw new IllegalStateException(String.format("line contains more than one '='! Line: %s", line));
            }
            if (lineSplit[0].contains(",")) {
                throw new IllegalStateException(String.format("lineSplit[0] contains ','! Line: %s", line));
            }
            key = lineSplit[0];
            value = lineSplit[1];
        } else {
            key = line;
            line = readLine(bufferedReader);
            if (!line.startsWith("{")) {
                throw new IllegalArgumentException("malformed file - expected { after enchantment line without '=' with key: " + key);
            }
            for (line = readLine(bufferedReader); !line.startsWith("}"); line = readLine(bufferedReader)) {
                value += line + "\t";
            }
        }
        item.addAddition(key, value);
    }
}
