package com.nitnelave.CreeperHeal;

import com.nitnelave.CreeperHeal.block.BurntBlockManager;
import com.nitnelave.CreeperHeal.block.ExplodedBlockManager;
import com.nitnelave.CreeperHeal.command.CreeperCommandManager;
import com.nitnelave.CreeperHeal.config.CfgVal;
import com.nitnelave.CreeperHeal.config.CreeperConfig;
import com.nitnelave.CreeperHeal.init.CreeperHealCommands;
import com.nitnelave.CreeperHeal.init.CreeperHealEvents;
import com.nitnelave.CreeperHeal.listeners.*;
import com.nitnelave.CreeperHeal.utils.CreeperLog;
import com.nitnelave.CreeperHeal.utils.MetricsLite;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 * The main class of the CreeperHeal plugin. The main aim of this plugin is to
 * replace the damage created by Creepers or TNT, but in a natural way, one
 * block at a time, over time.
 *
 * @author nitnelave
 *
 */
public class CreeperHeal extends JavaPlugin
{

    private static CreeperHeal instance;

    /*
     * Store whether the grief-related events have already been registered.
     */
    private static boolean griefRegistered = false;

    /*
     * (non-Javadoc)
     *
     * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
     */
    @Override
    public void onEnable()
    {
        instance = this;
        CreeperHealCommands.initialize(this);
        CreeperHealEvents.initialize(this);
        try
        {
            MetricsLite metrics = new MetricsLite(this);
            metrics.start();
        } catch (IOException e)
        {
            CreeperLog.LOGGER.log(Level.WARNING, "Could not submit data to MC-Stats", e);
        }
    }

    /**
     * Register grief-related events.
     */
    public static void registerGriefEvents()
    {
        if (!griefRegistered)
        {
            CreeperLog.LOGGER.fine("Registering Grief events");
            Bukkit.getServer().getPluginManager().registerEvents(new GriefListener(), getInstance());
            griefRegistered = true;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
     */
    @Override
    public void onDisable()
    {
        ExplodedBlockManager.forceReplace(); //replace blocks still in memory, so they are not lost
        BurntBlockManager.forceReplaceBurnt(); //same for burnt_blocks
    }

    /**
     * Get the instance of the CreeperHeal plugin.
     *
     * @return The instance of CreeperHeal.
     */
    public static CreeperHeal getInstance()
    {
        return instance;
    }

    /**
     * Gets the plugin data folder.
     *
     * @return The plugin data folder
     */
    public static File getCHFolder()
    {
        return getInstance().getDataFolder();
    }

}