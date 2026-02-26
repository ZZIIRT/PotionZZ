package org.zzii.potionzz.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.zzii.potionzz.manager.PotionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PotionTabCompleter implements TabCompleter {

    private final PotionManager potionManager;

    public PotionTabCompleter(PotionManager potionManager) {
        this.potionManager = potionManager;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        switch (args.length) {
            case 1:
                return Collections.singletonList("give");
            case 2:
                return Bukkit.getOnlinePlayers().stream()
                        .map(Player::getName)
                        .collect(Collectors.toList());
            case 3:
                return new ArrayList<>(potionManager.getKeys());
            default:
                return Collections.emptyList();
        }
    }
}
