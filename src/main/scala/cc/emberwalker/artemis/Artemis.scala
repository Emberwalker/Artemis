package cc.emberwalker.artemis

import cpw.mods.fml.common.{FMLLog, Mod}
import cpw.mods.fml.common.event.{FMLPostInitializationEvent, FMLInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.Mod.EventHandler
import java.util.logging.Logger
import cc.emberwalker.artemis.lib.Config
import org.apache.logging.log4j.LogManager

/**
 * The stdout-hunter mod.
 *
 * @author Arkan <arkan@emberwalker.cc>
 */
@Mod(modid = "Artemis", name = "Artemis", version = "${version}", modLanguage = "scala", dependencies = "before:*")
object Artemis {

  val logger = LogManager.getLogger("Artemis:Core")
  val outLogger = LogManager.getLogger("Artemis:STDOUT")
  val errLogger = LogManager.getLogger("Artemis:STDERR")

  @EventHandler
  def preInit(evt:FMLPreInitializationEvent) {
    logger.info("Artemis ${version} loading.")

    logger.info("Loading configuration.")
    Config.loadConfig(evt.getSuggestedConfigurationFile)

    logger.info("Inserting TracingPrintStream.")
    System.setOut(new TracingPrintStream(outLogger, System.out))
    System.setErr(new TracingPrintStream(errLogger, System.err))
    logger.info("Setup completed.")
  }

  @EventHandler
  def init(evt:FMLInitializationEvent) {

  }

  @EventHandler
  def postInit(evt:FMLPostInitializationEvent) {

  }

}
