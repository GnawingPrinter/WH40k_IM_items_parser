package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.example.demo.DemoApplication.readLine;

public class EnchantParser {
    public Enchant parse(LineNumberReader reader) throws IOException {
        String line = reader.readLine();
        if (line.startsWith("{")) {
            Enchant e = new Enchant();
            for (line = readLine(reader); !line.startsWith("}"); line = readLine(reader)) {
                readEnchantLine(line, e, reader);
            }
            return e;
        } else {
            throw new IllegalArgumentException("malformed file - expected { after Enchantment");
        }
    }


    private void readEnchantLine(String line, Enchant e, BufferedReader bufferedReader) throws IOException {
        String key;
        String value = "";
        if (line.contains("=")) {
            String[] lineSplit = line.split("=", 2);
            if (lineSplit.length != 2) {
                throw new IllegalArgumentException(String.format("line contains more than one '='! Line: %s", line));
            }
            if (lineSplit[0].contains(",")) {
                throw new IllegalArgumentException(String.format("lineSplit[0] contains ','! Line: %s", line));
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
        switch (key) {
            case "Name":
                e.setName(value);
                break;
            case "NameID":
                e.setNameId(value);
                break;
            case "ArtifactTypes":
                e.setArtifactTypes(value.split(","));
                break;
            case "EnchantQuality":
                e.setEnchantQuality(value);
                break;
            default:
                e.addAddition(key, value);
        }
    }
}
