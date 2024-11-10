package threaded.virtual

import utils.FileProperties
import java.io.IOException
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.locks.ReentrantLock
import java.util.logging.Level
import java.util.logging.Logger

class ThreadVirtual(private var filepath: String) : Runnable {
    private val lock = ReentrantLock()
    private val document = AtomicReference<Map<AtomicLong, String>>(mutableMapOf())
    private val objFileProperties: FileProperties = FileProperties()
    private val LOGGER: Logger = Logger.getLogger("DocumentReader")
    fun getDocument(): AtomicReference<Map<AtomicLong, String>> {
        return document
    }

    override fun run() {
        LOGGER.info("Started Virtual Thread")
        try {
            lock.lock()
            document.set(objFileProperties.readDocumentAtomic(filepath))
        } catch (e: IOException) {
            LOGGER.log(LOGGER.level, "Erro ao ler documento: ${e.message}")
        } finally {
            lock.unlock()
        }
        LOGGER.info("Thread Virtual para leitura de arquivo fechada")
    }
}