package cc.emberwalker.artemis.compat

import cc.emberwalker.artemis.compat.mods.GregTech
import cpw.mods.fml.common.Loader

/**
 * Mod compatibility handler.
 *
 * @author Arkan <arkan@emberwalker.cc>
 */
object CompatController {

  def loadCompatMods() {
    val mods = Array(GregTech) // Declare modules here.
    for (m <- mods) {
      if (Loader.isModLoaded(m.getModId)) m.setupCompat()
    }
  }

}
