package cc.emberwalker.artemis

import cpw.mods.fml.common.{FMLLog, Mod}
import cpw.mods.fml.common.event.{FMLPostInitializationEvent, FMLInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.Mod.EventHandler
import java.util.logging.Logger
import cc.emberwalker.artemis.lib.Config
import cc.emberwalker.artemis.compat.CompatController

/**
 * The stdout-hunter mod.
 *
 * @author Arkan <arkan@emberwalker.cc>
 */
@Mod(modid = "Artemis", name = "Artemis", version = "${version}", modLanguage = "scala", dependencies = "before:*")
object Artemis {

  val logger = Logger.getLogger("Artemis:Core")
  val outLogger = Logger.getLogger("Artemis:STDOUT")
  val errLogger = Logger.getLogger("Artemis:STDERR")

  @EventHandler
  def preInit(evt:FMLPreInitializationEvent) {
    logger.setParent(FMLLog.getLogger)
    outLogger.setParent(logger)
    errLogger.setParent(logger)

    logger.info("Artemis ${version} loading.")

    logger.info("Loading configuration.")
    Config.loadConfig(evt.getSuggestedConfigurationFile)

    logger.info("Inserting TracingPrintStream.")
    System.setOut(new TracingPrintStream(outLogger, "STDOUT", System.out))
    System.setErr(new TracingPrintStream(errLogger, "STDERR", System.err))

    logger.info("Initialising plugins.")
    CompatController.loadCompatMods()

    logger.info("Setup completed.")
  }

  @EventHandler
  def init(evt:FMLInitializationEvent) {

  }

  @EventHandler
  def postInit(evt:FMLPostInitializationEvent) {

  }

}
