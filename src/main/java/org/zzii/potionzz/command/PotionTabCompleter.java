package org.zzii.potionzz.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.zzii.potionzz.manager.PotionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PotionTabCompleter implements TabCompleter {

    // получаем список ключей единожды (зачем делать это каждый раз?)
    private final ArrayList<String> potionKeys;

    public PotionTabCompleter(PotionManager potionManager) {
        potionKeys = new ArrayList<>(potionManager.getKeys());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        switch (args.length) {
            case 1: return Collections.singletonList("give");
            case 3: return potionKeys;
            // если вернуть null для аргумента то баккит сам подставит список онлайн-игроков
            default: return null;
        }
    }
}
