package com.example.demo.domain;

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.*;
import java.util.stream.Stream;

public class ItemParser extends BlobParser {

    public static final String RARITY = "Rarity";
    public static final String TYPE = "Type";
    private final Map<String, Item> allItems = new HashMap<>();
    private final Set<String> allItemNames;

    public ItemParser(String path, Set<String> allItemNames) {
        super(path);
        this.allItemNames = allItemNames;
    }

    @Override
    public void parse(LineNumberReader reader) throws IOException {
        for (String line = readLine(reader); line != null; line = readLine(reader)) {
            ParsedItem blob = super.readWh40kImBlobToMap(reader);
            maybeAddItem(blob, line);
        }
    }

    private void maybeAddItem(ParsedItem blob, String line) {
        if (line.startsWith("__") || blob.getAdditions().get(RARITY) == null || blob.getAdditions().get(TYPE) == null ||
            blob.getAdditions().get("Debug") != null) {
            return;
        }
        String type = (String) blob.getAdditions().get("Type");
        if (allItemNames.contains(type)) {
            Item item = itemFromBlob(blob, line);
            this.allItems.put(line, item);
        }
    }

    private Item itemFromBlob(ParsedItem blob, String line) {
        Item item = new Item();
        item.setNameID(line);
        item.setRarity((String) blob.getAdditions().get(RARITY));
        item.setType((String) blob.getAdditions().get(TYPE));
        blob.getAdditions().remove(RARITY);
        blob.getAdditions().remove(TYPE);
        item.setAdditions(blob.getAdditions());
        return item;
    }

    public Map<String, Item> getAllItems() {
        return allItems;
    }

    public Stream<Item> streamItems() {
        return allItems.values().stream();
    }

}
