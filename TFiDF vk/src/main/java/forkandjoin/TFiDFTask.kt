package forkandjoin

import utils.TFiDF
import java.util.concurrent.RecursiveTask

class TFiDFTask(
    private val documentFrequency: List<Map<String, Int>>,
    private val inverseDocumentFrequency: Map<String, Double>
) : RecursiveTask<List<Map<String, Double>>>() {
    val objTFiDF: TFiDF = TFiDF()
    override fun compute(): List<Map<String, Double>> {
        return objTFiDF.calculateTFIDF(documentFrequency, inverseDocumentFrequency)
    }
}