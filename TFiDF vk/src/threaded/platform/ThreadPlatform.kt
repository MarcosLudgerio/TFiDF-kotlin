package threaded.platform

import utils.TFiDF
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.locks.ReentrantLock
import java.util.logging.Logger

class ThreadPlatform(document: Map<AtomicLong, String>): Runnable {
    private val lock = ReentrantLock()
    private var documents = mutableListOf<String>()
    private val termFrequency = AtomicReference<List<Map<String, AtomicLong>>>(mutableListOf())
    private val idfResult = AtomicReference<Map<String, Double>>(mutableMapOf())
    private var resultado = mutableListOf<Map<String, Double>>()
    private val objTf: TFiDF = TFiDF()
    private val LOGGER: Logger = Logger.getLogger("DocumentReader")

    init {
        documents = document.values.toList() as MutableList<String>
    }

    companion object {
        var documents: List<String> = emptyList()
    }

    fun calculateTermFrequency(): List<Map<String, AtomicLong>> {
        lock.lock()
        try {
            termFrequency.set(objTf.calculateTermFrequencyAtomic(documents))
        } finally {
            lock.unlock()
        }
        return termFrequency.get()
    }

    fun calculateDF(countDocuments: Int): Map<String, Double> {
        lock.lock()
        return try {
            idfResult.set(objTf.calculateIDFAtomic(termFrequency.get(), countDocuments))
            idfResult.get()
        } finally {
            lock.unlock()
        }
    }

    fun calculateDFAndIDF() {
        lock.lock()
        try {
            LOGGER.info("Calculando document frequency")
            val documentFrequency = calculateTermFrequency()
            LOGGER.info("Calculando doubleMap")
            val doubleMap:Map<String, Double>  = calculateDF(documentFrequency.size)
            LOGGER.info("Calculando resultado")
            // termFrequencyList: List<Map<String, AtomicLong>>, numDocuments: Int
            resultado = objTf.calculateTFIDFAtomic(documentFrequency, doubleMap) as MutableList<Map<String, Double>>
        } finally {
            lock.unlock()
        }
    }

    override fun run() {
        LOGGER.info("Started Platform Thread")
        calculateDFAndIDF()
        LOGGER.info("Finished Thread")
    }
}