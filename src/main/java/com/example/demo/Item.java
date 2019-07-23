package com.example.demo;


import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Item extends ParsedItem implements Comparable<Item> {
    private String type;
    private String nameID;
    private Rarity rarity;

    public Item() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNameID() {
        return nameID;
    }

    public void setNameID(String nameID) {
        this.nameID = nameID;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        if ("common,uncommon,rare1,rare2,godlike".equals(rarity)) {
            this.rarity = Rarity.godlike;
        } else {
            this.rarity = Rarity.valueOf(rarity);
        }
    }

    public List<String> getMainEnchants() {
        switch (getRarity()) {
            case godlike:
                return List.of(((String) getAdditions().get("GodlikeEnchants")).split(","));
            case ancient:
                return List.of(((String) getAdditions().get("FixEnchants")).split(";", 2)[0]);
            case biggodlike:
            case morality:
                return List.of((String) getAdditions().get("MainEnchant"));
            default:
                return Collections.emptyList();
        }
    }

    @Override
    public int compareTo(Item o) {
        return o.getNameID().compareTo(this.getNameID());
    }

    public static enum Rarity {
        godlike, ancient, biggodlike, morality
    }
}
