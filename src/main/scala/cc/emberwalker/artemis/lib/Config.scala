package cc.emberwalker.artemis.lib

import java.io
import cc.emberwalker.artemis.util.StackHelper
import net.minecraftforge.common.config.Configuration

/**
 * Artemis configuration.
 *
 * @author Arkan <arkan@emberwalker.cc>
 */
object Config {

  def loadConfig(confFile:io.File) {
    val conf = new Configuration(confFile)
    conf.load()

    stackDepth = conf.get("logging", "stackDepth", stackDepth).getInt(stackDepth)
    ignoreBuiltins = conf.get("logging", "ignoreBuiltins", ignoreBuiltins).getBoolean(ignoreBuiltins)

    useCustomRegex = conf.get("builtins", "useCustomRegex", useCustomRegex).getBoolean(useCustomRegex)
    customRegex = conf.get("builtins", "customRegex", customRegex).getString

    createBlamefile = conf.get("blame", "createBlamefile", createBlamefile).getBoolean(false)

    conf.save()
  }

  var stackDepth = 1
  var ignoreBuiltins = false

  var useCustomRegex = false
  var customRegex = StackHelper.DEFAULT_REGEX

  var createBlamefile = false

}
