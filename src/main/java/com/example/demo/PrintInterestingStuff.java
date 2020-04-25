package com.example.demo;

import com.example.demo.domain.EnchantmentsParser;
import com.example.demo.domain.Item;
import com.example.demo.domain.ItemParser;

import java.io.IOException;

import static com.example.demo.DemoApplication.ENCHANTMENTS_CFG;
import static com.example.demo.DemoApplication.INVENTORYITEMS_CFG;

public class PrintInterestingStuff {
    private final EnchantmentsParser enchantmentsParser = new EnchantmentsParser(ENCHANTMENTS_CFG);
    private ItemParser itemParser;

    public PrintInterestingStuff() throws IOException {
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
    }

    public static void main(String[] args) throws IOException {
        new PrintInterestingStuff();
    }
}
