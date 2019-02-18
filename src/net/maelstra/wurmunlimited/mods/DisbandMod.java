package net.maelstra.wurmunlimited.mods;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import mod.sin.lib.Util;

import com.wurmonline.server.NoSuchPlayerException;
import com.wurmonline.server.TimeConstants;
import com.wurmonline.server.villages.Village;

import org.gotti.wurmunlimited.modloader.classhooks.HookException;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;

import org.gotti.wurmunlimited.modloader.interfaces.Configurable;
import org.gotti.wurmunlimited.modloader.interfaces.PreInitable;
import org.gotti.wurmunlimited.modloader.interfaces.ServerStartedListener;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;

public class DisbandMod implements WurmServerMod, Configurable, PreInitable
{
    boolean bDebug = false;
    public static boolean autoDisbandInactive = false;
    public static int autoDisbandInactiveDays = 30;

    public static Logger logger;

    public DisbandMod()
    {
        DisbandMod.logger = Logger.getLogger(this.getClass().getName());
    }

    public void configure(Properties properties)
    {
        this.bDebug = Boolean.parseBoolean(properties.getProperty("debug", Boolean.toString(this.bDebug)));

        DisbandMod.autoDisbandInactive = Boolean.parseBoolean(properties.getProperty("autoDisbandInactive", Boolean.toString(DisbandMod.autoDisbandInactive)));
        DisbandMod.autoDisbandInactiveDays = Integer.parseInt(properties.getProperty("autoDisbandInactiveDays", Integer.toString(DisbandMod.autoDisbandInactiveDays)));
        if (DisbandMod.autoDisbandInactiveDays < 1)
        {
            DisbandMod.logger.info("DisbandMod: autoDisbandInactiveDays set to an invalid value. Reverting to default (30). Check your configuration file!");
            DisbandMod.autoDisbandInactiveDays = 30;
        }

        if (DisbandMod.autoDisbandInactive)
        {
            DisbandMod.logger.info("DisbandMod: Deeds whose mayors are inactive for " + Integer.toString(DisbandMod.autoDisbandInactiveDays) + " days will be automatically disbanded.");
        }
    }

    public void preInit()
    {
        try
        {
            ClassPool classPool = HookManager.getInstance().getClassPool();
            Class<DisbandMod> thisClass = DisbandMod.class;

            if (DisbandMod.autoDisbandInactive)
            {
                //CtClass ctGuardPlan = classPool.get("com.wurmonline.server.villages.GuardPlan");
                //String replace = "net.maelstra.wurmunlimited.mods.DisbandMod.logger.info(\"Checking for mayor inactivity on village \\\" + this.getVillage().getName() + \\\"...\");"
                //+ "if(this.getVillage().getMayor().isPlayer()){"
                //+ "  long lastLogout = com.wurmonline.server.Players.getInstance().getLastLogoutForPlayer(this.getVillage().getMayor().getId());"
                //+ "  long delta = System.currentTimeMillis() - lastLogout;"
                //+ "  long gracePeriod = " + String.valueOf(TimeConstants.DAY_MILLIS * DisbandMod.autoDisbandInactiveDays) + ";"
                //+ "  if(delta >= gracePeriod){"
                //+ "    this.getVillage().disband(\"upkeep\");"
                //+ "    return false;"
                //+ "  }"
                //+ "}";
                //Util.setReason("Forcibly disband deeds if the mayor is inactive for configured number of days (regardless of upkeep).");
                //Util.insertBeforeDeclared(thisClass, ctGuardPlan, "poll", replace);

                CtClass ctVillage = classPool.get("com.wurmonline.server.villages.Village");
                String replace = "net.maelstra.wurmunlimited.mods.DisbandMod.logAttempt(this.getName());"
                  + "try{"
                  + "  if(this.getMayor().isPlayer()&&!this.isPermanent){"
                  + "    long lastLogout = com.wurmonline.server.Players.getInstance().getLastLogoutForPlayer(this.getMayor().getId());"
                  + "    long delta = System.currentTimeMillis() - lastLogout;"
                  + "    long gracePeriod = " + String.valueOf(TimeConstants.DAY_MILLIS * DisbandMod.autoDisbandInactiveDays) + ";"
                  + "    if(delta >= gracePeriod){"
                  + "      return true;"
                  + "    }"
                  + "  }"
                  + "}"
                  + "catch(com.wurmonline.server.NoSuchPlayerException nspe){"
                  + "  net.maelstra.wurmunlimited.mods.DisbandMod.handleNoSuchPlayerException(nspe);"
                  + "  return false;"
                  + "}";
                Util.setReason("Automatically disband deeds if the mayor is inactive for configured number of days (regardless of upkeep).");
                Util.insertBeforeDeclared(thisClass, ctVillage, "checkDisband", replace);
            }
        }
        catch (NotFoundException e)
        {
            throw new HookException(e);
        }
    }

    public static void handleNoSuchPlayerException(NoSuchPlayerException nspe)
    {
        DisbandMod.logger.severe(nspe.getMessage());
    }

    public static void logAttempt(String villageName)
    {
        DisbandMod.logger.info("Checking for mayor inactivity on village " + villageName + "...");
    }
}
