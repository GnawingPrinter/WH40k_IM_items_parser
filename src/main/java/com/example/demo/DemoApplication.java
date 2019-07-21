package com.example.demo;


import java.io.*;
import java.util.*;

public class DemoApplication {


    public DemoApplication() {
        Map<String, Enchant> enchantmentsMap = parse();
        System.out.println("enchants with all_item types beeing something else than godlike:");
        enchantmentsMap.values().stream()
                .filter(e -> e.getArtifactTypes().contains("all_item"))
                .filter(e -> e.getEnchantQuality() != Enchant.EnchantQuality.godlike)
                .map(Enchant::getName)
                .forEachOrdered(System.out::println);
    }

    public static void main(String[] args) {
        new DemoApplication();
    }

    public static String readLine(BufferedReader bufferedReader) throws IOException {
        String ret = bufferedReader.readLine();
        if (ret == null) {
            return ret;
        }
        if (ret.isBlank()) {
            return readLine(bufferedReader);
        } else {
            return ret.trim();
        }
    }

    private Map<String, Enchant> parse() {
        Map<String, Enchant> enchantmentsMap = new HashMap<>();
        EnchantParser parser = new EnchantParser();
        try (LineNumberReader reader = new LineNumberReader(new InputStreamReader(Objects.requireNonNull(DemoApplication.class.getClassLoader().getResourceAsStream("enchantments.cfg"))))) {
            try {

                for (String line = readLine(reader); line != null; line = readLine(reader)) {
                    if (line.isBlank()) {
                        continue;
                    }
                    if (line.startsWith("Enchantment")) {
                        Enchant enchant = parser.parse(reader);
                        enchantmentsMap.put(enchant.getName(), enchant);
                    }
                }
            } catch (RuntimeException e) {
                throw new RuntimeException("something went wrong on line: " + reader.getLineNumber(), e);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        return enchantmentsMap;
    }

}
