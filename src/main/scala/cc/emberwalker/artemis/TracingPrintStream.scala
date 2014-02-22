package cc.emberwalker.artemis

import java.io.PrintStream

/**
 * No description.
 *
 * @author Arkan <arkan@emberwalker.cc>
 */
class TracingPrintStream(original:PrintStream, stream:String) extends PrintStream(original) {

  override def println(x: String) = {
    val stack = Thread.currentThread().getStackTrace
    // stack(2) is calling object.
    val name = stack(2).getClassName
    super.println(s"[Artemis-$stream][$name]: $x")
  }

}
