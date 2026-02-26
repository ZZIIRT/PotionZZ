package org.zzii.potionzz.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.zzii.potionzz.PotionZZ;
import org.zzii.potionzz.manager.PotionManager;
import org.zzii.potionzz.model.PotionData;
import org.zzii.potionzz.util.ChatUtils;

public class GivePotionCommand implements CommandExecutor {

    private final PotionManager potionManager;

    public GivePotionCommand(PotionManager potionManager) {
        this.potionManager = potionManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("zpotion.give")) {
            sender.sendMessage(msg("messages.no_permission"));
            return true;
        }

        if (args.length != 3 || !args[0].equalsIgnoreCase("give")) {
            sender.sendMessage(msg("messages.usage"));
            return false;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(msg("messages.player_not_found"));
            return false;
        }

        String potionKey = args[2];
        if (!potionManager.exists(potionKey)) {
            sender.sendMessage(msg("messages.potion_not_found"));
            return false;
        }

        PotionData potion = potionManager.get(potionKey);
        target.getInventory().addItem(potion.toItemStack());
        target.sendMessage(msg("messages.potion_received").replace("%potion%", potion.getName()));

        return true;
    }

    private String msg(String path) {
        return ChatUtils.colorize(PotionZZ.instance.getConfig().getString(path));
    }
}
