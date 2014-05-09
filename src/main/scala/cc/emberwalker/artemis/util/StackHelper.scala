package cc.emberwalker.artemis.util

import scala.collection.mutable.ArrayBuffer
import scala.util.matching.Regex
import cc.emberwalker.artemis.lib.Config

/**
 * Helper for parsing and dealing with stacktraces.
 *
 * @author Arkan <arkan@emberwalker.cc>
 */
object StackHelper {

  // This is also the default for the custom regex config.
  val DEFAULT_REGEX = "^paulscode..*|^java.lang..*|^com.intellij..*|^sun..*"

  private val internalRegex = if (Config.useCustomRegex) new Regex(Config.customRegex) else new Regex(DEFAULT_REGEX)

  private val INDENTS = "\t\t\t\t\t\t\t\t\t\t\t " // TODO: Make less fucking disgusting.

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
    val filtered = stripBuiltins(stripToBuffer(stack))
    val minimised = shrinkStack(filtered)
    makePrintable(minimised)
  }

}
