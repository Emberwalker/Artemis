package cc.emberwalker.artemis.lib

import java.io.File

import net.minecraftforge.common.config.Configuration

import cc.emberwalker.artemis.util.StackHelper

/**
 * Artemis configuration.
 *
 * @author Arkan <arkan@drakon.io>
 */
object Config {

  def loadConfig(confFile:File) {
    val conf = new Configuration(confFile)
    conf.load()

    stackDepth = conf.get("logging", "stackDepth", stackDepth).getInt(stackDepth)
    ignoreBuiltins = conf.get("logging", "ignoreBuiltins", ignoreBuiltins).getBoolean(ignoreBuiltins)

    useCustomRegex = conf.get("builtins", "useCustomRegex", useCustomRegex).getBoolean(useCustomRegex)
    customRegex = conf.get("builtins", "customRegex", customRegex).getString

    createBlamefile = conf.get("blame", "createBlamefile", createBlamefile).getBoolean(false)

    mapModIds = conf.get("idmap", "mapModIds", mapModIds).getBoolean(true)

    conf.save()
  }

  var stackDepth = 1
  var ignoreBuiltins = false

  var useCustomRegex = false
  var customRegex = StackHelper.DEFAULT_REGEX

  var createBlamefile = false

  var mapModIds = true

}
