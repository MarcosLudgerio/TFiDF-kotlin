package utils

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.atomic.AtomicLong
import java.util.logging.Logger


class FileProperties {
    private var countDoc: Int = 0
    private val counter: AtomicLong = AtomicLong()
    val LOGGER: Logger = Logger.getLogger("DocumentReader")


    @Throws(IOException::class)
    fun readDocument(filepath: String): Map<Int, String> {
        LOGGER.info("Reading documents from $filepath")
        val document = mutableMapOf<Int, String>()
        Files.newBufferedReader(Paths.get(filepath)).use { reader ->
            reader.lineSequence().forEach { line ->
                countDoc++
                if (line.isNotEmpty()) {
                    document[countDoc] = line
                }
            }
        }

        LOGGER.info("Reading finished")
        return document
    }

    @Throws(IOException::class)
    fun readDocumentAtomic(filepath: String): Map<AtomicLong, String>{
        val document = mutableMapOf<AtomicLong, String>()
        LOGGER.info("Reading documents Atomic from $filepath")
        Files.newBufferedReader(Paths.get(filepath)).use { reader ->
            reader.lineSequence().forEach { line ->
                if (line.isNotEmpty())
                    document[AtomicLong(counter.incrementAndGet())] = line
            }
        }
        LOGGER.info("Finished reading documents")
        return document
    }

}