package com.example.demo;

import javax.swing.*;
import java.util.*;

public class Enchant {

    private final Map<String, Object> additions = new HashMap<>();
    private String name;
    private String nameId;
    private List<String> artifactTypes;
    private EnchantQuality enchantQuality;

    public void assumeFinished() {
        Objects.requireNonNull(name);
        Objects.requireNonNull(nameId);
        Objects.requireNonNull(artifactTypes);
        Objects.requireNonNull(enchantQuality);
    }

    public Map<String, Object> getAdditions() {
        return additions;
    }

    public void addAddition(String name, Object value) {
        if (this.additions.containsKey(name)) {
            addAddition("_" + name, value);
        }
        this.additions.put(name, value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }

    public List<String> getArtifactTypes() {
        return artifactTypes;
    }

    public void setArtifactTypes(String... artifactTypes) {
        this.artifactTypes = List.of(artifactTypes);
    }

    public EnchantQuality getEnchantQuality() {
        return enchantQuality;
    }

    public void setEnchantQuality(String enchantQuality) {
        this.enchantQuality = EnchantQuality.valueOf(enchantQuality);
    }


    public static enum EnchantQuality {
        primary, secondary, godlike, morality, gem;
    }

}
