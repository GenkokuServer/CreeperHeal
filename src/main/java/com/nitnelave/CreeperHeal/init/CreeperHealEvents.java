package com.nitnelave.CreeperHeal.init;

import com.nitnelave.CreeperHeal.block.BurntBlockManager;
import com.nitnelave.CreeperHeal.block.ExplodedBlockManager;
import com.nitnelave.CreeperHeal.config.CfgVal;
import com.nitnelave.CreeperHeal.config.CreeperConfig;
import com.nitnelave.CreeperHeal.listeners.*;
import com.nitnelave.CreeperHeal.utils.CreeperLog;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public final class CreeperHealEvents {

    public static void initialize(Plugin plugin) {
        CreeperLog.LOGGER.fine("Registering events");
        PluginManager pm = plugin.getServer().getPluginManager();

        pm.registerEvents(new CreeperListener(), plugin);
        pm.registerEvents(new CreeperBlockListener(), plugin);

        if (CreeperConfig.getBool(CfgVal.LEAVES_VINES))
            pm.registerEvents(new LeavesListener(), plugin);

        if (CreeperConfig.getBool(CfgVal.PREVENT_BLOCK_FALL))
            pm.registerEvents(new BlockFallListener(), plugin);

        if (CreeperConfig.getBool(CfgVal.RAIL_REPLACEMENT))
            pm.registerEvents(new RailsUpdateListener(), plugin);

        if (CreeperConfig.getInt(CfgVal.WAIT_BEFORE_BURN_AGAIN) > 0)
            pm.registerEvents(new BlockIgniteListener(), plugin);

        ExplodedBlockManager.init();
        BurntBlockManager.init();
        CreeperLog.LOGGER.fine("Events registered");
    }

    private CreeperHealEvents() {
    }

}
