package cc.emberwalker.artemis.compat

import cc.emberwalker.artemis.compat.mods.GregTech
import net.minecraftforge.fml.common.Loader

/**
 * Mod compatibility handler.
 *
 * @author Arkan <arkan@drakon.io>
 */
object CompatController {

  def loadCompatMods() {
    val mods = Array(GregTech) // Declare modules here.
    for (m <- mods) {
      if (Loader.isModLoaded(m.getModId)) m.setupCompat()
    }
  }

}