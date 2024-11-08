package forkandjoin

import utils.TFiDF
import java.util.concurrent.RecursiveTask

class InverseDocumentFrequencyTask(
    private val inverseTermFrequencyList: List<Map<String, Int>>,
    private val numDocuments: Int
) : RecursiveTask<Map<String, Double>>() {
    val objTFiDF: TFiDF = TFiDF()
    override fun compute(): Map<String, Double> {
        return objTFiDF.calculateIDF(inverseTermFrequencyList, numDocuments)
    }
}