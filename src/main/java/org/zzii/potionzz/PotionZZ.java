package org.zzii.potionzz;

import org.bukkit.plugin.java.JavaPlugin;
import org.zzii.potionzz.command.GivePotionCommand;
import org.zzii.potionzz.command.PotionTabCompleter;
import org.zzii.potionzz.manager.PotionManager;

public class PotionZZ extends JavaPlugin {

    public static PotionZZ instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        PotionManager potionManager = new PotionManager(getConfig());
        getCommand("zpotion").setExecutor(new GivePotionCommand(potionManager));
        getCommand("zpotion").setTabCompleter(new PotionTabCompleter(potionManager));
    }
}
