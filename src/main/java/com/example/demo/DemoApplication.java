package com.example.demo;


import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DemoApplication {
    public static final String ENCHANTMENTS_CFG = "enchantments.cfg";
    public static final String INVENTORYITEMS_CFG = "inventoryitems.cfg";
    private final EnchantmentsParser enchantmentsParser = new EnchantmentsParser(ENCHANTMENTS_CFG);
    private ItemParser itemParser;

    public DemoApplication() throws IOException {
        this.enchantmentsParser.parse();
        this.itemParser = new ItemParser(INVENTORYITEMS_CFG, this.enchantmentsParser.getItems());
        this.itemParser.parse();

        enchantmentsParser.getItems().stream().sorted().forEach(item -> {
            Set<Enchant> enchantsPrimary = new HashSet<>();
            Set<Enchant> enchantsSecondary = new HashSet<>();
            Set<String> enchantsMain = new HashSet<>();
            findEnchantsByArtifactTypeContainedAndQualityEqual(item, Enchant.EnchantQuality.primary)
                    .forEach(enchantsPrimary::add);
            findEnchantsByArtifactTypeContainedAndQualityEqual(item, Enchant.EnchantQuality.secondary)
                    .forEach(enchantsSecondary::add);
            this.enchantmentsParser.getItemGroups().entrySet().stream().filter(stringListEntry -> stringListEntry.getValue().contains(item)).map(Map.Entry::getKey).forEach(group -> {
                findEnchantsByArtifactTypeContainedAndQualityEqual(group, Enchant.EnchantQuality.primary)
                        .forEach(enchantsPrimary::add);
                findEnchantsByArtifactTypeContainedAndQualityEqual(group, Enchant.EnchantQuality.secondary)
                        .forEach(enchantsSecondary::add);
            });
            this.itemParser.getAllItems().values().stream()
                    .filter(i -> i.getType().equals(item) && i.getRarity() == Item.Rarity.godlike).map(i -> (String) i.getAdditions().get("GodlikeEnchants"))
                    .flatMap(list -> Arrays.stream(list.split(","))).forEach(enchantsMain::add);

            System.out.println("\n### " + item + ":\nrelic enchants:\n```");
            System.out.println(enchantsMain.stream().sorted().collect(Collectors.joining("\n")));
            System.out.println("```\nprimary enchants:\n```");
            System.out.println(enchantsPrimary.stream().map(Enchant::getName).sorted().collect(Collectors.joining("\n")));
            System.out.println("```\nsecondary enchants:\n```");
            System.out.println(enchantsSecondary.stream().map(Enchant::getName).sorted().collect(Collectors.joining("\n")));
            System.out.println("```");


        });

//        findMainEnchantsFor(itemParser.getAllItems(), "force_staff", Item.Rarity.ancient).map(item -> item.getNameID()).forEach(System.out::println);

//        enchantmentsParser.getItems().forEach(System.out::println);
//        printAssumptionsAboutEnchants(enchantmentsParser.getEnchantMap());
//
//        findPrimSecAllFor(enchantmentsMap, "weapon_1h_overheat");
//        System.out.println();
//        findPrimSecAllFor(enchantmentsMap, "inferno_pistol");
//        System.out.println();
//        findPrimSecAllFor(enchantmentsMap, "iinferno_pistol");
//        System.out.println();
//        findPrimSecAllFor(enchantmentsMap, "weapon_1h_heat");
    }

    public static void main(String[] args) throws IOException {
        new DemoApplication();
    }

    private Stream<Enchant> findEnchantsByArtifactTypeContainedAndQualityEqual(String item, Enchant.EnchantQuality primary) {
        return this.enchantmentsParser.getEnchantMap().values().stream()
                .filter(enchant -> enchant.getArtifactTypes().contains(item) && enchant.getEnchantQuality() == primary);
    }

    private Stream<Item> findMainEnchantsFor(Map<String, Item> items, String item, Item.Rarity rarity) {
        return items.values().stream().filter(item1 -> item1.getType().equals(item) && item1.getRarity() == rarity);
    }

    private void findPrimSecAllFor(Map<String, Enchant> enchantmentsMap, String item) {
        System.out.println("all direct enchants for " + item + ":");
        enchantmentsMap.values().stream()
                .filter(enchant -> enchant.getArtifactTypes().contains(item))
                .map(enchant -> enchant.getName() + " " + enchant.getEnchantQuality()).forEachOrdered(System.out::println);
    }

    private void printAssumptionsAboutEnchants(Map<String, Enchant> enchantmentsMap) {
        System.out.println("enchants without any artifact type, not being quality: godlike, morality or gem:");
        enchantmentsMap.values().stream()
                .filter(enchant -> enchant.getArtifactTypes().isEmpty())
                .filter(enchant -> enchant.getEnchantQuality() != Enchant.EnchantQuality.godlike && enchant.getEnchantQuality() != Enchant.EnchantQuality.morality && enchant.getEnchantQuality() != Enchant.EnchantQuality.gem)
                .map(Enchant::getName)
                .forEachOrdered(System.out::println);
        System.out.println("enchants with quality: morality without empty artifactType:");
        enchantmentsMap.values().stream()
                .filter(enchant -> enchant.getEnchantQuality() == Enchant.EnchantQuality.morality)
                .filter(enchant -> !enchant.getArtifactTypes().isEmpty())
                .map(Enchant::getName)
                .forEachOrdered(System.out::println);
        System.out.println("enchants with quality: gem without empty artifactType: ");
        enchantmentsMap.values().stream()
                .filter(enchant -> enchant.getEnchantQuality() == Enchant.EnchantQuality.gem)
                .filter(enchant -> !enchant.getArtifactTypes().isEmpty())
                .map(Enchant::getName)
                .forEachOrdered(System.out::println);
        System.out.println("enchants for artifactType: \"all_item\" being something else than quality: godlike:");
        enchantmentsMap.values().stream()
                .filter(e -> e.getArtifactTypes().contains("all_item"))
                .filter(e -> e.getEnchantQuality() != Enchant.EnchantQuality.godlike)
                .map(Enchant::getName)
                .forEachOrdered(System.out::println);
        System.out.println("enchants with quality: godlike without empty ArtifactType or ArtifactType = \"all_item\"");
        enchantmentsMap.values().stream()
                .filter(e -> e.getEnchantQuality() == Enchant.EnchantQuality.godlike)
                .filter(e -> !e.getArtifactTypes().contains("all_item") && !e.getArtifactTypes().isEmpty())
                .map(Enchant::getName)
                .forEachOrdered(System.out::println);
        System.out.println("enchants being not godlike or gem without type, not having moralityRequirement = 300 or -300");
        enchantmentsMap.values().stream()
                .filter(e -> (e.getEnchantQuality() != Enchant.EnchantQuality.godlike && e.getEnchantQuality() != Enchant.EnchantQuality.gem))
                .filter(enchant -> enchant.getArtifactTypes().isEmpty())
                .filter(enchant -> !enchant.getAdditions().containsKey("MoralityRequirement") ||
                        !(enchant.getAdditions().get("MoralityRequirement").equals("300") || enchant.getAdditions().get("MoralityRequirement").equals("-300")))
                .map(enchant -> enchant.getName() + " additions: " + enchant.getAdditions())
                .forEachOrdered(System.out::println);
        System.out.println("enchants with quality morality not having moralityRequirement = 300 | -300");
        enchantmentsMap.values().stream()
                .filter(e -> e.getEnchantQuality() == Enchant.EnchantQuality.morality)
                .filter(enchant -> !enchant.getAdditions().containsKey("MoralityRequirement") ||
                        !(enchant.getAdditions().get("MoralityRequirement").equals("300") || enchant.getAdditions().get("MoralityRequirement").equals("-300")))
                .map(Enchant::getName)
                .forEachOrdered(System.out::println);
        System.out.println("enchants with quality: morality with artifactTypes not empty:");
        enchantmentsMap.values().stream()
                .filter(enchant -> enchant.getEnchantQuality() == Enchant.EnchantQuality.morality)
                .filter(enchant -> !enchant.getArtifactTypes().isEmpty())
                .map(Enchant::getName)
                .forEachOrdered(System.out::println);
        System.out.println("enchants with quality: primary or secondary with empty artifactTypes or artifactTypes=all_item:");
        enchantmentsMap.values().stream()
                .filter(enchant -> enchant.getEnchantQuality() == Enchant.EnchantQuality.primary || enchant.getEnchantQuality() == Enchant.EnchantQuality.secondary)
                .filter(enchant -> enchant.getArtifactTypes().contains("all_item"))
                .map(Enchant::getName)
                .forEachOrdered(System.out::println);
        System.out.println("enchants having \"all_item\" together with other artifactTypes");
        enchantmentsMap.values().stream()
                .filter(enchant -> enchant.getArtifactTypes().contains("all_item") && enchant.getArtifactTypes().size() > 1)
                .map(Enchant::getName)
                .forEachOrdered(System.out::println);
        System.out.println("enchants with quality: gem without empty ArtifactTypes: ");
        enchantmentsMap.values().stream()
                .filter(enchant -> enchant.getEnchantQuality() == Enchant.EnchantQuality.gem && !enchant.getArtifactTypes().isEmpty())
                .map(Enchant::getName)
                .forEachOrdered(System.out::println);
        System.out.println("enchants with ArtifactType not empty and not \"all_item\" beeing something else then primary/secondary");
        enchantmentsMap.values().stream()
                .filter(enchant -> (!enchant.getArtifactTypes().isEmpty() && !enchant.getArtifactTypes().contains("all_item")))
                .filter(enchant -> (enchant.getEnchantQuality() != Enchant.EnchantQuality.primary && enchant.getEnchantQuality() != Enchant.EnchantQuality.secondary))
                .map(Enchant::getName)
                .forEachOrdered(System.out::println);
    }

}
