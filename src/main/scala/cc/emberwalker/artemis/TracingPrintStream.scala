package cc.emberwalker.artemis

import java.io.PrintStream
import cc.emberwalker.artemis.util.StackHelper
import org.apache.logging.log4j.Logger

/**
 * Stream tracer.
 *
 * @author Arkan <arkan@emberwalker.cc>
 */
class TracingPrintStream(logger:Logger, original:PrintStream) extends PrintStream(original) {

  override def println(x: String) = {
    val stack = Thread.currentThread().getStackTrace
    logger.info(s"[${StackHelper.getStackTag(stack)}]: $x")
  }

}
