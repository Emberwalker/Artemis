package cc.emberwalker.artemis.compat.mods

import cc.emberwalker.artemis.compat.IModCompat
import cc.emberwalker.artemis.Artemis

/**
 * Gregtech compat.
 *
 * @author Arkan <arkan@drakon.io>
 */
object GregTech extends IModCompat {

  override def getModId: String = "gregtech_addon"

  override def setupCompat() {
    try {
      val clazz = Class.forName("gregtechmod.api.util.GT_Log")
      val outField = clazz.getField("out")
      val errField = clazz.getField("err")

      outField.set(null, System.out)
      errField.set(null, System.err)
      Artemis.logger.info("Reflected printstream into gregtech_addon.")
    } catch {
      case _: Throwable => Artemis.logger.warn("Failed to reflect printstream into gregtech_addon. Ignoring.")
    }
  }

}