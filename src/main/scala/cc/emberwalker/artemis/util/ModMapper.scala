package cc.emberwalker.artemis.util

import scala.collection.mutable
import scala.collection.JavaConversions._

import org.apache.logging.log4j.LogManager

import net.minecraftforge.fml.common.Loader

/**
 * Helper to manage mapping classes to mod IDs.
 *
 * @author Arkan <arkan@drakon.io>
 */
object ModMapper {

  private val logger = LogManager.getLogger("Artemis/Mapper")
  private var processMappings = false
  private val map = new mutable.HashMap[String, String]()

  def init() {
    logger.info("Compiling package -> mod mappings.")

    val modList = Loader.instance().getModList
    modList map { i =>
      val mId = i.getModId
      i.getOwnedPackages map { map.put(_, mId) }
    }

    logger.info(s"Done; ${map.size} entries created.")
    processMappings = true
  }

  def getModId(clName:String): Option[String] = {
    if (!processMappings) return Option(clName)

    val pkg = clName.split('.').dropRight(1).mkString(".")
    val opt = map.get(pkg)
    if (opt.isEmpty) {
      logger.warn(s"Missing mapping for package $pkg (original: $clName).")
    }

    opt
  }

}
