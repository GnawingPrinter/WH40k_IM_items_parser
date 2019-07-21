package com.example.demo;


public class Item extends ParsedItem {
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

    public static enum Rarity {
        godlike, ancient, biggodlike, morality
    }
}
