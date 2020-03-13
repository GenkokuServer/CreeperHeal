package com.nitnelave.CreeperHeal.init;

import com.nitnelave.CreeperHeal.command.CreeperCommand;
import com.nitnelave.CreeperHeal.command.CreeperCommandManager;
import com.nitnelave.CreeperHeal.config.CreeperConfig;
import com.nitnelave.CreeperHeal.utils.CreeperLog;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.logging.Level;

public final class CreeperHealCommands {

    public static void initialize(Plugin plugin) {
        CommandMap commandMap;
        try {
            Field field = plugin.getServer().getPluginManager().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            commandMap = (CommandMap) (field.get(plugin.getServer().getPluginManager()));
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            CreeperLog.LOGGER.log(Level.SEVERE, "Couldn't get vanilla command map", exception);
            return;
        }
        if (commandMap != null) {
            String[] aliases = { CreeperConfig.getAlias() };
            CreeperCommand command = new CreeperCommand(aliases, "", "", new CreeperCommandManager());
            commandMap.register("creeperheal", command);
        }
    }

    private CreeperHealCommands() {
    }

}
