package cc.emberwalker.artemis

import org.apache.logging.log4j.LogManager

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler

import cc.emberwalker.artemis.lib.Config
import cc.emberwalker.artemis.compat.CompatController
import cc.emberwalker.artemis.util.{ModMapper, ExitLogThread}

/**
 * The stdout-hunter mod.
 *
 * @author Arkan <arkan@drakon.io>
 */
@Mod(modid = "Artemis", name = "Artemis", version = "${version}", modLanguage = "scala", dependencies = "before:*", acceptableRemoteVersions="*")
object Artemis {

  val logger = LogManager.getLogger("Artemis/Core")
  val outLogger = LogManager.getLogger("Artemis/STDOUT")
  val errLogger = LogManager.getLogger("Artemis/STDERR")

  @EventHandler
  def preInit(evt:FMLPreInitializationEvent) {
    logger.info("Artemis ${version} loading.")

    logger.info("Loading configuration.")
    Config.loadConfig(evt.getSuggestedConfigurationFile)

    logger.info("Inserting TracingPrintStream.")
    System.setOut(new TracingPrintStream(outLogger, System.out))
    System.setErr(new TracingPrintStream(errLogger, System.err))

    logger.info("Initialising plugins.")
    CompatController.loadCompatMods()

    if (Config.createBlamefile) {
      logger.info("Injecting JVM shutdown hook thread for blamefile.")
      Runtime.getRuntime.addShutdownHook(new ExitLogThread)
    }

    if (Config.mapModIds) ModMapper.init()

    logger.info("Setup completed.")
  }

}
