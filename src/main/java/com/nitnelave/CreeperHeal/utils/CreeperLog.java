package com.nitnelave.CreeperHeal.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.block.Block;

import com.nitnelave.CreeperHeal.CreeperHeal;
import com.nitnelave.CreeperHeal.config.CfgVal;
import com.nitnelave.CreeperHeal.config.CreeperConfig;

/**
 * This class is used for all the outputting to the console and to players.
 *
 * @author nitnelave
 *
 */
public final class CreeperLog
{
    /*
     * Logger, for outputting to the console.
     */
    public final static Logger LOGGER = CreeperHeal.getInstance().getLogger();

    /*
     * Whether to output debug messages.
     */
    private static boolean debug;

    static
    {
        File folder = CreeperHeal.getCHFolder();
        try {
            if (folder.exists())
                LOGGER.addHandler(new FileHandler(folder + "/%u.log", true));
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Couldn't register the FileHandler", exception);
        }
        debug = CreeperConfig.getBool(CfgVal.DEBUG);
    }

    /**
     * Display the type and the location of a block in a formatted way. If warning
     * is true, then it is a warning (as part of a warning message). Otherwise
     * it is a debug message.
     *
     * @param block
     *            The block whose information is displayed.
     * @param warn
     *            Whether it is a warning or a debug message.
     */
    public static void displayBlockLocation(Block block, boolean warn)
    {
        final Location location = block.getLocation();
        final String message = block.getType() + " at "
            + location.getBlockX() + "; " + location.getBlockY() + "; " + location.getBlockZ();
        if (warn)
            LOGGER.warning(message);
        else
            LOGGER.fine(message);
    }

    public static void setDebug(boolean bool)
    {
        debug = bool;
        LOGGER.info("Debug mode: " + debug);
    }

    private CreeperLog()
    {
    }

}
