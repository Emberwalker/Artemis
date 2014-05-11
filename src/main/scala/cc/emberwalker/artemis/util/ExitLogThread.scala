package cc.emberwalker.artemis.util

import scala.collection.mutable
import java.util.concurrent.locks.ReentrantLock
import org.apache.logging.log4j.LogManager
import java.util.Date
import java.text.SimpleDateFormat
import java.io.File
import org.apache.commons.io.FileUtils

/**
 * JVM Shutdown Hook thread
 *
 * @author Arkan <arkan@emberwalker.cc>
 */
class ExitLogThread extends Thread with Runnable {

  private val logger = LogManager.getLogger

  this.setName("ArtemisExitHook")
  logger.info("Exit hook created. Ready.")

  override def run() {
    import ExitLogThread._

    mapLock.lock()
    val vec = failMap.toVector
    mapLock.unlock()

    val dateStr = new SimpleDateFormat("dd/MM/yyyy (ss:mm:hh)").format(new Date())
    var str = "Artemis - Badly behaved class list\n"
    str += s"Generated at $dateStr\n"
    str += "==================================\n"

    vec sortBy(_._2) reverseMap (elem => str += s"${elem._2}\t:\t${elem._1}\n")

    try {
      val f = new File("ArtemisBlamefile.log")
      FileUtils.writeStringToFile(f, str)
    } catch {
      case _:Throwable => // Ignore.
    }
  }

}

object ExitLogThread {

  val mapLock = new ReentrantLock()
  val failMap = new mutable.HashMap[String, Int]()

}
