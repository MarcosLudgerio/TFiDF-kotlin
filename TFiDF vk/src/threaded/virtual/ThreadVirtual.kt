package threaded.virtual

import utils.FileProperties
import java.io.IOException
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.locks.ReentrantLock

class ThreadVirtual(filepath: String) : Runnable {
    private val lock = ReentrantLock()
    private var filepath: String
    private val document = AtomicReference<Map<AtomicLong, String>>(mutableMapOf())
    private val objFileProperties: FileProperties = FileProperties()

    init {
        this.filepath = filepath
    }

    fun getDocument(): AtomicReference<Map<AtomicLong, String>> {
        return document
    }

    override fun run() {
        println("Tentando ler arquivo")
        try {
            lock.lock()
            document.set(objFileProperties.readDocumentAtomic(filepath))
        } catch (e: IOException) {
            println("Erro ao ler documento: ${e.message}")
        } finally {
            lock.unlock()
        }
        println("Thread Virtual para leitura de arquivo fechada")
    }
}