package forkandjoin

import utils.FileProperties
import java.io.IOException
import java.util.concurrent.ForkJoinPool
import java.util.logging.Logger

object TestForkAndJoin {
    private const val filePath = "D:\\repositorios-git\\TFiDF-kotlin\\TFiDF vk\\dataset\\dataset_1mb.txt"
    private val LOGGER: Logger = Logger.getLogger(TermFrequencyTask::class.java.name)

    val objFileProperties: FileProperties = FileProperties()
    @JvmStatic
    fun main(args: Array<String>) {
        try {
            val listOfLines = objFileProperties.readDocumentAtomic(filePath)
            val valuesArray = listOfLines.values.toTypedArray()
            LOGGER.info("Criamos um array com: ${valuesArray.size} elementos")

            val pool = ForkJoinPool()
            val taskFrequencyTerm = TermFrequencyTask(valuesArray, 0, listOfLines.size)

            val totalTermFrequency = pool.invoke(taskFrequencyTerm)

            val inverseDocumentFrequencyTask = InverseDocumentFrequencyTask(totalTermFrequency, totalTermFrequency.size)
            val inverseTermFrequency = pool.invoke(inverseDocumentFrequencyTask)

            val tFiDFTask = TFiDFTask(totalTermFrequency, inverseTermFrequency)
            val listTfiDf = pool.invoke(tFiDFTask)

            LOGGER.info("Term frequency task completed")
        } catch (e: IOException) {
            println("Erro ao ler o arquivo: ${e.message}")
        }
    }
}