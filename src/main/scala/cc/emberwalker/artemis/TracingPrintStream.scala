package cc.emberwalker.artemis

import java.io.PrintStream
import java.util.logging.Logger
import cc.emberwalker.artemis.util.StackHelper

/**
 * Stream tracer.
 *
 * @author Arkan <arkan@emberwalker.cc>
 */
class TracingPrintStream(logger:Logger, stream:String, original:PrintStream) extends PrintStream(original) {

  override def println(x: String) = {
    val stack = Thread.currentThread().getStackTrace
    logger.info(s"[${StackHelper.getStackTag(stack)}]: $x")
  }

}
