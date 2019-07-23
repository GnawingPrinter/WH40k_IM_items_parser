package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.sql.Blob;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class EnchantmentsParser extends BlobParser {

    private final Map<String, List<String>> itemGroups = new HashMap<>();
    private final Set<String> items = new HashSet<>();
    private final Map<String, Enchant> enchantMap = new HashMap<>();

    public EnchantmentsParser(String path) {
        super(path);
    }

    @Override
    public void parse(LineNumberReader reader) throws IOException {
        for (String line = readLine(reader); line != null; line = readLine(reader)) {
            if (line.startsWith("Templates")) {
                parseTemplates(reader);
            } else if (line.startsWith("Enchantment")) {
                line = readLine(reader);
                if (!line.startsWith("{")) {
                    throw new IllegalArgumentException("malformed file - expected { after enchantment, got: " + line);
                }
                Enchant e = new Enchant();
                for (line = readLine(reader); !line.startsWith("}"); line = readLine(reader)) {
                    readEnchantLine(line, e, reader);
                }
                e.assumeFinished();
                enchantMap.put(e.getName(), e);
            }
        }
    }

    private void parseTemplates(LineNumberReader reader) throws IOException {
        String line = BlobParser.readLine(reader);
        if (line.startsWith("{")) {
            for (line = BlobParser.readLine(reader); !line.startsWith("}"); line = BlobParser.readLine(reader)) {
                if (line.contains("=")) {
                    String[] stringslineSplit = line.split("=", 2);
                    this.itemGroups.put(stringslineSplit[0], Arrays.asList(stringslineSplit[1].split(",")));
                } else {
                    throw new IllegalStateException("expected a list of items assigned to a name, but got: " + line);
                }
            }

            this.itemGroups.values().stream().flatMap(Collection::stream).forEach(items::add);
            HashSet<String> check = new HashSet<>();
            check.addAll(itemGroups.get("all_item"));
            check.add("iinferno_pistol");
            if (!check.equals(items)) {
                throw new IllegalStateException("all_item and all collected items differ!\n" +
                                                items.stream().sorted().collect(Collectors.joining(",")) + "\n" +
                                                check.stream().sorted().collect(Collectors.joining(",")));
            }
        } else {
            throw new IllegalStateException("expected { after \"Templates\", found: " + line);
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
                throw new IllegalArgumentException(
                        "malformed file - expected { after enchantment line without '=' with key: " + key);
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

    public Map<String, List<String>> getItemGroups() {
        return itemGroups;
    }

    public Stream<Map.Entry<String, List<String>>> streamItemGroups() {
        return itemGroups.entrySet().stream();
    }

    public Set<String> getItems() {
        return items;
    }

    public Stream<String> streamItems() {
        return items.stream();
    }

    public Map<String, Enchant> getEnchantMap() {
        return enchantMap;
    }

    public Stream<Enchant> streamEnchants() {
        return enchantMap.values().stream();
    }
}
