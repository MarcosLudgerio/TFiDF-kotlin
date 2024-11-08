package forkandjoin

import utils.TFiDF
import java.util.concurrent.RecursiveTask

class TermFrequencyTask(
    private val documents: Array<String>,
    private val start: Int,
    private val end: Int
) : RecursiveTask<List<Map<String, Int>>>() {

    companion object {
        private const val THRESHOLD = 10 // Limite para divisão de tarefas
    }

    val objTFiDF: TFiDF = TFiDF()
    override fun compute(): List<Map<String, Int>> {
        val mid = documents.size / 2
        return if (end - start <= THRESHOLD) {
            objTFiDF.calculateTermFrequency(documents.toList())
        } else {
            val leftHalf = documents.copyOfRange(start, mid)
            val rightHalf = documents.copyOfRange(mid + 1, end)
            return getAllResult(leftHalf, mid, rightHalf)
        }
    }

    private fun getAllResult(leftHalf: Array<String>, mid: Int, rightHalf: Array<String>): List<Map<String, Int>> {
        val taskLeft = TermFrequencyTask(leftHalf, start, mid)
        val taskRight = TermFrequencyTask(rightHalf, mid + 1, documents.size - 1)

        taskLeft.fork()
        taskRight.fork()

        val leftResult = taskLeft.join()
        val rightResult = taskRight.join()

        return leftResult + rightResult
    }
}