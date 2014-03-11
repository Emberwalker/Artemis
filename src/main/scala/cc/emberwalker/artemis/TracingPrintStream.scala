package cc.emberwalker.artemis

import java.io.PrintStream
import java.util.logging.Logger

/**
 * No description.
 *
 * @author Arkan <arkan@emberwalker.cc>
 */
class TracingPrintStream(logger:Logger, stream:String, original:PrintStream) extends PrintStream(original) {

  override def println(x: String) = {
    val stack = Thread.currentThread().getStackTrace
    // stack(2) is calling object.
    val name = stack(2).getClassName
    logger.info(s"[$name]: $x")
  }

}
