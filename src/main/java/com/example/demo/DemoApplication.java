package com.example.demo;


import java.awt.datatransfer.StringSelection;
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

        System.out.println("resist_all_major");
        enchantmentsParser.streamEnchants().filter(i -> i.getName().equals("resist_all_major"))
                .flatMap(i -> i.getArtifactTypes().stream()).forEach(System.out::println);
        System.out.println();
        System.out.println("Critical_damage_bonus");
        enchantmentsParser.streamEnchants().filter(i -> i.getName().equals("Critical_damage_bonus"))
                .flatMap(i -> i.getArtifactTypes().stream()).forEach(System.out::println);
        System.out.println();
        System.out.println("critical_hit_strength_major");
        enchantmentsParser.streamEnchants().filter(i -> i.getName().equals("critical_hit_strength_major"))
                .flatMap(i -> i.getArtifactTypes().stream()).forEach(System.out::println);
        System.out.println();
        System.out.println("resistance_physical_major");
        enchantmentsParser.streamEnchants().filter(i -> i.getName().equals("resistance_physical_major"))
                .flatMap(i -> i.getArtifactTypes().stream()).forEach(System.out::println);
        System.out.println();
        System.out.println("Heal_on_Crit");
        itemParser.streamItems().filter(item -> item.getMainEnchants().contains("Heal_on_Crit")).sorted()
                .map(Item::getNameID).forEach(System.out::println);
        System.out.println();
        System.out.println("Godlike_Suppression_Heal_on_Crit");
        itemParser.streamItems().filter(item -> item.getMainEnchants().contains("Godlike_Suppression_Heal_on_Crit"))
                .sorted().map(Item::getNameID).forEach(System.out::println);
        System.out.println();
        System.out.println("Godlike_Enraged_on_Crit");
        itemParser.streamItems().filter(item -> item.getMainEnchants().contains("Godlike_Enraged_on_Crit")).sorted()
                .map(Item::getNameID).forEach(System.out::println);
        System.out.println();
        System.out.println("Godlike_Bonus_Enraged_limit");
        itemParser.streamItems().filter(item -> item.getMainEnchants().contains("Godlike_Bonus_Enraged_limit")).sorted()
                .map(Item::getNameID).forEach(System.out::println);
        System.out.println();
        System.out.println("Godlike_Crit_Chance_per_Enraged");
        itemParser.streamItems().filter(item -> item.getMainEnchants().contains("Godlike_Crit_Chance_per_Enraged"))
                .sorted().map(Item::getNameID).forEach(System.out::println);
        System.out.println();
        System.out.println("Godlike_Crit_Strength_per_Enraged");
        itemParser.streamItems().filter(item -> item.getMainEnchants().contains("Godlike_Crit_Strength_per_Enraged"))
                .sorted().map(Item::getNameID).forEach(System.out::println);
        System.out.println();
        System.out.println("Godlike_Damage_per_Enraged");
        itemParser.streamItems().filter(item -> item.getMainEnchants().contains("Godlike_Damage_per_Enraged")).sorted()
                .map(Item::getNameID).forEach(System.out::println);
        System.out.println();
        System.out.println("Godlike_Damage_per_Enraged_special");
        itemParser.streamItems().filter(item -> item.getMainEnchants().contains("Godlike_Damage_per_Enraged_special"))
                .sorted().map(Item::getNameID).forEach(System.out::println);

//        this.enchantmentsParser.streamEnchants()
//                .filter(e -> !e.getArtifactTypes().isEmpty() && !e.getArtifactTypes().contains("all_item"))
//                .filter(e -> e.getAdditions().get("PriorityGroup") != null &&
//                             ((String) e.getAdditions().get("PriorityGroup")).startsWith("base"))
//                .map(e -> e.getName() + " " + e.getArtifactTypes()).forEach(System.out::println);

//        base.stream().sorted()
//                .map(e -> e.getName() + " " + e.getEnchantQuality() + " " + e.getAdditions().get("PriorityGroup"))
//                .forEach(System.out::println);
//        System.out.println();
//        bonus.stream().sorted()
//                .map(e -> e.getName() + " " + e.getEnchantQuality() + " " + e.getAdditions().get("PriorityGroup"))
//                .forEach(System.out::println);
//        });


