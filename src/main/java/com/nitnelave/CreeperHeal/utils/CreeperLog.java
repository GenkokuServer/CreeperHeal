package com.nitnelave.CreeperHeal.utils;

import com.nitnelave.CreeperHeal.CreeperHeal;
import com.nitnelave.CreeperHeal.config.CfgVal;
import com.nitnelave.CreeperHeal.config.CreeperConfig;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.logging.*;

/**
 * This class is used for all the outputting to the console and to players.
 *
 * @author nitnelave
 *
 */
public final class CreeperLog
{
    private final static Path LOG_DIRECTORY;

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
        LOG_DIRECTORY = CreeperHeal.getCHFolder().toPath().resolve("logs");
        try {
            if (Files.notExists(LOG_DIRECTORY)) {
                try {
                    Files.createDirectories(LOG_DIRECTORY);
                } catch (FileAlreadyExistsException ignored) {
                }
            }
            Path logFile =
                LOG_DIRECTORY.resolve(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now()) + "-%g.log");
            FileHandler handler = new FileHandler(logFile.toAbsolutePath().toString(), true);
            handler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(handler);
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Couldn't register the FileHandler", exception);
        }
        setDebug(CreeperConfig.getBool(CfgVal.DEBUG));
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
        LOGGER.setLevel(debug ? Level.ALL : Level.INFO);
        LOGGER.info("Debug mode: " + debug);
    }

    private CreeperLog()
    {
    }

}
