package org.zzii.potionzz.model;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.zzii.potionzz.util.ChatUtils;

import java.util.ArrayList;
import java.util.List;

public class PotionData {

    private final String name;
    private final List<String> lore;
    private final Color color;
    private final boolean hideAttributes;
    private final boolean splash;
    private final List<PotionEffect> effects;

    private PotionData(String name, List<String> lore, Color color,
                       boolean hideAttributes, boolean splash, List<PotionEffect> effects) {
        this.name = name;
        this.lore = lore;
        this.color = color;
        this.hideAttributes = hideAttributes;
        this.splash = splash;
        this.effects = effects;
    }

    public static PotionData fromConfig(FileConfiguration config, String path) {
        String name = ChatUtils.colorize(config.getString(path + ".name"));
        List<String> lore = ChatUtils.colorize(config.getStringList(path + ".lore"));
        String colorRaw = config.getString(path + ".color");
        Color color = colorRaw != null ? parseColor(colorRaw) : null;
        boolean hideAttributes = config.getBoolean(path + ".hideAttributes");
        boolean splash = config.getBoolean(path + ".splash_potion");
        List<PotionEffect> effects = parseEffects(config.getStringList(path + ".effects"));
        return new PotionData(name, lore, color, hideAttributes, splash, effects);
    }

    public ItemStack toItemStack() {
        ItemStack item = new ItemStack(splash ? Material.SPLASH_POTION : Material.POTION, 1);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        if (meta == null) return item;

        meta.setDisplayName(name);
        if (!lore.isEmpty()) meta.setLore(lore);
        if (color != null) meta.setColor(color);
        if (hideAttributes) meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_POTION_EFFECTS);
        effects.forEach(effect -> meta.addCustomEffect(effect, true));

        item.setItemMeta(meta);
        return item;
    }

    public String getName() {
        return name;
    }

    private static List<PotionEffect> parseEffects(List<String> rawEffects) {
        List<PotionEffect> effects = new ArrayList<>();
        for (String raw : rawEffects) {
            String[] parts = raw.split(":");
            if (parts.length != 3) continue;
            PotionEffectType type = PotionEffectType.getByName(parts[0]);
            if (type == null) continue;
            effects.add(new PotionEffect(type, Integer.parseInt(parts[2]), Integer.parseInt(parts[1])));
        }
        return effects;
    }

    private static Color parseColor(String rgb) {
        String[] parts = rgb.split(",");
        return Color.fromRGB(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }
}
