package com.example.demo;

import java.util.*;
import java.util.stream.Collectors;

public class Enchant extends ParsedItem implements Comparable<Enchant> {

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
        this.artifactTypes = Arrays.stream(artifactTypes).filter(s -> !s.isBlank()).collect(Collectors.toList());
    }

    public EnchantQuality getEnchantQuality() {
        return enchantQuality;
    }

    public void setEnchantQuality(String enchantQuality) {
        this.enchantQuality = EnchantQuality.valueOf(enchantQuality);
    }

    public String getValueOrEmpty() {
        if (this.getAdditions().containsKey("_Values")) {
            return (String) this.getAdditions().get("_Values");
        } else if (this.getAdditions().containsKey("Values")) {
            return (String) this.getAdditions().get("Values");
        } else {
            return "";
        }
    }

    @Override
    public int compareTo(Enchant o) {
        return this.getName().toLowerCase().compareTo(o.getName().toLowerCase());
    }

    public static enum EnchantQuality {
        primary, secondary, godlike, morality, gem;
    }

}
