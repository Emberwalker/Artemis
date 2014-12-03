package cc.emberwalker.artemis

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import org.apache.logging.log4j.LogManager
import cc.emberwalker.artemis.lib.Config
import cc.emberwalker.artemis.compat.CompatController
import cc.emberwalker.artemis.util.ExitLogThread
import org.apache.logging.log4j.core.Logger

/**
 * The stdout-hunter mod.
 *
 * @author Arkan <arkan@emberwalker.cc>
 */
@Mod(modid = "Artemis", name = "Artemis", version = "${version}", modLanguage = "scala", dependencies = "before:*", acceptableRemoteVersions="*")
object Artemis {

  val logger = LogManager.getLogger("Artemis:Core").asInstanceOf[Logger]
  val outLogger = LogManager.getLogger("Artemis:STDOUT").asInstanceOf[Logger]
  val errLogger = LogManager.getLogger("Artemis:STDERR").asInstanceOf[Logger]

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

    logger.info("Setup completed.")
  }

}
