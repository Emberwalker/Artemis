package cc.emberwalker.artemis

import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.network.NetworkMod
import cpw.mods.fml.common.event.{FMLPostInitializationEvent, FMLInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.Mod.EventHandler
import java.util.logging.Logger

/**
 * The stdout-hunter mod.
 *
 * @author Arkan <arkan@emberwalker.cc>
 */
@Mod(modid = "Artemis", name = "Artemis", version = "${version}", modLanguage = "scala")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
object Artemis {

  val logger = Logger.getLogger("Artemis")

  @EventHandler
  def preInit(evt:FMLPreInitializationEvent) {
    logger.info("Artemis ${version} loading.")
    logger.info("Inserting TracingPrintStream...")
    System.setOut(new TracingPrintStream(System.out, "STDOUT"))
    System.setErr(new TracingPrintStream(System.err, "STDERR"))
    logger.info("TracingPrintStream inserted on STDOUT/STDERR. These will now be redirected.")
  }

  @EventHandler
  def init(evt:FMLInitializationEvent) {
    System.out.println("Testing time!")
  }

  @EventHandler
  def postInit(evt:FMLPostInitializationEvent) {

  }

}