//        printAllPrimSecValueWikiPage();
//        printAllItemWikiPage();
//        findMainEnchantsFor(itemParser.streamItems(), "force_staff", Item.Rarity.ancient).map(item -> item.getNameID()).forEach(System.out::println);

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

//    private Stream<Enchant> allEnchantsHavingArtifactTypeOrGroupOfType(String type) {
//
//    }

    private void printAllPrimSecValueWikiPage() {
        System.out.println("## Primary enchants:\n```");
        printEnchantValuesOfType(Enchant.EnchantQuality.primary);
        System.out.println("```\n## Secondary enchants:\n```");
        printEnchantValuesOfType(Enchant.EnchantQuality.secondary);
        System.out.println("```\n## main enchants:\n```");
        printEnchantValuesOfType(Enchant.EnchantQuality.godlike);
        System.out.println("```\n## morality main enchants:\n```");
        printEnchantValuesOfType(Enchant.EnchantQuality.morality);

    }

    private void printEnchantValuesOfType(Enchant.EnchantQuality quality) {
        this.enchantmentsParser.streamEnchants().filter(enchant -> enchant.getEnchantQuality() == quality)
                .filter(enchant -> !enchant.getValueOrEmpty().isBlank()).sorted().forEach(item -> System.out.println(
                "\n" + item.getName() + ":\n  " + item.getValueOrEmpty() +
                addIfpresent(item, "PriorityGroup", "\n " + " ") + addIfpresent(item, "TwoHandedDouble", "\n  ")));
    }

    private void findAllEnchantableWith(String enchantName) {
        this.itemParser.streamItems().filter(item -> item.getMainEnchants().contains(enchantName)).map(Item::getNameID)
                .sorted().forEach(System.out::println);
    }

    private void printAllItemWikiPage() {
        enchantmentsParser.getItems().stream().sorted().forEach(item -> {
            Set<Enchant> enchantsPrimary = new HashSet<>();
            Set<Enchant> enchantsSecondary = new HashSet<>();
            Set<String> enchantsMain = new HashSet<>();
            findEnchantsByArtifactTypeContainedAndQualityEqual(item, Enchant.EnchantQuality.primary)
                    .forEach(enchantsPrimary::add);
            findEnchantsByArtifactTypeContainedAndQualityEqual(item, Enchant.EnchantQuality.secondary)
                    .forEach(enchantsSecondary::add);
            this.enchantmentsParser.streamItemGroups()
                    .filter(stringListEntry -> stringListEntry.getValue().contains(item)).map(Map.Entry::getKey)
                    .forEach(group -> {
                        findEnchantsByArtifactTypeContainedAndQualityEqual(group, Enchant.EnchantQuality.primary)
                                .forEach(enchantsPrimary::add);
                        findEnchantsByArtifactTypeContainedAndQualityEqual(group, Enchant.EnchantQuality.secondary)
                                .forEach(enchantsSecondary::add);
                    });
            this.itemParser.streamItems().filter(i -> i.getType().equals(item) && i.getRarity() == Item.Rarity.godlike)
                    .map(i -> (String) i.getAdditions().get("GodlikeEnchants"))
                    .flatMap(list -> Arrays.stream(list.split(","))).forEach(enchantsMain::add);

            System.out.println("\n## " + item + ":\nrelic enchants:\n```");
            System.out
                    .println(enchantsMain.stream().map(String::toLowerCase).sorted().collect(Collectors.joining("\n")));
            System.out.println("```\nprimary enchants:\n```");
            System.out.println(enchantsPrimary.stream().map(Enchant::getName).map(String::toLowerCase).sorted()
                    .collect(Collectors.joining("\n")));
            System.out.println("```\nsecondary enchants:\n```");
            System.out.println(enchantsSecondary.stream().map(Enchant::getName).map(String::toLowerCase).sorted()
                    .collect(Collectors.joining("\n")));
            System.out.println("```");

            printAdditionalItems(item);

        });
    }

    private void printAdditionalItems(String item) {
        System.out.println("### Ancients: ");
        System.out.println("```");
        this.itemParser.streamItems().filter(i -> i.getType().equals(item) && i.getRarity() == Item.Rarity.ancient)
                .map(i -> i.getNameID() + ":\n" + i.getAdditions().get("FixEnchants") + "\n").sorted()
                .forEach(System.out::println);
        System.out.println("```");
        System.out.println("### Archeotech: ");
        System.out.println("```");
        this.itemParser.streamItems().filter(i -> i.getType().equals(item) && i.getRarity() == Item.Rarity.biggodlike)
                .map(i -> i.getNameID() + ": " + biggodlikeAndMoralityItemEnchantmentsDescription(i) + "\n").sorted()
                .forEach(System.out::println);
        System.out.println("```");
        System.out.println("### Morality: ");
        System.out.println("```");
        this.itemParser.streamItems().filter(i -> i.getType().equals(item) && i.getRarity() == Item.Rarity.morality)
                .map(i -> i.getNameID() + ": " + biggodlikeAndMoralityItemEnchantmentsDescription(i) + "\n").sorted()
                .forEach(System.out::println);
        System.out.println("```");

    }

    private String biggodlikeAndMoralityItemEnchantmentsDescription(Item i) {
        return addIfpresent(i, "MainEnchant", "\n  ") + addIfpresent(i, "PrimaryEnchants1", "\n  ") +
               addIfpresent(i, "PrimaryEnchants2", "\n  ") + addIfpresent(i, "SecondaryEnchants1", "\n  ") +
               addIfpresent(i, "SecondaryEnchants2", "\n  ") + addIfpresent(i, "ForcedSlots", "\n  ");
    }

    private String addIfpresent(ParsedItem i, String key, String prefix) {
        if (i.getAdditions().containsKey(key)) {
            return prefix + key + "=" + (i.getAdditions().get(key)).toString();
        } else {
            return "";
        }
    }

    private Stream<Enchant> findEnchantsByArtifactTypeContainedAndQualityEqual(String item,
                                                                               Enchant.EnchantQuality primary) {
        return this.enchantmentsParser.streamEnchants()
                .filter(enchant -> enchant.getArtifactTypes().contains(item) && enchant.getEnchantQuality() == primary);
    }

    private Stream<Item> findMainEnchantsFor(Map<String, Item> items, String item, Item.Rarity rarity) {
        return items.values().stream().filter(item1 -> item1.getType().equals(item) && item1.getRarity() == rarity);
    }

    private void printDirectsForItem(String item) {
        System.out.println("all direct enchants for " + item + ":");
        enchantmentsParser.streamEnchants().filter(enchant -> enchant.getArtifactTypes().contains(item))
                .map(enchant -> enchant.getName() + " " + enchant.getEnchantQuality()).sorted()
                .forEachOrdered(System.out::println);
    }

    private void printAssumptionsAboutEnchants() {
        System.out.println("enchants without any artifact type, not being quality: godlike, morality or gem:");
        enchantmentsParser.streamEnchants().filter(enchant -> enchant.getArtifactTypes().isEmpty())
                .filter(enchant -> enchant.getEnchantQuality() != Enchant.EnchantQuality.godlike &&
                                   enchant.getEnchantQuality() != Enchant.EnchantQuality.morality &&
                                   enchant.getEnchantQuality() != Enchant.EnchantQuality.gem).map(Enchant::getName)
                .forEachOrdered(System.out::println);
        System.out.println("enchants with quality: morality without empty artifactType:");
        enchantmentsParser.streamEnchants()
                .filter(enchant -> enchant.getEnchantQuality() == Enchant.EnchantQuality.morality)
                .filter(enchant -> !enchant.getArtifactTypes().isEmpty()).map(Enchant::getName)
                .forEachOrdered(System.out::println);
        System.out.println("enchants with quality: gem without empty artifactType: ");
        enchantmentsParser.streamEnchants().filter(enchant -> enchant.getEnchantQuality() == Enchant.EnchantQuality.gem)
                .filter(enchant -> !enchant.getArtifactTypes().isEmpty()).map(Enchant::getName)
                .forEachOrdered(System.out::println);
        System.out.println("enchants for artifactType: \"all_item\" being something else than quality: godlike:");
        enchantmentsParser.streamEnchants().filter(e -> e.getArtifactTypes().contains("all_item"))
                .filter(e -> e.getEnchantQuality() != Enchant.EnchantQuality.godlike).map(Enchant::getName)
                .forEachOrdered(System.out::println);
        System.out.println("enchants with quality: godlike without empty ArtifactType or ArtifactType = \"all_item\"");
        enchantmentsParser.streamEnchants().filter(e -> e.getEnchantQuality() == Enchant.EnchantQuality.godlike)
                .filter(e -> !e.getArtifactTypes().contains("all_item") && !e.getArtifactTypes().isEmpty())
                .map(Enchant::getName).forEachOrdered(System.out::println);
        System.out
                .println("enchants being not godlike or gem without type, not having moralityRequirement = 300 or -300");
        enchantmentsParser.streamEnchants().filter(e -> (e.getEnchantQuality() != Enchant.EnchantQuality.godlike &&
                                                         e.getEnchantQuality() != Enchant.EnchantQuality.gem))
                .filter(enchant -> enchant.getArtifactTypes().isEmpty())
                .filter(enchant -> !enchant.getAdditions().containsKey("MoralityRequirement") ||
                                   !(enchant.getAdditions().get("MoralityRequirement").equals("300") ||
                                     enchant.getAdditions().get("MoralityRequirement").equals("-300")))
                .map(enchant -> enchant.getName() + " additions: " + enchant.getAdditions())
                .forEachOrdered(System.out::println);
        System.out.println("enchants with quality morality not having moralityRequirement = 300 | -300");
        enchantmentsParser.streamEnchants().filter(e -> e.getEnchantQuality() == Enchant.EnchantQuality.morality)
                .filter(enchant -> !enchant.getAdditions().containsKey("MoralityRequirement") ||
                                   !(enchant.getAdditions().get("MoralityRequirement").equals("300") ||
                                     enchant.getAdditions().get("MoralityRequirement").equals("-300")))
                .map(Enchant::getName).forEachOrdered(System.out::println);
        System.out.println("enchants with quality: morality with artifactTypes not empty:");
        enchantmentsParser.streamEnchants()
                .filter(enchant -> enchant.getEnchantQuality() == Enchant.EnchantQuality.morality)
                .filter(enchant -> !enchant.getArtifactTypes().isEmpty()).map(Enchant::getName)
                .forEachOrdered(System.out::println);
        System.out.println(
                "enchants with quality: primary or secondary with empty artifactTypes or artifactTypes=all_item:");
        enchantmentsParser.streamEnchants()
                .filter(enchant -> enchant.getEnchantQuality() == Enchant.EnchantQuality.primary ||
                                   enchant.getEnchantQuality() == Enchant.EnchantQuality.secondary)
                .filter(enchant -> enchant.getArtifactTypes().contains("all_item")).map(Enchant::getName)
                .forEachOrdered(System.out::println);
        System.out.println("enchants having \"all_item\" together with other artifactTypes");
        enchantmentsParser.streamEnchants().filter(enchant -> enchant.getArtifactTypes().contains("all_item") &&
                                                              enchant.getArtifactTypes().size() > 1)
                .map(Enchant::getName).forEachOrdered(System.out::println);
        System.out.println("enchants with quality: gem without empty ArtifactTypes: ");
        enchantmentsParser.streamEnchants()
                .filter(enchant -> enchant.getEnchantQuality() == Enchant.EnchantQuality.gem &&
                                   !enchant.getArtifactTypes().isEmpty()).map(Enchant::getName)
                .forEachOrdered(System.out::println);
        System.out.println(
                "enchants with ArtifactType not empty and not \"all_item\" beeing something else then primary/secondary");
        enchantmentsParser.streamEnchants().filter(enchant -> (!enchant.getArtifactTypes().isEmpty() &&
                                                               !enchant.getArtifactTypes().contains("all_item")))
                .filter(enchant -> (enchant.getEnchantQuality() != Enchant.EnchantQuality.primary &&
                                    enchant.getEnchantQuality() != Enchant.EnchantQuality.secondary))
                .map(Enchant::getName).forEachOrdered(System.out::println);
    }

}
