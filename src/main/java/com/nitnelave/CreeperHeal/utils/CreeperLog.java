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
public abstract class CreeperLog
{
    /*
     * Logger, for outputting to the console.
     */
    public final static Logger LOGGER = CreeperHeal.getInstance().getLogger();

    /*
     * The verbosity level. Initialized at -42 as an arbitrary value, to detect
     * that it hasn't been loaded from the config yet.
     */
    private static int logLevel = -42;
    /*
     * Whether to output debug messages.
     */
    private static boolean debug;

    static
    {
        File folder = CreeperHeal.getCHFolder();
        LOGGER.setParent(Logger.getLogger("Minecraft"));
        try {
            if (folder.mkdirs())
                LOGGER.addHandler(new FileHandler(folder + "/%u.log", true));
            else
                LOGGER.severe("Couldn't create the data directories");
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Couldn't register the FileHandler", exception);
        }

        debug = CreeperConfig.getBool(CfgVal.DEBUG);
    }

    /**
     * Display the type and the location of a block in a formatted way. If force
     * is true, then it is a warning (as part of a warning message). Otherwise
     * it is a debug message.
     *
     * @param block
     *            The block whose information is displayed.
     * @param force
     *            Whether it is a warning or a debug message.
     */
    public static void displayBlockLocation(Block block, boolean force)
    {
        final Location location = block.getLocation();
        final String message = block.getType() + " at "
            + location.getBlockX() + "; " + location.getBlockY() + "; " + location.getBlockZ();
        if (force)
            LOGGER.warning(message);
        else
            LOGGER.fine(message);
    }

    public static void setDebug(boolean bool)
    {
        debug = bool;
        LOGGER.info("[CreeperHeal] Debug mode: " + debug);
    }

}
