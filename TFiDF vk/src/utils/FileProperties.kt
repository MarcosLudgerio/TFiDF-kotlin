package utils

import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.atomic.AtomicLong

class FileProperties {
    private var countDoc: Int = 0
    private val counter: AtomicLong = AtomicLong()

    fun readDocument(filepath: String): Map<Int, String> {
        val document: HashMap<Int, String> = HashMap()
        val reader = Files.newBufferedReader(Paths.get(filepath))
        var lines: List<String> = reader.readLines()
        for (line in lines) {
            if (line.isEmpty()) break
            countDoc++
            document.put(countDoc, line)
        }
        reader.close()
        return document
    }


    private val document: Map<Integer, String> = HashMap()

}