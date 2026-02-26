package org.zzii.potionzz.manager;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.zzii.potionzz.model.PotionData;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PotionManager {

    private final Map<String, PotionData> potions = new HashMap<>();

    public PotionManager(FileConfiguration config) {
        load(config);
    }

    private void load(FileConfiguration config) {
        ConfigurationSection section = config.getConfigurationSection("potions");
        if (section == null) return;
        for (String key : section.getKeys(false)) {
            potions.put(key, PotionData.fromConfig(config, "potions." + key));
        }
    }

    public boolean exists(String key) {
        return potions.containsKey(key);
    }

    public PotionData get(String key) {
        return potions.get(key);
    }

    public Set<String> getKeys() {
        return Collections.unmodifiableSet(potions.keySet());
    }
}
