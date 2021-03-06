package cc.emberwalker.artemis.util

import scala.collection.mutable.ArrayBuffer
import scala.util.matching.Regex
import cc.emberwalker.artemis.lib.Config
import cc.emberwalker.artemis.Artemis

/**
 * Helper for parsing and dealing with stacktraces.
 *
 * @author Arkan <arkan@drakon.io>
 */
object StackHelper {

  // This is also the default for the custom regex config.
  val DEFAULT_REGEX = "^paulscode..*|^java.lang..*|^com.intellij..*|^sun..*"

  private val internalRegex = if (Config.useCustomRegex) new Regex(Config.customRegex) else new Regex(DEFAULT_REGEX)

  private val INDENTS = "\t\t\t\t" // TODO: Make less fucking disgusting.

  private def submitEntryToLog(stack:Array[StackTraceElement]) {
    ExitLogThread.mapLock.lock()
    try {
      val cName = stack(2).getClassName
      val cCount = ExitLogThread.failMap.get(cName)
      if (cCount.nonEmpty)
        ExitLogThread.failMap.update(cName, cCount.get + 1)
      else
        ExitLogThread.failMap.put(cName, 1)
    } catch {
      case _:Throwable => Artemis.logger.warn("Failed to add to failmap from stack: " + stack)
    } finally {
      ExitLogThread.mapLock.unlock()
    }
  }

  private def stripToBuffer(stack:Array[StackTraceElement]):ArrayBuffer[String] = {
    val out = new ArrayBuffer[String]()
    for (elem <- stack) {
      out += s"${elem.getClassName}:${elem.getMethodName}:${elem.getLineNumber}"
    }
    out.drop(2) // Remove Artemis/Java internals from start of trace.
  }

  private def stripBuiltins(stack:ArrayBuffer[String]):ArrayBuffer[String] = {
    if (Config.ignoreBuiltins)
      stack filter(x => internalRegex.findFirstMatchIn(x).isEmpty)
    else
      stack
  }

  private def shrinkStack(stack:ArrayBuffer[String]):ArrayBuffer[String] = {
    stack.slice(0, Config.stackDepth)
  }

  private def makePrintable(stack:ArrayBuffer[String]):String = {
    if (stack.size <= 0) return "...internal..."

    var str = ""
    var first = true
    for (elem <- stack) {
      if (first) {
        str = elem
        first = false
      } else str += s"\n$INDENTS$elem"
    }
    str
  }

  def getStackTag(stack:Array[StackTraceElement]):String = {
    if (Config.createBlamefile) submitEntryToLog(stack) // Save some processing if we're not making a blamefile.

    // Mod ID map takes a shortcut, if possible.
    if (Config.mapModIds) {
      val opt = ModMapper.getModId(stack(2).getClassName)
      if (opt.nonEmpty) return s"Mod/${opt.get}"
    }

    val filtered = stripBuiltins(stripToBuffer(stack))
    val minimised = shrinkStack(filtered)
    makePrintable(minimised)
  }

}
